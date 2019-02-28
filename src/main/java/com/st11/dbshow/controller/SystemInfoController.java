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
import java.util.*;

import static com.st11.dbshow.common.DbShow.*;


@Controller
public class SystemInfoController {

    @Autowired
    private ApiService apiService;

    @RequestMapping(value = "daTable", method = RequestMethod.GET)
    public String daTable(
            @RequestParam(value = "tableName", required = false) String tableName,
            HttpServletRequest request, Model model) throws IOException{
//        System.out.println("[Request ApiService Param] : " + request.getRequestURL().toString() + "/" + getParameterMap(request).toString());
        model.addAttribute("serverTime", getCurrentTime());

        final String apiMethod = "daTable";

        if (!isNullOrEmpty(tableName)) {
            Collection<DaTableVO> modelCollection = null;
            modelCollection = apiService.getApiModels(apiMethod, new TypeReference<Collection<DaTableVO>>() {
                    }
                    , tableName.toUpperCase());
            model.addAttribute("model", modelCollection);
        }

        return "content/daTable";
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
            HttpServletRequest request, Model model) throws IOException{
//        System.out.println("[Request ApiService Param] : " + request.getRequestURL().toString() + "/" + getParameterMap(request).toString());
        model.addAttribute("serverTime", getCurrentTime());

        final String apiMethod = "daSyncData";

        if (!isNullOrEmpty(tableName)) {
            Collection<DaSyncTableVO> modelCollection = null;
            modelCollection = apiService.getApiModels(apiMethod, new TypeReference<Collection<DaSyncTableVO>>() {
                    }
                    , tableName.toUpperCase());
            model.addAttribute("model", modelCollection);
        }

        return "content/daSyncData";
    }

}
