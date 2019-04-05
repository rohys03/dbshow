package com.st11.dbshow.controller;


import com.fasterxml.jackson.core.type.TypeReference;
import com.st11.dbshow.repository.DaSyncTableVO;
import com.st11.dbshow.repository.DaTableVO;
import com.st11.dbshow.repository.RefObjectVO;
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
import java.util.*;

import static com.st11.dbshow.common.DbShow.*;


@Controller
public class SystemInfoController {

    @Autowired
    private ApiService apiService;

    @RequestMapping(value = "daTables", method = RequestMethod.GET)
    public String daTables (
            @RequestParam(value = "dbName", required = false) String dbName,
            @RequestParam(value = "tableName", required = false) String tableName,
            @RequestParam(value = "logicalAreaCd1", required = false) final String logicalAreaCd1,
            @RequestParam(value = "logicalAreaCd2", required = false) final String logicalAreaCd2,
            Model model) throws URISyntaxException, IOException {
//        System.out.println("[Request ApiService Param] : " + request.getRequestURL().toString() + "/" + getParameterMap(request).toString());
        model.addAttribute("serverTime", getCurrentTime());

        final String apiMethod = "daTables";

        HashMap<String, String> inParam = new HashMap<>();

        if (!isNullOrEmpty(dbName)) inParam.put("dbName", dbName.toUpperCase());
        if (!isNullOrEmpty(tableName)) inParam.put("tableName", tableName.toUpperCase());
        if (!isNullOrEmpty(logicalAreaCd1)) inParam.put("logicalAreaCd1", logicalAreaCd1.toUpperCase());
        if (!isNullOrEmpty(logicalAreaCd2)) inParam.put("logicalAreaCd2", logicalAreaCd2.toUpperCase());

        Collection<DaTableVO> modelCollection = null;
        modelCollection = apiService.getApiModels(apiMethod, new TypeReference<Collection<DaTableVO>>() {}
                , inParam);
        model.addAttribute("model", modelCollection);
        return "content/daTables";
    }

    @RequestMapping(value = "referencedObject")
    public String referencedObject(
            @RequestParam(value = "dbName", required = false) String dbName,
            @RequestParam(value = "objectType", required = false) String objectType,
            @RequestParam(value = "owner", required = false) String owner,
            @RequestParam(value = "objectName", required = false) String objectName,
            HttpServletRequest request, Model model) throws IOException{
        final String apiMethod = "referencedObject";

        model.addAttribute("serverTime", getCurrentTime());

        if (!isNullOrEmpty(dbName) && !isNullOrEmpty(objectType) && !isNullOrEmpty(owner) && !isNullOrEmpty(objectName)) {
            Collection<RefObjectVO> modelCollection = null;
            modelCollection = apiService.getApiModels(apiMethod, new TypeReference<Collection<RefObjectVO>>() {
                    }
                    , dbName.toUpperCase(), objectType.toUpperCase(), owner.toUpperCase(), objectName.toUpperCase());
            model.addAttribute("model", modelCollection);
        }

        return "content/referencedObject";
    }

    @RequestMapping(value = "daSyncData", method = RequestMethod.GET)
    public String daSyncData(
            @RequestParam(value = "tableName", required = false) String tableName,
            @RequestParam(value = "hostName", required = false) String hostName,
            Model model) throws URISyntaxException, IOException {

        final String apiMethod = "daSyncData";

        HashMap<String, String> inParam = new HashMap<>();

        if (!isNullOrEmpty(tableName)) inParam.put("tableName", tableName.toUpperCase());
        if (!isNullOrEmpty(hostName)) inParam.put("hostName", hostName.toUpperCase());

        Collection<DaSyncTableVO> modelCollection = null;
        modelCollection = apiService.getApiModels(apiMethod, new TypeReference<Collection<DaSyncTableVO>>() {
                }
                , inParam);
        model.addAttribute("model", modelCollection);

//        if (!isNullOrEmpty(tableName)) {
//        }

        return "content/daSyncData";
    }

}
