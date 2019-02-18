package com.st11.dbshow.controller;


import com.fasterxml.jackson.core.type.TypeReference;
import com.st11.dbshow.service.ApiService;
import com.st11.dbshow.repository.SqlAreaVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.*;

import static com.st11.dbshow.common.DbShow.*;

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
    public String sqlListByObject(
            @RequestParam(value = "dbName", required = false) String dbName,
            @RequestParam(value = "commandType", required = false) String commandType,
            @RequestParam(value = "owner", required = false) String owner,
            @RequestParam(value = "name", required = false) String name,
            HttpServletRequest request, Model model) throws IOException{
        final String apiMethod = "sqlApplicationList";

        model.addAttribute("serverTime", getCurrentTime());
        if (!isNullOrEmpty(dbName) && !isNullOrEmpty(commandType) && !isNullOrEmpty(owner) && !isNullOrEmpty(name)) {
            Collection<SqlAreaVO> modelCollection = null;
            modelCollection = apiService.getApiModels(apiMethod, new TypeReference<Collection<SqlAreaVO>>() {
            }, dbName, commandType, owner, name);
            model.addAttribute("model", modelCollection);
        }

        return "content/sqlApplication";
    }

    @RequestMapping(value = {"sqlDetail"})
    public String sqlDetail(
            @RequestParam(value = "dbName", required = false) String dbName,
            @RequestParam(value = "searchType", required = false) String searchType,
            @RequestParam(value = "sqlString", required = false) String sqlString,
            HttpServletRequest request, Model model) throws IOException {
        final String apiMethod = "sqlDetail";

        model.addAttribute("serverTime", getCurrentTime());

        if (searchType != "" && sqlString != "") {
            Collection<SqlAreaVO> modelCollection = null;
            modelCollection = apiService.getApiModels(apiMethod, new TypeReference<Collection<SqlAreaVO>>() {
            }, dbName, searchType, sqlString);
            model.addAttribute("model", modelCollection);
        }

        return "content/sqlDetail";
    }

}
