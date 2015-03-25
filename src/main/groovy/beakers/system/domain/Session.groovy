package beakers.system.domain

import grails.persistence.Entity
import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString
import org.springframework.session.ExpiringSession

import javax.persistence.Id
import java.util.concurrent.TimeUnit

@Entity
@ToString
@EqualsAndHashCode
class Session implements ExpiringSession {

    public static final int DEFAULT_MAX_INACTIVE_INTERVAL_SECONDS = 1800;

    @Id
    String id
    long creationTime = java.lang.System.currentTimeMillis();
    long lastAccessedTime = creationTime

    int maxInactiveIntervalInSeconds = DEFAULT_MAX_INACTIVE_INTERVAL_SECONDS;

    static transients = ['sessionAttrs', 'dataCache']
    transient Map<String, Object> sessionAttrs
    byte[] dataCache

    static constraints = {
        id nullable: false, blank: false, unique: true
        creationTime nullable: false
        lastAccessedTime nullable: false
        data nullable: true
    }

    static mapping = {
        id column: 'id', generator: 'assigned'
    }

    public mergeSession(ExpiringSession session) {
        if (session == null) {
            throw new IllegalArgumentException("session cannot be null");
        }
        this.id = session.getId();
        this.sessionAttrs = new HashMap<String, Object>(session.getAttributeNames().size());
        for (String attrName : session.getAttributeNames()) {
            Object attrValue = session.getAttribute(attrName);
            this.sessionAttrs.put(attrName, attrValue);
        }
        this.lastAccessedTime = session.getLastAccessedTime();
        this.creationTime = session.getCreationTime();
        this.maxInactiveIntervalInSeconds = session.maxInactiveIntervalInSeconds
        this.dataCache = null
    }

    public boolean isNew() {
        return lastAccessedTime == creationTime
    }

    public boolean isExpired() {
        return isExpired(java.lang.System.currentTimeMillis());
    }

    boolean isExpired(long now) {
        if (maxInactiveIntervalInSeconds < 0) {
            return false;
        }
        return now - TimeUnit.SECONDS.toMillis(maxInactiveIntervalInSeconds) >= lastAccessedTime;
    }

    public Object getAttribute(String attributeName) {
        return sessionAttrs.get(attributeName);
    }

    public Set<String> getAttributeNames() {
        return sessionAttrs.keySet();
    }

    public void setAttribute(String attributeName, Object attributeValue) {
        if (attributeValue == null) {
            removeAttribute(attributeName);
        } else {
            sessionAttrs.put(attributeName, attributeValue);
        }
        this.dataCache = null
    }

    public void removeAttribute(String attributeName) {
        sessionAttrs.remove(attributeName);
        this.dataCache = null
    }

    byte[] getData() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream()
        baos.withObjectOutputStream { it.writeObject(sessionAttrs); it.flush() }
        dataCache = baos.toByteArray()
        return dataCache
    }

    void setData(byte[] data) {
        dataCache = data
        ByteArrayInputStream bais = new ByteArrayInputStream(data)
        sessionAttrs = bais.withObjectInputStream { it.readObject() }
    }
}