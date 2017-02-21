package com.marekkwiecien.web;

import com.marekkwiecien.model.WebUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Collection;

/**
 * Created by holow on 08.11.2016.
 */
public final class ControllerHelper {

    private static final Logger log = LoggerFactory.getLogger(ControllerHelper.class);

    private ControllerHelper() {
        // utility class
    }

    public static boolean hasRole(final Collection<? extends GrantedAuthority> authorities, final String... rolesToCheck) {
        if (authorities != null) for (final GrantedAuthority authority : authorities)
            if (rolesToCheck != null) for (final String roleToCheck : rolesToCheck) {
                log.info("checking auth {} against role to check {}", authority.getAuthority(), roleToCheck);
                if (authority.getAuthority().equals(roleToCheck)) return true;
            }

        return false;
    }

    public static WebUser getLoggedInUser() {
        try {
            final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            if (auth != null) return (WebUser) auth.getPrincipal();
        } catch (final Exception e) {
            // ignore
        }
        return null;
    }

}
