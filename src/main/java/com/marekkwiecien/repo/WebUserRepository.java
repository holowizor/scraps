package com.marekkwiecien.repo;

import com.marekkwiecien.model.WebUser;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by holow on 2/20/2017.
 */
public interface WebUserRepository extends MongoRepository<WebUser, String> {

    WebUser findOneByUsername(final String username);

}
