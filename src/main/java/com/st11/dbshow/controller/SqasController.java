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


@Controller
public class SqasController {

    @Autowired
    ApiService apiService;

    private String getCurrentTime() {
        DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG);
        String formattedDate = dateFormat.format(new Date());

        return formattedDate;
    }

    @RequestMapping(value = {"", "index"})
    public String index(HttpServletRequest request, Model model) {

        model.addAttribute("serverTime", getCurrentTime());
        model.addAttribute("includedContent", "content/dashboard :: dashboardData");
        return "index";
    }

    @RequestMapping(value = {"sqlListByObject"})
    public String sqlListByParsingSchemaName(HttpServletRequest request, Model model) throws IOException{

        final String apiMethod = "sqlApplicationList";

        HashMap<String, Object> inParams = new HashMap<>();
        Collection <SqlAreaVO> modelCollection = null;

        String dbName = request.getParameter("dbName");
        String commandType = request.getParameter("commandType");
        String owner = request.getParameter("owner");
        String name = request.getParameter("name");

        if (dbName == null || dbName =="") {dbName= "OMSTGDB";}
        if (commandType == null || commandType =="") {commandType= "SELECT";}
        if (owner == null || owner =="") {owner= "";}
        if (name == null || name =="") {name= "";}

        inParams.put("dbName", dbName);
        inParams.put("owner", owner);
        inParams.put("commandType", commandType);
        inParams.put("name", name);

        System.out.println("/sqlListByObject/ " + inParams.toString());

        if (owner != "" && name != "") {
            modelCollection = apiService.getApiModels(apiMethod, new TypeReference<Collection<SqlAreaVO>>() {
            }, dbName, commandType, owner, name);
        }

        model.addAttribute("serverTime", getCurrentTime());
        model.addAttribute("inParams", inParams);
        model.addAttribute("includedContent", "content/sqlApplication :: sqlData");

        model.addAttribute("model", modelCollection);
        return "index";
    }

    @RequestMapping(value = {"sqlDetail"})
    public String sqlDetail(HttpServletRequest request, Model model) throws IOException {

        final String apiMethod = "sqlDetail";
        HashMap<String, Object> inParams = new HashMap<>();
        Collection<SqlAreaVO> modelCollection = null;

        String dbName = request.getParameter("dbName");
        String searchType = request.getParameter("searchType");
        String sqlString = request.getParameter("sqlString");

        if(dbName == null || dbName =="") {dbName = "OMSTGDB";}
        if(searchType == null || searchType =="") {searchType = "SQLID";}
        if(sqlString == null || sqlString =="") {sqlString = "";}

        inParams.put("dbName", dbName);
        inParams.put("searchType", searchType);
        inParams.put("sqlString", sqlString);

        if (searchType != "" && sqlString != "") {
            modelCollection = apiService.getApiModels(apiMethod, new TypeReference<Collection<SqlAreaVO>>() {
            }, dbName, searchType, sqlString);
        }

        model.addAttribute("serverTime", getCurrentTime());
        model.addAttribute("inParam", inParams);
        model.addAttribute("includedContent", "content/sqlDetail :: sqlData");
        model.addAttribute("model", modelCollection);

        return "index";
    }
}
