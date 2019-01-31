package com.st11.dbshow.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

import static com.st11.dbshow.util.DbShow.*;


@Controller
public class SqlDistributionController {

    @RequestMapping(value = "sqlDistributionKpi")
    public String index(HttpServletRequest request, Model model) {

        model.addAttribute("serverTime", getCurrentTime());
        return "content/dashboard";
    }

}
