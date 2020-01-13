package com.st11.dbshow.controller;


import com.fasterxml.jackson.core.type.TypeReference;
import com.st11.dbshow.common.DbShow;
import com.st11.dbshow.config.SideBarConfig;
import com.st11.dbshow.repository.DaStatMngAllVO;
import com.st11.dbshow.repository.DbKpiStatVO;
import com.st11.dbshow.service.ApiService;
import com.sun.media.jfxmedia.logging.Logger;
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
    SideBarConfig sideBarConfig;

    @Autowired
    ApiService apiService;

    @RequestMapping(value = {"", "/", "/index"})
    public String index(HttpServletRequest request, Model model) throws URISyntaxException, IOException{

        HashMap<String, String> inParam = new HashMap<>();

        inParam.put("cnt","1");

        Collection<DbKpiStatVO> kpiStatCollection = null;
        kpiStatCollection = apiService.getApiModels("dbKpiStatDaily", new TypeReference<Collection<DbKpiStatVO>>() {
                }
                , inParam);

//        System.out.println("kpiStatCollection: " + kpiStatCollection.toString());

        Collection<DaStatMngAllVO> daStatMngAllCollection = null;
        daStatMngAllCollection = apiService.getApiModels("dbshow/getDaStatMngAll", new TypeReference<Collection<DaStatMngAllVO>>() {
                }
                );
//        System.out.println("dbshow/daStatMngCollection: " + daStatMngAllCollection.toString());


        model.addAttribute("kpiStats", kpiStatCollection);
        model.addAttribute("daStatMngAll", daStatMngAllCollection);

        model.addAttribute("serverTime", getCurrentTime());

        System.out.println(sideBarConfig.getMenuA_A().toString());
//        System.out.println(sideBarConfig.getMenuA_B());

        return "index";
    }

}
