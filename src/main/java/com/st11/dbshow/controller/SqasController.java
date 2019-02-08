package com.st11.dbshow.controller;


import com.fasterxml.jackson.core.type.TypeReference;
import com.st11.dbshow.service.ApiService;
import com.st11.dbshow.repository.SqlAreaVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.text.DateFormat;
import java.util.*;

import static com.st11.dbshow.util.DbShow.*;

@Controller
public class SqasController {

    @Autowired
    ApiService apiService;

    @RequestMapping(value = "dashboard")
    public String dashboard(HttpServletRequest request, Model model) {

        model.addAttribute("serverTime", getCurrentTime());

        return "content/dashboard";
    }

    @RequestMapping(value = {"sqlListByObject"})
    public String sqlListByObject(HttpServletRequest request, Model model) throws IOException{

        final String apiMethod = "sqlApplicationList";

        Map<String, String> inParams = getParameterMap(request);
        Collection <SqlAreaVO> modelCollection = null;

        String dbName = inParams.getOrDefault("dbName", "dbName").toUpperCase();
        String commandType = inParams.getOrDefault("commandType", "commandType").toUpperCase();
        String owner = inParams.getOrDefault("owner", "owner").toUpperCase();
        String name = inParams.getOrDefault("name", "name").toUpperCase();

        System.out.println("/sqlListByObject/ " + inParams.toString());

        if (owner != "" && name != "") {
            modelCollection = apiService.getApiModels(apiMethod, new TypeReference<Collection<SqlAreaVO>>() {
            }, dbName, commandType, owner, name);
        }

        model.addAttribute("serverTime", getCurrentTime());
        model.addAttribute("model", modelCollection);

        return "content/sqlApplication";
    }

    @RequestMapping(value = {"sqlDetail"})
    public String sqlDetail(HttpServletRequest request, Model model) throws IOException {

        final String apiMethod = "sqlDetail";

        Map<String, String> inParams = getParameterMap(request);
        Collection <SqlAreaVO> modelCollection = null;

        String dbName = inParams.getOrDefault("dbName", "dbName").toUpperCase();
        String searchType = inParams.getOrDefault("searchType", "searchType").toUpperCase();
        String sqlString = inParams.getOrDefault("sqlString", "sqlString").toUpperCase();

        if (searchType != "" && sqlString != "") {
            modelCollection = apiService.getApiModels(apiMethod, new TypeReference<Collection<SqlAreaVO>>() {
            }, dbName, searchType, sqlString);
        }

        model.addAttribute("serverTime", getCurrentTime());
        model.addAttribute("model", modelCollection);

        return "content/sqlDetail";
    }

}
