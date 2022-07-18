package com.devsuperior.movieflix.config;

import com.devsuperior.movieflix.security.JwtTokenVerifierFilter;
import com.devsuperior.movieflix.security.JwtUserPasswordAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtConfig jwtConfig;

    private final AuthConfig authConfig;

    private final Environment env;

    final BCryptPasswordEncoder passwordEncoder;

    private final UserDetailsService userDetailsService;

    public WebSecurityConfig(JwtConfig jwtConfig, BCryptPasswordEncoder passwordEncoder, UserDetailsService userDetailsService, AuthConfig authConfig, Environment env) {
        this.jwtConfig = jwtConfig;
        this.passwordEncoder = passwordEncoder;
        this.userDetailsService = userDetailsService;
        this.authConfig = authConfig;
        this.env = env;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder);
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    @Bean
    public JwtUserPasswordAuthenticationFilter jwtLoginFilter() throws Exception {
        var filter = new JwtUserPasswordAuthenticationFilter(jwtConfig);
        filter.setAuthenticationManager(authenticationManager());
        filter.setFilterProcessesUrl("/oauth/token");
        return filter;
    }

     @Override
    protected void configure(HttpSecurity http) throws Exception {
         if (Arrays.asList(env.getActiveProfiles()).contains("test")) {
             http.headers().frameOptions().disable();
         }
        http.
                csrf().disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilter(jwtLoginFilter())
                .addFilterAfter(new JwtTokenVerifierFilter(jwtConfig, authConfig), JwtUserPasswordAuthenticationFilter.class)
                .authorizeRequests()
                .antMatchers("/actuator/**").permitAll()
                .antMatchers("/h2-console/**").permitAll()
                .antMatchers("/h2-console").permitAll()
                .antMatchers("/login").permitAll()
                .antMatchers("/oauth/token").permitAll()
                .antMatchers("/users/profile").authenticated()
                .antMatchers(HttpMethod.POST,"/reviews").hasRole("MEMBER")
                .anyRequest().authenticated();


    }
}
