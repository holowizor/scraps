package com.marekkwiecien.service;

import com.marekkwiecien.model.Context;
import com.marekkwiecien.repo.ContextRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by holow on 2/21/2017.
 */
@Service
public class ContextService {

    @Autowired
    private ContextRepository contextRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    public List<Context> getUserContexts(final String userId) {

        List<Context> list = contextRepository.findByOwner(userId);
        if (list == null || list.size() == 0) {
            list = new ArrayList<>();

            list.add(createNewContext(userId, "prime", "#ffffff"));
        }

        return list;
    }

    public Context getActiveContext(final List<Context> contexts) {
        for (final Context c : contexts) {
            if (c.isActive()) return c;
        }
        return setActive(contexts.get(0));
    }

    public Context createNewContext(final String userId, final String name, final String color) {
        final Context context = new Context();
        context.setOwner(userId);
        context.setName(name);
        context.setColor(color);
        contextRepository.save(context);

        return setActive(context);
    }

    public Context setActive(final Context context) {
        // multi just in case
        mongoTemplate.updateMulti(Query.query(Criteria.where("active").is(true)).addCriteria(Criteria.where("owner").is(context.getOwner())),
                new Update().set("active", false), Context.class);
        mongoTemplate.updateFirst(Query.query(Criteria.where("id").is(context.getId())),
                new Update().set("active", true), Context.class);

        context.setActive(true);
        return context;
    }
}
