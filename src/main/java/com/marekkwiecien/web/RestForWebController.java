package com.marekkwiecien.web;

import com.marekkwiecien.model.Scrap;
import com.marekkwiecien.repo.ScrapRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by holow on 2/21/2017.
 */
@Controller
public class RestForWebController {

    @Autowired
    private ScrapRepository scrapRepository;

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
}