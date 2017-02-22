package com.marekkwiecien.repo;

import com.marekkwiecien.model.Scrap;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * Created by holow on 2/21/2017.
 */
public interface ScrapRepository extends MongoRepository<Scrap, String> {

    List<Scrap> findByContext(final String contextId);

    void deleteByContext(final String contextId);
}
