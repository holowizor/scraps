package com.marekkwiecien.conf;

import com.marekkwiecien.service.RememberMeTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Created by holow on 2/20/2017.
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private RememberMeTokenService rememberMeTokenService;

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/css/**", "/fonts/**", "/img/**", "/js/**", "/signup")
                .permitAll().anyRequest().authenticated();

        http.formLogin().loginPage("/login").permitAll().and().rememberMe().tokenRepository(rememberMeTokenService)
                .and().logout().logoutUrl("/logout").logoutSuccessUrl("/login").permitAll();

        http.csrf().disable();
        http.headers().frameOptions().sameOrigin();
    }

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
