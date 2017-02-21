package com.marekkwiecien.repo;

import com.marekkwiecien.model.Context;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * Created by holow on 2/21/2017.
 */
public interface ContextRepository extends MongoRepository<Context, String> {

    List<Context> findByOwner(final String owner);
}
