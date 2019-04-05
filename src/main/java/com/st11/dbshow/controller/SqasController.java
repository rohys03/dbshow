package com.st11.dbshow.controller;


import com.fasterxml.jackson.core.type.TypeReference;
import com.st11.dbshow.repository.SqlNameVO;
import com.st11.dbshow.service.ApiService;
import com.st11.dbshow.repository.SqlAreaVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.URISyntaxException;
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

        String[] listItems = {"ALL", "SELECT", "DML", "OTHERS"};

        model.addAttribute("listItems", listItems);

        if (!isNullOrEmpty(commandType) && !isNullOrEmpty(owner) && !isNullOrEmpty(name)) {
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

        if (!isNullOrEmpty(searchType) && !isNullOrEmpty(sqlString)) {
            Collection<SqlAreaVO> modelCollection = null;
            modelCollection = apiService.getApiModels(apiMethod, new TypeReference<Collection<SqlAreaVO>>() {
            }, dbName, searchType, sqlString);
            model.addAttribute("model", modelCollection);
        }

        return "content/sqlDetail";
    }

    @RequestMapping(value = {"sqlName"})
    public String sqlName(
            @RequestParam(value = "clctDy", defaultValue = "20190318") final String clctDy,
            @RequestParam(value = "sqlName", required = false) final String sqlName,
            @RequestParam(value = "logicalAreaCd1", required = false) final String logicalAreaCd1,
            @RequestParam(value = "logicalAreaCd2", required = false) final String logicalAreaCd2,
            Model model) throws IOException, URISyntaxException {
        final String apiMethod = "sqlName";

        HashMap<String, String> inParam = new HashMap<>();

        if (!isNullOrEmpty(clctDy)) inParam.put("clctDy", clctDy.toUpperCase());
        if (!isNullOrEmpty(sqlName)) inParam.put("sqlName", sqlName.toUpperCase());
        if (!isNullOrEmpty(logicalAreaCd1)) inParam.put("logicalAreaCd1", logicalAreaCd1.toUpperCase());
        if (!isNullOrEmpty(logicalAreaCd2)) inParam.put("logicalAreaCd2", logicalAreaCd2.toUpperCase());

        Collection<SqlNameVO> modelCollection = null;
        modelCollection = apiService.getApiModels(apiMethod, new TypeReference<Collection<SqlNameVO>>() {}
                , inParam);
        model.addAttribute("model", modelCollection);
        return "content/sqlName";
    }

}
