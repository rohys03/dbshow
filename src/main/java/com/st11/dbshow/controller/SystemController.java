package com.st11.dbshow.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.st11.dbshow.repository.DaTableVO;
import com.st11.dbshow.repository.DbKpiStatVO;
import com.st11.dbshow.service.ApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.HashMap;

import static com.st11.dbshow.common.DbShow.getCurrentTime;
import static com.st11.dbshow.common.DbShow.isNullOrEmpty;

@Controller
public class SystemController {

    @Autowired
    private ApiService apiService;

    @RequestMapping(value = "dbDistributionStats", method = RequestMethod.GET)
    public String daTables (
            @RequestParam(value = "cnt", required = false, defaultValue = "30") String cnt,
            Model model) throws URISyntaxException, IOException {
//        System.out.println("[Request ApiService Param] : " + request.getRequestURL().toString() + "/" + getParameterMap(request).toString());
        model.addAttribute("serverTime", getCurrentTime());

        final String apiMethod = "dbKpiStatWeekly";

        HashMap<String, String> inParam = new HashMap<>();

        if (!isNullOrEmpty(cnt)) inParam.put("cnt", cnt);

        Collection<DbKpiStatVO> modelCollection = null;
        modelCollection = apiService.getApiModels(apiMethod, new TypeReference<Collection<DbKpiStatVO>>() {
                }
                , inParam);
        model.addAttribute("model", modelCollection);
        return "content/dbDistributionStats";
    }
}
