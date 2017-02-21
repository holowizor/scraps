package com.marekkwiecien.service;

import com.marekkwiecien.model.Scrap;
import com.marekkwiecien.repo.ScrapRepository;
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
public class ScrapService {

    @Autowired
    private ScrapRepository scrapRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    public List<Scrap> getScraps(final String contextId) {

        List<Scrap> list = scrapRepository.findByContext(contextId);
        if (list == null || list.size() == 0) {
            list = new ArrayList<>();

            list.add(createNewScrap(contextId, "one"));
        }

        return list;
    }

    public Scrap getActiveScrap(final List<Scrap> scraps) {
        for (final Scrap s : scraps) {
            if (s.isActive()) return s;
        }
        return setActive(scraps.get(0));
    }

    public Scrap createNewScrap(final String contextId, final String name) {
        final Scrap scrap = new Scrap();
        scrap.setContext(contextId);
        scrap.setName(name);
        scrapRepository.save(scrap);

        return setActive(scrap);
    }

    public Scrap setActive(final Scrap scrap) {
        // multi just in case
        mongoTemplate.updateMulti(Query.query(Criteria.where("active").is(true)).addCriteria(Criteria.where("context").is(scrap.getContext())),
                new Update().set("active", false), Scrap.class);
        mongoTemplate.updateFirst(Query.query(Criteria.where("id").is(scrap.getId())),
                new Update().set("active", true), Scrap.class);

        scrap.setActive(true);
        return scrap;
    }
}
