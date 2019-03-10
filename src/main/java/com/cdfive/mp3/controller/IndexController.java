package com.cdfive.mp3.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author cdfive
 */
@Controller
public class IndexController {

    @RequestMapping("/")
    public String welcome() {
        return "index";
    }
}
