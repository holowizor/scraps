package com.marekkwiecien.web;

import com.marekkwiecien.model.Context;
import com.marekkwiecien.model.Scrap;
import com.marekkwiecien.model.WebUser;
import com.marekkwiecien.service.ContextService;
import com.marekkwiecien.service.ScrapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * Created by holow on 2/20/2017.
 */
@Controller
public class IndexController {

    @Autowired
    private ContextService contextService;

    @Autowired
    private ScrapService scrapService;

    @RequestMapping("/")
    public String index(final Model model) {
        final WebUser user = ControllerHelper.getLoggedInUser();

        final List<Context> contexts = contextService.getUserContexts(user.getId());
        final Context context = contextService.getActiveContext(contexts);

        final List<Scrap> scraps = scrapService.getScraps(context.getId());
        final Scrap scrap = scrapService.getActiveScrap(scraps);

        model.addAttribute("contexts", contexts);
        model.addAttribute("activeContext", context);

        model.addAttribute("scraps", scraps);
        model.addAttribute("activeScrap", scrap);

        return "index";
    }
}
