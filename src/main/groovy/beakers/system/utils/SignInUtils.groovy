package beakers.system.utils

import beakers.system.domain.auth.User
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder

public class SignInUtils {

    /**
     * Automatically sign in user
     *
     * @param username User's username
     */
    public static void signin(String username) {
        User.withTransaction {
            def roles = User.findByUsername(username).roleGroup.roles*.name.collect { new SimpleGrantedAuthority(it) }
            SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(username, null, roles));
        }
    }

    public static void signout() {
        SecurityContextHolder.getContext().setAuthentication(null);
    }

}
