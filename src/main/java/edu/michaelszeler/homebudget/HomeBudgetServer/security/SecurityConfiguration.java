package edu.michaelszeler.homebudget.HomeBudgetServer.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.savedrequest.NullRequestCache;

import javax.sql.DataSource;

//@EnableWebSecurity
//@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    private final DataSource dataSource;
    private final CustomBasicAuthenticationEntryPoint authenticationEntryPoint;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public SecurityConfiguration(DataSource dataSource, CustomBasicAuthenticationEntryPoint entryPoint, PasswordEncoder passwordEncoder) {
        this.dataSource = dataSource;
        this.authenticationEntryPoint = entryPoint;
        this.passwordEncoder = passwordEncoder;
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder.jdbcAuthentication()
                .dataSource(dataSource)
                .usersByUsernameQuery("SELECT u.login, u.password, s.enabled FROM user u, security s WHERE s.user_id = u.id AND u.login=?")
                .authoritiesByUsernameQuery("SELECT u.login, s.authority FROM user u, security s WHERE s.user_id = u.id AND u.login=?")
                .passwordEncoder(passwordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.requestCache()
                    .requestCache(new NullRequestCache())
                .and()
                    .authorizeRequests()
                    .antMatchers(HttpMethod.GET,"/user").fullyAuthenticated()
                    .antMatchers(HttpMethod.POST, "/user").access("!isFullyAuthenticated()")
                    .antMatchers(HttpMethod.PUT, "/user/changePassword").access("!isFullyAuthenticated()")
                    .antMatchers(HttpMethod.GET,"/budget", "/budget/archive").fullyAuthenticated()
                    .antMatchers(HttpMethod.POST,"/budget").fullyAuthenticated()
                    .antMatchers(HttpMethod.GET,"/strategy", "/strategy/categories", "/strategy/archive").fullyAuthenticated()
                    .antMatchers(HttpMethod.POST, "/strategy").fullyAuthenticated()
                    .antMatchers(HttpMethod.GET,"/expense", "/expense/categories").fullyAuthenticated()
                    .antMatchers(HttpMethod.POST, "/expense").fullyAuthenticated()
                .and()
                    .httpBasic()
                    .authenticationEntryPoint(authenticationEntryPoint)
                .and()
                    .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                    .csrf()
                    .disable();
    }
}
