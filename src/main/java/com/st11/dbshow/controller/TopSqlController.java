package com.st11.dbshow.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.st11.dbshow.repository.*;
import com.st11.dbshow.service.ApiService;
import com.st11.dbshow.service.DbShowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;

import static com.st11.dbshow.common.DbShow.*;

@Controller
public class TopSqlController {

    @Autowired
    ApiService apiService;
    @Autowired
    DbShowService dbShowService;

    @RequestMapping(value = {"topSqlDayList"})
    public String topSqlDayList(
            @RequestParam(value = "dbId", required = false, defaultValue = "1") final String dbId,
            @RequestParam(value = "clctDy1", required = false) String clctDy1,
            @RequestParam(value = "clctDy2", defaultValue = "") String clctDy2,
            @RequestParam(value = "orderString", required = false, defaultValue = "EXEC_DIFF") final String orderString,
            @RequestParam(value = "ascending", required = false, defaultValue = "DESC") final String ascending,
            Model model) throws IOException, URISyntaxException {
        final String apiMethod = "topSqlDayList";

        HashMap<String, String> inParam = new HashMap<>();

        Date from = new Date();
        SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd");
        String defaultClctDy1 = transFormat.format(from);

        if (!isNullOrEmpty(dbId)) inParam.put("dbId", dbId.toUpperCase());
        if (!isNullOrEmpty(clctDy1)) {
            inParam.put("clctDy1", clctDy1.replace("-",""));
        } else {
            inParam.put("clctDy1", defaultClctDy1.replace("-",""));
        }

        if (!isNullOrEmpty(clctDy2)) {
            inParam.put("clctDy2", clctDy2.replace("-",""));
        } else {
            inParam.put("clctDy2", clctDy1.replace("-",""));
        }
        if (!isNullOrEmpty(orderString)) inParam.put("orderString", orderString.toUpperCase());
        if (!isNullOrEmpty(ascending)) inParam.put("ascending", ascending.toUpperCase());

        Collection<SqlAreaDiffVO> modelCollection = null;

        if (!inParam.isEmpty()) {
            modelCollection = apiService.getApiModels(apiMethod, new TypeReference<Collection<SqlAreaDiffVO>>() {
                    }
                    , inParam);
        }

        model.addAttribute("dbList", dbShowService.getDaDbList("Y"));

        String[] orderStringList = {"EXEC_DIFF", "BGET_DIFF", "CPU_DIFF", "ELAP_DIFF"};
        model.addAttribute("orderStringList", orderStringList);

        model.addAttribute("defaultDate", LocalDateTime.now());
        model.addAttribute("dbId", dbId);
        model.addAttribute("model", modelCollection);

        return "content/topSqlDayList";
    }

}
