package com.st11.dbshow.controller;


import com.fasterxml.jackson.core.type.TypeReference;
import com.st11.dbshow.repository.RefObjectVO;
import com.st11.dbshow.service.ApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.text.DateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;


@Controller
public class SystemInfoController {

    @Autowired
    private ApiService apiService;

    private String getCurrentTime() {
        DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG);
        String formattedDate = dateFormat.format(new Date());

        return formattedDate;
    }

    @RequestMapping(value = "referencedObject")
    public String index(HttpServletRequest request, Model model) throws IOException{

        final String apiMethod = "referencedObject";

        HashMap<String, Object> inParams = new HashMap<>();
        Collection <RefObjectVO> modelCollection = null;

        String dbName = request.getParameter("dbName");
        String objectType = request.getParameter("objectType");
        String owner = request.getParameter("owner");
        String objectName = request.getParameter("objectName");

        if (dbName == null || dbName =="") {dbName= "OMSTGDB";}
        if (objectType == null || objectType =="") {objectType= "TABLE";}
        if (owner == null || owner =="") {owner= "";}
        if (objectName == null || objectName =="") {objectName= "";}

        inParams.put("dbName", dbName);
        inParams.put("owner", owner);
        inParams.put("commandType", objectType);
        inParams.put("objectName", objectName);

        System.out.println("/referencedObject/ " + inParams.toString());

        if (owner != "" && objectName != "") {
            modelCollection = apiService.getApiModels(apiMethod, new TypeReference<Collection<RefObjectVO>>() {
            }, dbName, objectType, owner, objectName);
        }

        model.addAttribute("serverTime", getCurrentTime());
        model.addAttribute("inParams", inParams);
        model.addAttribute("includedContent", "content/sqlApplication :: sqlData");

        model.addAttribute("model", modelCollection);

        return "content/referencedObject";
    }

}
