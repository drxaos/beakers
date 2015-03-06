package beakers.config

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.Ordered
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.encrypt.Encryptors
import org.springframework.security.crypto.encrypt.TextEncryptor
import org.springframework.security.crypto.password.NoOpPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.authentication.AuthenticationSuccessHandler
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler

import javax.sql.DataSource

/**
 * Spring security
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true, order = Ordered.HIGHEST_PRECEDENCE)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private ApplicationContext context;

    @Autowired
    private DataSource dataSource;

    @Autowired
    public void registerAuthentication(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication()
                .dataSource(dataSource)
                .usersByUsernameQuery('''
                        select username, password, true from user where username = ?
                ''')
                .authoritiesByUsernameQuery('''
                        select username, r.name as authority from user u, role_group rg, role_group_roles rgr, role r
                        where u.username = ? and u.role_group_id = rg.id and rgr.role_group_id = rg.id and rgr.role_id = r.id
                ''')
                .groupAuthoritiesByUsername('''
                        select rg.id as id, rg.name as group_name, username, r.name as authority from user u, role_group rg, role_group_roles rgr, role r
                        where u.username = ? and u.role_group_id = rg.id and rgr.role_group_id = rg.id and rgr.role_id = r.id
                ''')
                .passwordEncoder(passwordEncoder());
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/css/*.*", "/images/*.*", "/js/*.*");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/login/**", "/signup/**", "/signout/**").anonymous()
                .antMatchers("/favicon.ico", "/favicon.png", "/css/**", "/images/**", "/js/**").anonymous()

                .and()
                .formLogin()
                .loginPage("/login")
                .successHandler(successHandler())
                .loginProcessingUrl("/login/authenticate")
                .failureUrl("/login?error=1")

                .and().logout()
                .logoutUrl("/signout")
                .logoutSuccessUrl("/")
                .deleteCookies("JSESSIONID")

                .and().exceptionHandling().accessDeniedPage("/login")
                .and().rememberMe().key("rememberMe").tokenValiditySeconds(86400)
                .and().csrf().disable()

        http
                .sessionManagement()
                .maximumSessions(Integer.MAX_VALUE)
                .expiredUrl("/login?expired=1")
                .maxSessionsPreventsLogin(true)
                .and()
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    @Bean
    public TextEncryptor textEncryptor() {
        return Encryptors.noOpText();
    }

    @Bean
    public AuthenticationSuccessHandler successHandler() {
        SavedRequestAwareAuthenticationSuccessHandler handler = new SavedRequestAwareAuthenticationSuccessHandler();
        handler.defaultTargetUrl = '/'
        handler.alwaysUseDefaultTargetUrl = false
        handler.targetUrlParameter = 'spring-security-redirect'
        handler.useReferer = false
        return handler;
    }
}
