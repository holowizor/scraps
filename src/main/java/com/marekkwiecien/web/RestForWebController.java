package com.marekkwiecien.web;

import com.marekkwiecien.model.Context;
import com.marekkwiecien.model.Scrap;
import com.marekkwiecien.model.WebUser;
import com.marekkwiecien.repo.ContextRepository;
import com.marekkwiecien.repo.ScrapRepository;
import com.marekkwiecien.service.ContextService;
import com.marekkwiecien.service.ScrapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by holow on 2/21/2017.
 */
@Controller
public class RestForWebController {

    @Autowired
    private ScrapRepository scrapRepository;

    @Autowired
    private ScrapService scrapService;

    @Autowired
    private ContextRepository contextRepository;

    @Autowired
    private ContextService contextService;

    @RequestMapping("/loadScraps")
    @ResponseBody
    public List<Scrap> loadScraps(@RequestParam("context") final String context) {
        return scrapService.getScraps(context);
    }

    @RequestMapping("/createScrap")
    @ResponseBody
    public Scrap createScrap(@RequestParam("context") final String context,
                             @RequestParam("name") final String name) {

        final Scrap scrap = scrapService.createNewScrap(context, name);
        return scrap;
    }

    @RequestMapping("/saveScrap")
    @ResponseBody
    public Scrap saveScrap(@RequestParam("id") final String scrapId,
                           @RequestParam("content") final String content) {

        final Scrap scrap = scrapRepository.findOne(scrapId);
        if (scrap != null) {
            scrap.setContent(content);
            scrapRepository.save(scrap);
        }

        return scrap;
    }

    @RequestMapping("/setActiveScrap")
    @ResponseBody
    public Scrap setActiveScrap(@RequestParam("scrap") final String scrapId) {
        final Scrap scrap = scrapRepository.findOne(scrapId);
        if (scrap != null) {
            return scrapService.setActive(scrap);
        }
        return scrap;
    }

    @RequestMapping("/createContext")
    @ResponseBody
    public Context createContext(@RequestParam("name") final String name) {
        final WebUser user = ControllerHelper.getLoggedInUser();
        final Context context = contextService.createNewContext(user.getId(), name, "#ffffff");
        return context;
    }

    @RequestMapping("/setActiveContext")
    @ResponseBody
    public Context setActiveContext(@RequestParam("context") final String contextId) {
        final Context context = contextRepository.findOne(contextId);
        if (context != null) {
            return contextService.setActive(context);
        }
        return context;
    }
}