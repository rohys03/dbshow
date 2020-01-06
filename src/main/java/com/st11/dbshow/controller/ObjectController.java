package com.st11.dbshow.controller;


import com.fasterxml.jackson.core.type.TypeReference;
import com.st11.dbshow.repository.*;
import com.st11.dbshow.service.ApiService;
import com.st11.dbshow.service.DbShowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.*;

import static com.st11.dbshow.common.DbShow.*;


@Controller
public class ObjectController {

    @Autowired
    private ApiService apiService;

    @Autowired
    private DbShowService dbShowService;

    @RequestMapping(value = "daTables", method = RequestMethod.GET)
    public String daTables (
            @RequestParam(value = "dbName", required = false) String dbName,
            @RequestParam(value = "tableName", required = false) String tableName,
            @RequestParam(value = "logicalAreaCd1", required = false) final String logicalAreaCd1,
            @RequestParam(value = "logicalAreaCd2", required = false) final String logicalAreaCd2,
            Model model) throws URISyntaxException, IOException {
//        System.out.println("[Request ApiService Param] : " + request.getRequestURL().toString() + "/" + getParameterMap(request).toString());
        model.addAttribute("serverTime", getCurrentTime());


        final String apiMethod = "daTables";

        HashMap<String, String> inParam = new HashMap<>();

        if (!isNullOrEmpty(dbName)) inParam.put("dbName", dbName.toUpperCase());
        if (!isNullOrEmpty(tableName)) inParam.put("tableName", tableName.toUpperCase());
        if (!isNullOrEmpty(logicalAreaCd1)) inParam.put("logicalAreaCd1", logicalAreaCd1.toUpperCase());
        if (!isNullOrEmpty(logicalAreaCd2)) inParam.put("logicalAreaCd2", logicalAreaCd2.toUpperCase());

        Collection<DaTableVO> modelCollection = null;
        modelCollection = apiService.getApiModels(apiMethod, new TypeReference<Collection<DaTableVO>>() {
                }
                , inParam);
        model.addAttribute("model", modelCollection);
        return "content/daTables";
    }

    @RequestMapping(value = "referencedObject")
    public String referencedObject(
            @RequestParam(value = "dbName", required = false) String dbName,
            @RequestParam(value = "objectType", required = false) String objectType,
            @RequestParam(value = "owner", required = false) String owner,
            @RequestParam(value = "objectName", required = false) String objectName,
            HttpServletRequest request, Model model) throws IOException{
        final String apiMethod = "referencedObject";

        model.addAttribute("serverTime", getCurrentTime());

        if (!isNullOrEmpty(dbName) && !isNullOrEmpty(objectType) && !isNullOrEmpty(owner) && !isNullOrEmpty(objectName)) {
            Collection<RefObjectVO> modelCollection = null;
            modelCollection = apiService.getApiModels(apiMethod, new TypeReference<Collection<RefObjectVO>>() {
                    }
                    , dbName.toUpperCase(), objectType.toUpperCase(), owner.toUpperCase(), objectName.toUpperCase());
            model.addAttribute("model", modelCollection);
        }

        return "content/referencedObject";
    }

    @RequestMapping(value = "daSyncData", method = RequestMethod.GET)
    public String daSyncData(
            @RequestParam(value = "tableName", required = false) String tableName,
            @RequestParam(value = "hostName", required = false) String hostName,
            Model model) throws URISyntaxException, IOException {

        Collection<DaStatMngVO> daStatMngModels = apiService.getLastDaStatMng("TMALL", "DA_SYNC_TABLES");
        model.addAttribute("daStatMngModel", daStatMngModels.toArray()[0]);
//        System.out.println("[daStatMngModel]" + model.toString());

        final String apiMethod = "daSyncData";

        HashMap<String, String> inParam = new HashMap<>();

        if (!isNullOrEmpty(tableName)) inParam.put("tableName", tableName.toUpperCase());
        if (!isNullOrEmpty(hostName)) inParam.put("hostName", hostName.toUpperCase());

        Collection<DaSyncTableVO> modelCollection = null;

        if (!inParam.isEmpty()) {
            modelCollection = apiService.getApiModels(apiMethod, new TypeReference<Collection<DaSyncTableVO>>() {
                    }
                    , inParam);

            System.out.println(modelCollection.toString());

        }
        model.addAttribute("model", modelCollection);

//        if (!isNullOrEmpty(tableName)) {
//        }

        return "content/daSyncData";
    }


    @RequestMapping(value = {"tableDetail"})
    public String sqlNameDetail(
//            @RequestParam(value = "clctDy", required = false) final String clctDy,
            @RequestParam(value = "dbId", required = false) final String dbId,
            @RequestParam(value = "owner", required = false) final String owner,
            @RequestParam(value = "tableName", required = false) final String tableName,
            Model model) throws IOException, URISyntaxException {
        final String apiMethod = "jpa/daObject";
        final String apiMethod2 = "daTables";
        final String apiMethod3 = "jpa/daSqlFullText";


        HashMap<String, String> inParam = new HashMap<>();

        Collection<DaDbVO> daDbVOList = dbShowService.getDaDbList("Y");
        model.addAttribute("dbList", daDbVOList);

        if (!isNullOrEmpty(owner)) return "content/tableDetail";
        if (!isNullOrEmpty(tableName)) return "content/tableDetail";

        if (!isNullOrEmpty(tableName)) inParam.put("objectName", tableName.toUpperCase());
        if (!isNullOrEmpty(tableName)) inParam.put("tableName", tableName.toUpperCase());
        if (!isNullOrEmpty(dbId)) inParam.put("dbId", dbId.toUpperCase());
        if (!isNullOrEmpty(owner)) inParam.put("owner", owner.toUpperCase());
        inParam.put("objectType", "TABLE");

        DaObjectVO daObjectVO = apiService.getApiModel(apiMethod, DaObjectVO.class, inParam);

        Collection<DaTableVO> daTableCollection = null;
        daTableCollection = apiService.getApiModels(apiMethod2, new TypeReference<Collection<DaTableVO>>() {}, inParam);

        model.addAttribute("daObjectVO", daObjectVO);
        model.addAttribute("daTableVO", daTableCollection.toArray()[0]);

        System.out.println("[MODEL] : " + model.toString());

        /*
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
*/
        return "content/tableDetail";
    }



}
