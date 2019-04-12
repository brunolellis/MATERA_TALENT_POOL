package io.github.brunolellis.employee.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class ApiSecurityConfig extends WebSecurityConfigurerAdapter {

	// matera
    private static final String SECRET = "$2a$04$HUTL8U5pwFNJVk7GnIPQR.hJf5gCV2Ov0n.OJeLRfMBOyi/8IuNG6";

	@Override
    protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
        	.passwordEncoder(passwordEncoder())
        	.withUser("matera")
        	.password(SECRET)
        	.roles("ADMIN");
    }

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http.httpBasic()
        		.realmName("employee-api")
        		.and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        		.and().csrf().disable()
        		.authorizeRequests().antMatchers(HttpMethod.DELETE, "/api/**").authenticated()
        		.anyRequest().permitAll();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(4);
    }
}
