package com.st11.dbshow.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.st11.dbshow.repository.AreaInfoVO;
import com.st11.dbshow.repository.DaTableVO;
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
import java.util.Collection;
import java.util.HashMap;

import static com.st11.dbshow.common.DbShow.*;


@Controller
public class SqlDistributionController {

    @Autowired
    private ApiService apiService;

    @RequestMapping(value = "sqlDistributionKpi")
    public String index(HttpServletRequest request, Model model) {
        model.addAttribute("serverTime", getCurrentTime());

        return "content/dashboard";
    }

    @RequestMapping(value = "logicalArea", method = RequestMethod.GET)
    public String daTables (
            @RequestParam(value = "logicalAreaCd1", required = false) final String logicalAreaCd1,
            @RequestParam(value = "logicalAreaCd2", required = false) final String logicalAreaCd2,
            Model model) throws URISyntaxException, IOException {
//        System.out.println("[Request ApiService Param] : " + request.getRequestURL().toString() + "/" + getParameterMap(request).toString());
        model.addAttribute("serverTime", getCurrentTime());

        final String apiMethod = "logicalArea";

        HashMap<String, String> inParam = new HashMap<>();

        if (!isNullOrEmpty(logicalAreaCd1)) inParam.put("logicalAreaCd1", logicalAreaCd1.toUpperCase());
        if (!isNullOrEmpty(logicalAreaCd2)) inParam.put("logicalAreaCd2", logicalAreaCd2.toUpperCase());

        Collection<AreaInfoVO> modelCollection = null;
        modelCollection = apiService.getApiModels(apiMethod, new TypeReference<Collection<AreaInfoVO>>() {}
                , inParam);
        model.addAttribute("model", modelCollection);
        return "content/logicalArea";
    }



}
