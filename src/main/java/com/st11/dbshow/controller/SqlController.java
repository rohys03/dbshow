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

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.util.logging.Logger;
import java.util.HashMap;
import java.util.Collection;

import static com.st11.dbshow.common.DbShow.*;

@Controller
public class SqlController {
    private final Logger logger = Logger.getLogger(this.getClass().getName());

    @Autowired
    ApiService apiService;

    @Autowired
    DbShowService dbShowService;

    @RequestMapping(value = "dashboard")
    public String dashboard(HttpServletRequest request, Model model) {
        model.addAttribute("serverTime", getCurrentTime());

        return "content/dashboard";
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

    @RequestMapping(value = {"sqlNameDetail"})
    public String sqlNameDetail(
            @RequestParam(value = "clctDy", required = false) final String clctDy,
            @RequestParam(value = "dbId", required = false) final String dbId,
            @RequestParam(value = "sqlName", required = false) final String sqlName,
            @RequestParam(value = "sqlNameNo", required = false) final String sqlNameNo,
            Model model) throws IOException, URISyntaxException {
        final String apiMethod = "sqlNameStatsHist";
        final String apiMethod2 = "sqlNameMappSummary";
        final String apiMethod3 = "jpa/daSqlFullText";

        HashMap<String, String> inParam = new HashMap<>();

        if (!isNullOrEmpty(clctDy)) {
            inParam.put("clctDy", clctDy.replace("-",""));
        } else return "content/sqlNameDetail";
        if (!isNullOrEmpty(dbId)) inParam.put("dbId", dbId.toUpperCase());
        if (!isNullOrEmpty(sqlName)) inParam.put("sqlName", sqlName.toUpperCase());
        if (!isNullOrEmpty(sqlNameNo)) inParam.put("sqlNameNo", sqlNameNo.toUpperCase());

        System.out.println(inParam);

        Collection<SqlNameVO> modelCollection = null;

        if (inParam.containsKey("sqlName") || inParam.containsKey("sqlNameNo")) {
            modelCollection = apiService.getApiModels(apiMethod, new TypeReference<Collection<SqlNameVO>>() {
                    }
                    , inParam);
        }
        model.addAttribute("histModel", modelCollection);

        model.addAttribute("dbList", dbShowService.getDaDbList("Y"));

        try {
            inParam.clear();
            if (!isNullOrEmpty(dbId)) inParam.put("dbId", dbId.toUpperCase());
            if (!isNullOrEmpty(sqlName)) inParam.put("sqlName", sqlName.toUpperCase());
            if (!isNullOrEmpty(sqlNameNo)) inParam.put("sqlNameNo", sqlNameNo.toUpperCase());
            System.out.println("inParam" + inParam.toString());
            SqlNameMappVO sqlNameMappVO = apiService.getApiModel(apiMethod2, SqlNameMappVO.class, inParam);

            model.addAttribute("summaryModel", sqlNameMappVO);

            inParam.clear();
            if (!isNullOrEmpty(dbId)) inParam.put("dbId", dbId.toUpperCase());
            inParam.put("sqlId", sqlNameMappVO.getRepSqlId());

            DaSqlFullTextVO daSqlFullTextVO = apiService.getApiModel(apiMethod3, DaSqlFullTextVO.class, inParam);
            String sqlFullText = (daSqlFullTextVO.getSqlFullText().isEmpty()) ? sqlNameMappVO.getSqlText() : daSqlFullTextVO.getSqlFullText();
            model.addAttribute("sqlFullText", sqlFullText);

            System.out.println("[sqlFullText]" + sqlFullText);
        } catch (NullPointerException e) {
            logger.info("[sqlNameMappVO] NullPointerException" + inParam.toString());
        }

        return "content/sqlNameDetail";
    }

    @RequestMapping(value = {"sqlNameList", "sqlNameListBySqlName", "sqlNameListByTableName", "sqlNameListByAreaCd"})
    public String sqlNameList(
            @RequestParam(value = "dbId", required = false) final String dbId,
            @RequestParam(value = "sqlName", required = false) final String sqlName,
            @RequestParam(value = "owner", required = false) final String owner,
            @RequestParam(value = "tableName", required = false) final String tableName,
            @RequestParam(value = "subjAreaCd", required = false) final String subjAreaCd,
            HttpServletRequest request, Model model) throws IOException, URISyntaxException {
        final String apiMethod = "sqlNameList";

        String returnPath = "content" + request.getServletPath();

        HashMap<String, String> inParam = new HashMap<>();

//        System.out.println("DBID: " + dbId);
        if (!isNullOrEmpty(sqlName)) inParam.put("sqlName", sqlName);
        if (!isNullOrEmpty(dbId) && !dbId.equals("0")) inParam.put("dbId", dbId);
        if (!isNullOrEmpty(owner)) inParam.put("owner", owner.toUpperCase());
        if (!isNullOrEmpty(tableName)) inParam.put("tableName", tableName.toUpperCase());
        if (!isNullOrEmpty(subjAreaCd)) inParam.put("subjAreaCd", subjAreaCd.toUpperCase());

        Collection<SqlNameVO> modelCollection = null;

        if (!inParam.isEmpty()) {
            modelCollection = apiService.getApiModels(apiMethod, new TypeReference<Collection<SqlNameVO>>() {
                    }
                    , inParam);
        }

        Collection<DaDbVO> daDbVOList = dbShowService.getDaDbList("Y");
        DaDbVO allDb = new DaDbVO();
        allDb.setDbId(0);
        allDb.setDbNm("ALL");
        daDbVOList.add(allDb);

        model.addAttribute("dbList", daDbVOList);
        model.addAttribute("defaultDate", LocalDateTime.now());
        model.addAttribute("sqlNameListVO", modelCollection);
        return returnPath;
    }

}
