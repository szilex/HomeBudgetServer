package edu.michaelszeler.homebudget.HomeBudgetServer.security;

import edu.michaelszeler.homebudget.HomeBudgetServer.service.implementation.DefaultUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import static edu.michaelszeler.homebudget.HomeBudgetServer.security.SecurityConstants.SIGN_UP_URL;

@EnableWebSecurity
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {
    private final DefaultUserDetailsService userDetailsService;
    private final CustomBasicAuthenticationEntryPoint authenticationEntryPoint;
    private final PasswordEncoder passwordEncoder;

    public WebSecurityConfiguration(DefaultUserDetailsService userDetailsService, CustomBasicAuthenticationEntryPoint authenticationEntryPoint, PasswordEncoder passwordEncoder) {
        this.userDetailsService = userDetailsService;
        this.authenticationEntryPoint = authenticationEntryPoint;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable().authorizeRequests()
                .antMatchers(HttpMethod.POST, SIGN_UP_URL).permitAll()
                .antMatchers(HttpMethod.GET,"/user").fullyAuthenticated()
                .antMatchers(HttpMethod.POST, "/user/register").access("!isFullyAuthenticated()")
                .antMatchers(HttpMethod.PUT, "/user/changePassword").access("!isFullyAuthenticated()")
                .antMatchers(HttpMethod.GET,"/budget", "/budget/archive").fullyAuthenticated()
                .antMatchers(HttpMethod.POST,"/budget").fullyAuthenticated()
                .antMatchers(HttpMethod.GET,"/strategy", "/strategy/categories", "/strategy/archive").fullyAuthenticated()
                .antMatchers(HttpMethod.POST, "/strategy").fullyAuthenticated()
                .antMatchers(HttpMethod.GET,"/expense", "/expense/categories").fullyAuthenticated()
                .antMatchers(HttpMethod.POST, "/expense").fullyAuthenticated()
                .anyRequest().authenticated()
                .and()
                .httpBasic()
                .authenticationEntryPoint(authenticationEntryPoint)
                .and()
                .addFilter(new JWTAuthenticationFilter(authenticationManager()))
                .addFilter(new JWTAuthorizationFilter(authenticationManager()))
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
        return source;
    }
}
