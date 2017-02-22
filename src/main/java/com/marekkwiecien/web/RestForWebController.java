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

        final Scrap scrap = scrapService.createNew(context, name);
        return scrap;
    }

    @RequestMapping("/saveScrap")
    @ResponseBody
    public Scrap saveScrap(@RequestParam("scrap") final String scrapId,
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

    @RequestMapping("/deleteScrap")
    @ResponseBody
    public Scrap deleteScrap(@RequestParam("scrap") final String scrapId) {
        final Scrap scrap = scrapService.delete(scrapId);
        return scrap;
    }

    @RequestMapping("/updateScrap")
    @ResponseBody
    public Scrap updateScrap(@RequestParam("scrap") final String scrapId, @RequestParam("name") final String name) {
        final Scrap scrap = scrapService.update(scrapId, name);
        return scrap;
    }

    @RequestMapping("/loadContexts")
    @ResponseBody
    public List<Context> loadContexts() {
        final WebUser user = ControllerHelper.getLoggedInUser();
        return contextService.getUserContexts(user.getId());
    }

    @RequestMapping("/createContext")
    @ResponseBody
    public Context createContext(@RequestParam("name") final String name) {
        final WebUser user = ControllerHelper.getLoggedInUser();
        final Context context = contextService.createNew(user.getId(), name, "#ffffff");
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

    @RequestMapping("/deleteContext")
    @ResponseBody
    public Context deleteContext(@RequestParam("context") final String contextId) {
        final Context context = contextService.delete(contextId);

        // find active context
        final WebUser user = ControllerHelper.getLoggedInUser();
        final Context active = contextService.getActive(contextService.getUserContexts(user.getId()));
        // and make sure there is at least one scrap and it is active
        scrapService.getActive(scrapService.getScraps(active.getId()));

        return context;
    }

    @RequestMapping("/updateContext")
    @ResponseBody
    public Context updateContext(@RequestParam("context") final String contextId, @RequestParam("name") final String name) {
        final Context context = contextService.update(contextId, name);
        return context;
    }
}