package com.marekkwiecien.service;

import com.marekkwiecien.model.RememberMeToken;
import com.marekkwiecien.repo.RememberMeTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.rememberme.PersistentRememberMeToken;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created by holow on 2/20/2017.
 */
@Service
public class RememberMeTokenService implements PersistentTokenRepository {

    @Autowired
    private RememberMeTokenRepository repository;

    @Override
    public void createNewToken(PersistentRememberMeToken token) {
        repository.save(new RememberMeToken(null,
                token.getUsername(),
                token.getSeries(),
                token.getTokenValue(),
                token.getDate()));
    }

    @Override
    public void updateToken(String series, String value, Date lastUsed) {
        RememberMeToken token = repository.findBySeries(series);
        repository.save(new RememberMeToken(token.getId(), token.getUsername(), series, value, lastUsed));
    }

    @Override
    public PersistentRememberMeToken getTokenForSeries(String seriesId) {
        return repository.findBySeries(seriesId);
    }

    @Override
    public void removeUserTokens(String username) {
        RememberMeToken token = repository.findByUsername(username);
        if (token != null) {
            repository.delete(token);
        }
    }
}