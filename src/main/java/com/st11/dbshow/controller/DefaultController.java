package com.st11.dbshow.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.text.DateFormat;
import java.util.*;

import static com.st11.dbshow.common.DbShow.getParameterMap;


@Controller
public class DefaultController {

    private String getCurrentTime() {
        DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG);
        String formattedDate = dateFormat.format(new Date());

        return formattedDate;
    }

    @RequestMapping(value = {"", "/", "/index"})
    public String index(HttpServletRequest request, Model model) throws IOException{
//        System.out.println("[Request ApiService Param] : " + request.getRequestURL().toString() + "/" + getParameterMap(request).toString());
        model.addAttribute("serverTime", getCurrentTime());

        return "index";
    }

}
