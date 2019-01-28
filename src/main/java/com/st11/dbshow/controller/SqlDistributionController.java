package com.st11.dbshow.controller;


import com.st11.dbshow.repository.SqlAreaVO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.text.DateFormat;
import java.util.Date;


@Controller
public class SqlDistributionController {

    private String getCurrentTime() {
        DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG);
        String formattedDate = dateFormat.format(new Date());

        return formattedDate;
    }

    @RequestMapping(value = "sqlDistributionKpi")
    public String index(HttpServletRequest request, Model model) {

        model.addAttribute("serverTime", getCurrentTime());
        return "content/dashboard";
    }

}
