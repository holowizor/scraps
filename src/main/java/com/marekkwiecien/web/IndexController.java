package com.marekkwiecien.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by holow on 2/20/2017.
 */
@Controller
public class IndexController {

    @RequestMapping("/")
    public String index(final Model model) {
        return "index";
    }
}
