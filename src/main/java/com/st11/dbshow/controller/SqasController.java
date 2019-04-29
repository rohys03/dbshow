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

    @RequestMapping(value = {"sqlNameDetail"})
    public String sqlNameDetail(
            @RequestParam(value = "clctDy", required = false) final String clctDy,
            @RequestParam(value = "dbId", required = false) final String dbId,
            @RequestParam(value = "sqlName", required = false) final String sqlName,
            @RequestParam(value = "sqlNameNo", required = false) final String sqlNameNo,
            Model model) throws IOException, URISyntaxException {
        final String apiMethod = "sqlNameStatsHist";
        final String apiMethod2 = "sqlNameMappSummary";

        HashMap<String, String> inParam = new HashMap<>();

        if (!isNullOrEmpty(clctDy)) {
            inParam.put("clctDy", clctDy.toUpperCase());
        } else return "content/sqlNameDetail";
        if (!isNullOrEmpty(dbId)) inParam.put("dbId", dbId.toUpperCase());
        if (!isNullOrEmpty(sqlName)) inParam.put("sqlName", sqlName.toUpperCase());
        if (!isNullOrEmpty(sqlNameNo)) inParam.put("sqlNameNo", sqlNameNo.toUpperCase());

        Collection<SqlNameVO> modelCollection = null;
        Collection<SqlNameMappVO> modelCollection2 = null;

        if (inParam.containsKey("sqlName") || inParam.containsKey("sqlNameNo")) {
            modelCollection = apiService.getApiModels(apiMethod, new TypeReference<Collection<SqlNameVO>>() {
                    }
                    , inParam);

            inParam.remove("clctDy");
            System.out.println("[sqlNameMappSummary]" + inParam.toString());
            modelCollection2 = apiService.getApiModels(apiMethod2, new TypeReference<Collection<SqlNameMappVO>>() {
                    }
                    , inParam);
        }

        model.addAttribute("histModel", modelCollection);
        model.addAttribute("summaryModel", modelCollection2);

        System.out.println("[summaryModel]" + modelCollection2.toString());


        return "content/sqlNameDetail";
    }

    @RequestMapping(value = {"sqlNameList"})
    public String sqlName(
            @RequestParam(value = "sqlName", required = false) final String sqlName,
            @RequestParam(value = "logicalAreaCd1", required = false) final String logicalAreaCd1,
            @RequestParam(value = "logicalAreaCd2", required = false) final String logicalAreaCd2,
            Model model) throws IOException, URISyntaxException {
        final String apiMethod = "sqlNameList";

        HashMap<String, String> inParam = new HashMap<>();

        if (!isNullOrEmpty(sqlName)) inParam.put("sqlName", sqlName.toUpperCase());
        if (!isNullOrEmpty(logicalAreaCd1)) inParam.put("logicalAreaCd1", logicalAreaCd1.toUpperCase());
        if (!isNullOrEmpty(logicalAreaCd2)) inParam.put("logicalAreaCd2", logicalAreaCd2.toUpperCase());

        Collection<SqlNameVO> modelCollection = null;

        if (!inParam.isEmpty()) {
            modelCollection = apiService.getApiModels(apiMethod, new TypeReference<Collection<SqlNameVO>>() {
                    }
                    , inParam);
        }


        model.addAttribute("defaultDate", "20190315");
        model.addAttribute("model", modelCollection);
        return "content/sqlNameList";
    }

    @RequestMapping(value = {"daTopSql"})
    public String daTopSql(
            @RequestParam(value = "clctDy", required = false) final String clctDy,
            @RequestParam(value = "logicalAreaCd1", required = false) final String logicalAreaCd1,
            @RequestParam(value = "logicalAreaCd2", required = false) final String logicalAreaCd2,
            Model model) throws IOException, URISyntaxException {
        final String apiMethod = "dbshow/getLastDaStatMng2";

        HashMap<String, String> inParam = new HashMap<>();
        ObjectMapper objectMapper = new ObjectMapper();
        DaStatMngVO daStatMngVO = new DaStatMngVO();

        inParam.put("dbName", "PMETA");
        inParam.put("statName", "DA_SQLNAME_MAPP");
//        if (!isNullOrEmpty(clctDy)) inParam.put("clctDy", clctDy.toUpperCase());
//        if (!isNullOrEmpty(logicalAreaCd1)) inParam.put("logicalAreaCd1", logicalAreaCd1.toUpperCase());
//        if (!isNullOrEmpty(logicalAreaCd2)) inParam.put("logicalAreaCd2", logicalAreaCd2.toUpperCase());

        String modelCollection = null;

        if (!inParam.isEmpty()) {
            modelCollection = apiService.getApiString(apiMethod, inParam);

            daStatMngVO = objectMapper.readValue(modelCollection, DaStatMngVO.class);
        }

        System.out.println("[daTopSql] result: " + daStatMngVO.toString());

        model.addAttribute("model", daStatMngVO);
        return "content/daTopSql";
    }

}
