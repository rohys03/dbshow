package com.st11.dbshow.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.st11.dbshow.repository.AreaInfoVO;
import com.st11.dbshow.repository.DaTableVO;
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
import java.util.Collection;
import java.util.HashMap;

import static com.st11.dbshow.common.DbShow.*;


@Controller
public class SqlDistributionController {

    @Autowired
    private ApiService apiService;

/*
    @RequestMapping(value = "logicalArea", method = RequestMethod.GET)
    public String logicalArea (
            @RequestParam(value = "logicalAreaCd", required = false) final String logicalAreaCd,
            @RequestParam(value = "logicalAreaCd2", required = false) final String logicalAreaCd2,
            Model model) throws URISyntaxException, IOException {
//        System.out.println("[Request ApiService Param] : " + request.getRequestURL().toString() + "/" + getParameterMap(request).toString());
        model.addAttribute("serverTime", getCurrentTime());

        final String apiMethod = "logicalArea";

        HashMap<String, String> inParam = new HashMap<>();

        if (!isNullOrEmpty(logicalAreaCd)) inParam.put("logicalAreaCd", logicalAreaCd.toUpperCase());
        if (!isNullOrEmpty(logicalAreaCd2)) inParam.put("logicalAreaCd2", logicalAreaCd2.toUpperCase());


        Collection<AreaInfoVO> modelCollection = null;
        modelCollection = apiService.getApiModels(apiMethod, new TypeReference<Collection<AreaInfoVO>>() {}
                , inParam);
        model.addAttribute("model", modelCollection);
        return "content/logicalArea";
    }
*/

    @RequestMapping(value = "dbDistributionStats", method = RequestMethod.GET)
    public String dbDistributionKpiWeekly (
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
    @RequestMapping(value = "dbDistributionStats2", method = RequestMethod.GET)
    public String dbDistributionKpiDaily (
            @RequestParam(value = "cnt", required = false, defaultValue = "30") String cnt,
            Model model) throws URISyntaxException, IOException {
//        System.out.println("[Request ApiService Param] : " + request.getRequestURL().toString() + "/" + getParameterMap(request).toString());
        model.addAttribute("serverTime", getCurrentTime());

        final String apiMethod = "dbKpiStatDaily";

        HashMap<String, String> inParam = new HashMap<>();

        if (!isNullOrEmpty(cnt)) inParam.put("cnt", cnt);

        Collection<DbKpiStatVO> modelCollection = null;
        modelCollection = apiService.getApiModels(apiMethod, new TypeReference<Collection<DbKpiStatVO>>() {
                }
                , inParam);
        model.addAttribute("model", modelCollection);
        return "content/dbDistributionStats2";
    }
}
