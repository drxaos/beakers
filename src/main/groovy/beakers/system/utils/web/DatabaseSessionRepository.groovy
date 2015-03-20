package beakers.system.utils.web

import beakers.system.domain.web.Session
import org.apache.commons.collections4.map.LRUMap
import org.springframework.session.ExpiringSession
import org.springframework.session.MapSession
import org.springframework.session.SessionRepository

public class DatabaseSessionRepository implements SessionRepository<ExpiringSession> {

    private Integer defaultMaxInactiveInterval

    private Map sessionsCache

    private int cacheSize

    DatabaseSessionRepository(int cacheSize) {
        this.cacheSize = cacheSize
        sessionsCache = Collections.synchronizedMap(new LRUMap(cacheSize))
    }

    public void setDefaultMaxInactiveInterval(int defaultMaxInactiveInterval) {
        this.defaultMaxInactiveInterval = Integer.valueOf(defaultMaxInactiveInterval)
    }

    public void save(ExpiringSession session) {
        sessionsCache.put(session.getId(), session)
        Session.withTransaction {
            Session s = Session.get(session.getId())
            if (!s) {
                s = new Session()
            }
            if (s.new || session.lastAccessedTime - s.lastAccessedTime > 60000) {
                s.mergeSession(session)
                s.save(flush: true)
                Session.executeUpdate("DELETE FROM Session s WHERE :now - s.lastAccessedTime > s.maxInactiveIntervalInSeconds * 1000", [now: System.currentTimeMillis()])
            }
        }
    }

    public ExpiringSession getSession(String id) {
        ExpiringSession saved = sessionsCache.get(id)
        if (saved == null) {
            Session.withTransaction {
                saved = Session.get(id)
            }
        }
        if (saved == null) {
            return null
        }
        if (saved.isExpired()) {
            delete(saved.getId())
            return null
        }
        MapSession result = new MapSession(saved)
        result.setLastAccessedTime(System.currentTimeMillis())
        return result
    }

    public void delete(String id) {
        sessionsCache.remove(id)
        Session.withTransaction {
            Session s = Session.get(id)
            s.delete(flush: true)
        }
    }

    public ExpiringSession createSession() {
        ExpiringSession result = new MapSession()
        if (defaultMaxInactiveInterval != null) {
            result.setMaxInactiveIntervalInSeconds(defaultMaxInactiveInterval)
        }
        return result
    }
}
