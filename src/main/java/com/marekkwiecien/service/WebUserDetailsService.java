package com.marekkwiecien.service;

import com.marekkwiecien.repo.WebUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Created by holow on 2/20/2017.
 */
@Service
public class WebUserDetailsService implements UserDetailsService {

    @Autowired
    private WebUserRepository webUserRepository;

    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        return webUserRepository.findOneByUsername(username);
    }

}
