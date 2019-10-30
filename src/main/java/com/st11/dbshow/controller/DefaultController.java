package com.st11.dbshow.controller;


import com.fasterxml.jackson.core.type.TypeReference;
import com.st11.dbshow.repository.DbKpiStatVO;
import com.st11.dbshow.service.ApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.URISyntaxException;
import java.text.DateFormat;
import java.util.*;

import static com.st11.dbshow.common.DbShow.getCurrentTime;
import static com.st11.dbshow.common.DbShow.getParameterMap;
import static com.st11.dbshow.common.DbShow.isNullOrEmpty;


@Controller
public class DefaultController {

    @Autowired
    ApiService apiService;

    @RequestMapping(value = {"", "/", "/index"})
    public String index(HttpServletRequest request, Model model) throws URISyntaxException, IOException{

        final String apiMethod = "dbKpiStatDaily";

        HashMap<String, String> inParam = new HashMap<>();
        inParam.put("cnt","1");

        Collection<DbKpiStatVO> modelCollection = null;
        modelCollection = apiService.getApiModels(apiMethod, new TypeReference<Collection<DbKpiStatVO>>() {
                }
                , inParam);
        System.out.println("modelCollection: " + modelCollection.toString());
        model.addAttribute("kpiStats", modelCollection);

        model.addAttribute("serverTime", getCurrentTime());

        return "index";
    }

}
