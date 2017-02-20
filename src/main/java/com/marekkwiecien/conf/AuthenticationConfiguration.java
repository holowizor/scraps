package com.marekkwiecien.conf;

import com.marekkwiecien.model.WebRole;
import com.marekkwiecien.model.WebUser;
import com.marekkwiecien.repo.WebUserRepository;
import com.marekkwiecien.service.WebUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.PostConstruct;
import java.util.Arrays;

/**
 * Created by holow on 2/20/2017.
 */
@Configuration
@EnableGlobalMethodSecurity(securedEnabled = true)
public class AuthenticationConfiguration extends GlobalAuthenticationConfigurerAdapter {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private WebUserDetailsService webUserDetailsService;

    @Autowired
    private WebUserRepository webUserRepository;

    @Override
    public void init(final AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(webUserDetailsService).passwordEncoder(passwordEncoder);
    }

    @PostConstruct
    public void createDefaultUser() {

        WebUser admin = webUserRepository.findOneByUsername("admin");
        if (admin == null) {
            admin = new WebUser();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("admin"));
            admin.setRoles(Arrays.asList(WebRole.ROLE_ADMIN));
            webUserRepository.save(admin);
        }
    }
}
