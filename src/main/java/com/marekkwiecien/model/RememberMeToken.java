package com.marekkwiecien.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.security.web.authentication.rememberme.PersistentRememberMeToken;

import java.util.Date;

/**
 * Created by holow on 2/20/2017.
 */
public class RememberMeToken extends PersistentRememberMeToken {

    @Id
    private final String id;

    @PersistenceConstructor
    public RememberMeToken(String id, String username, String series, String tokenValue, Date date) {
        super(username, series, tokenValue, date);
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
