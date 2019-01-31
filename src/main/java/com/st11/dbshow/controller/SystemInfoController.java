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
import java.util.*;

import static com.st11.dbshow.util.DbShow.*;


@Controller
public class SystemInfoController {

    @Autowired
    private ApiService apiService;

    @RequestMapping(value = "referencedObject")
    public String index(HttpServletRequest request, Model model) throws IOException{

        final String apiMethod = "referencedObject";

        Map<String, String> inParams = getParameterMap(request);

        Collection <RefObjectVO> modelCollection = null;

        String dbName = inParams.getOrDefault("dbName", "dbName");
        String objectType = inParams.getOrDefault("objectType", "objectType");
        String owner = inParams.getOrDefault("owner", "owner");
        String objectName = inParams.getOrDefault("objectName", "objectName");

        System.out.println("/referencedObject/ " + inParams.toString());

        if (owner != "" && objectName != "") {
            modelCollection = apiService.getApiModels(apiMethod, new TypeReference<Collection<RefObjectVO>>() {
            }, dbName, objectType, owner, objectName);
        }

        model.addAttribute("serverTime", getCurrentTime());

        model.addAttribute("model", modelCollection);

        return "content/referencedObject";
    }

}
