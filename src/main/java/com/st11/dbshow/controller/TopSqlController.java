package com.st11.dbshow.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.st11.dbshow.repository.DaStatMngVO;
import com.st11.dbshow.repository.SqlNameMappVO;
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
public class TopSqlController {

    @Autowired
    ApiService apiService;

    @RequestMapping(value = {"topSqlDayList"})
    public String topSqlDayList(
            @RequestParam(value = "clcyDy1", required = false) final String clcyDy1,
            @RequestParam(value = "clcyDy2", required = false) final String clcyDy2,
            @RequestParam(value = "orderString", required = false) final String orderString,
            Model model) throws IOException, URISyntaxException {
        final String apiMethod = "topSqlDayList";

        HashMap<String, String> inParam = new HashMap<>();

        if (!isNullOrEmpty(clcyDy1)) inParam.put("clcyDy1", clcyDy1.toUpperCase());
        if (!isNullOrEmpty(clcyDy2)) inParam.put("clcyDy2", clcyDy2.toUpperCase());
        if (!isNullOrEmpty(orderString)) inParam.put("orderString", orderString.toUpperCase());

        Collection<SqlAreaVO> modelCollection = null;

        if (!inParam.isEmpty()) {
            modelCollection = apiService.getApiModels(apiMethod, new TypeReference<Collection<SqlAreaVO>>() {
                    }
                    , inParam);
        }

        model.addAttribute("defaultDate", "20190315");
        model.addAttribute("model", modelCollection);
        return "content/topSqlDayList";
    }
}
