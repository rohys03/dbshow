package com.st11.dbshow.controller;


import com.st11.dbshow.service.SqasService;
import com.st11.dbshow.repository.SqlAreaVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.text.DateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
public class SqasController {

    @Autowired
    SqasService sqasService;

    private String getCurrentTime() {

        Date date = new Date();
        DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG);

        String formattedDate = dateFormat.format(date);

        return formattedDate;

    }

    @RequestMapping(value = {"sqlListByObject"})
    public String sqlListByParsingSchemaName(HttpServletRequest request, Model model) {

        String owner = request.getParameter("owner");
        String name = request.getParameter("name");
        String commandType = request.getParameter("commandType");
        List<SqlAreaVO> sqlareaList = null;
        System.out.println("/sqlListByObject/owner:"+ owner + "/name:" + name + "/commandType:" + commandType);

        HashMap<String, Object> inParam = new HashMap<>();
        inParam.put("owner", owner);
        inParam.put("name", name);
        inParam.put("commandType", commandType);


        if (name != null) {
            sqlareaList = sqasService.getSqlAreaList(inParam);
        } else {
            sqlareaList = sqasService.getSqlAreaListAll();
        }

        model.addAttribute("serverTime", getCurrentTime());
        model.addAttribute("sqlareaList", sqlareaList);
        model.addAttribute("inParam", inParam);

        return "sqlListByObject";
    }
}
