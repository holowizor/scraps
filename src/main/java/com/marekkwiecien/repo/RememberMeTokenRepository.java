package com.marekkwiecien.repo;

import com.marekkwiecien.model.RememberMeToken;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by holow on 2/20/2017.
 */
public interface RememberMeTokenRepository extends MongoRepository<RememberMeToken, String> {

    RememberMeToken findBySeries(String series);

    RememberMeToken findByUsername(String username);

}
