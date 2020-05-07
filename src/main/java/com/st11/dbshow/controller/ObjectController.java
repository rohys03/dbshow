package com.st11.dbshow.controller;


import com.fasterxml.jackson.core.type.TypeReference;
import com.st11.dbshow.common.DbShow;
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
import java.time.LocalDateTime;
import java.util.*;

import static com.st11.dbshow.common.DbShow.*;


@Controller
public class ObjectController {

    @Autowired
    private ApiService apiService;

    @Autowired
    private DbShowService dbShowService;

    @RequestMapping(value = "daTableList", method = RequestMethod.GET)
    public String daTableList (
            @RequestParam(value = "dbId", required = false) final String dbId,
            @RequestParam(value = "dbName", required = false) String dbName,
            @RequestParam(value = "tableName", required = false) String tableName,
            @RequestParam(value = "subjAreaCd", required = false) final String subjAreaCd,
            Model model) throws URISyntaxException, IOException {
//        System.out.println("[Request ApiService Param] : " + request.getRequestURL().toString() + "/" + getParameterMap(request).toString());
        model.addAttribute("serverTime", getCurrentTime());

        final String apiMethod = "daTableList";

        HashMap<String, String> inParam = new HashMap<>();

        if (!isNullOrEmpty(dbName)) inParam.put("dbName", dbName.toUpperCase());
        if (!isNullOrEmpty(dbId) && !dbId.equals("0")) inParam.put("dbId", dbId);
        if (!isNullOrEmpty(tableName)) inParam.put("tableName", tableName.toUpperCase());
        if (!isNullOrEmpty(subjAreaCd)) inParam.put("subjAreaCd", subjAreaCd.toUpperCase());

        Collection<DaTableVO> modelCollection = null;
        if (!inParam.isEmpty()) {
            modelCollection = apiService.getApiModels(apiMethod, new TypeReference<Collection<DaTableVO>>() {
                    }
                    , inParam);
        }

        Collection<DaDbVO> daDbVOList = dbShowService.getDaDbList("Y");
        DaDbVO allDb = new DaDbVO();
        allDb.setDbId(0);
        allDb.setDbNm("ALL");
        daDbVOList.add(allDb);

        model.addAttribute("dbList", daDbVOList);
        model.addAttribute("model", modelCollection);

//        System.out.println("MODEL]: " + model.toString());
        return "content/daTableList";
    }

    @RequestMapping(value = "daSyncData", method = RequestMethod.GET)
    public String daSyncData(
            @RequestParam(value = "tableName", required = false) String tableName,
            @RequestParam(value = "hostName", required = false) String hostName,
            HttpServletRequest request, Model model) throws URISyntaxException, IOException {

        Collection<DaStatMngVO> daStatMngModels = apiService.getLastDaStatMng("TMALL", "DA_SYNC_TABLES");
        model.addAttribute("daStatMngModel", daStatMngModels.toArray()[0]);
//        System.out.println("[daStatMngModel]" + model.toString());

        final String apiMethod = "daSyncData";
        String returnPath = "content" + request.getServletPath();

        HashMap<String, String> inParam = new HashMap<>();

        if (!isNullOrEmpty(tableName)) {
            inParam.put("tableName", tableName.toUpperCase());
        } else {
            return returnPath;
        }
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

        return returnPath;
    }


    @RequestMapping(value = {"daTableDetail"})
    public String daTableDetail(
//            @RequestParam(value = "clctDy", required = false) final String clctDy,
            @RequestParam(value = "dbId", required = false) final String dbId,
            @RequestParam(value = "owner", required = false) final String owner,
            @RequestParam(value = "tableName", required = false) final String tableName,
            HttpServletRequest request, Model model) throws IOException, URISyntaxException {
        final String apiMethod = "jpa/daObject";
        final String apiMethod2 = "jpa/daTable";
        final String apiMethod3 = "jpa/daObjectList";

        String returnPath = "content" + request.getServletPath();

        HashMap<String, String> inParam = new HashMap<>();

        Collection<DaDbVO> daDbVOList = dbShowService.getDaDbList("Y");
        model.addAttribute("dbList", daDbVOList);

        if (isNullOrEmpty(owner)) return returnPath;
        if (isNullOrEmpty(tableName)) return returnPath;

        if (!isNullOrEmpty(tableName)) inParam.put("objectName", tableName.toUpperCase());
        if (!isNullOrEmpty(tableName)) inParam.put("tableName", tableName.toUpperCase());
        if (!isNullOrEmpty(dbId)) inParam.put("dbId", dbId.toUpperCase());
        if (!isNullOrEmpty(owner)) inParam.put("owner", owner.toUpperCase());

        inParam.put("objectType", "TABLE");

//        System.out.println("[inParam] : " + inParam.toString());

        DaObjectVO daObjectVO = apiService.getApiModel(apiMethod, DaObjectVO.class, inParam);
        DaTableVO daTableVO = apiService.getApiModel(apiMethod2, DaTableVO.class, inParam);
        DaTableVO daTabModificationVO = apiService.getApiModel("daTabModification", DaTableVO.class, inParam);

        model.addAttribute("daObjectVO", daObjectVO);
        model.addAttribute("daTableVO", daTableVO);
        model.addAttribute("tabModificationVO", daTabModificationVO);

        DbShow dbShow = new DbShow(daDbVOList);
        Map<Integer, String> dbMap = dbShow.getDbList();

        Collection<DaObjectVO> daObjectVOList = null;
        if (!inParam.isEmpty()) {
            daObjectVOList = apiService.getApiModels(apiMethod3, new TypeReference<Collection<DaObjectVO>>() {}, inParam);
        }

        ArrayList<String> daObjectList = new ArrayList<>();

        for (DaObjectVO vo : daObjectVOList) {
            String text = vo.getOwner() + "." + vo.getObjectName() + "@" + dbMap.get(Integer.parseInt(vo.getDbId())) + " (" + vo.getObjectType() + ")" ;
            daObjectList.add(text);
        }


        Collection<DamEntityVO> damEntityVOList = null;
        Collection<SqlNameVO> sqlNameVOList = null;
        Collection<SqlIdVO> sqlIdVOList = null;

        if (!inParam.isEmpty()) {
            damEntityVOList = apiService.getApiModels("dam/entityList", new TypeReference<Collection<DamEntityVO>>() {}, inParam);
            sqlNameVOList = apiService.getApiModels("sqlNameList", new TypeReference<Collection<SqlNameVO>>() {}, inParam);
            sqlIdVOList = apiService.getApiModels("sqlIdList", new TypeReference<Collection<SqlIdVO>>() {}, inParam);
        }

        model.addAttribute("daObjectList", daObjectList);
        model.addAttribute("damEntityVO", damEntityVOList);
        model.addAttribute("sqlNameVOList", sqlNameVOList);
        model.addAttribute("sqlIdVOList", sqlIdVOList);

        model.addAttribute("defaultDate", LocalDateTime.now());

        return returnPath;
    }


    @RequestMapping(value = "daTabSubjAreaList")
    public String daTabSubjAreaList (
            @RequestParam(value = "subjAreaCd", required = false) final String subjAreaCd,
            HttpServletRequest request, Model model) throws URISyntaxException, IOException {
//        System.out.println("[Request ApiService Param] : " + request.getRequestURL().toString() + "/" + getParameterMap(request).toString());
        model.addAttribute("serverTime", getCurrentTime());

        final String apiMethod = "daTabSubjAreaList";
        String returnPath = "content" + request.getServletPath();

        HashMap<String, String> inParam = new HashMap<>();

        if (!isNullOrEmpty(subjAreaCd)) inParam.put("subjAreaCd", subjAreaCd.toUpperCase());

        Collection<DaTabSubjAreaVO> daTabSubjAreaList = null;
        daTabSubjAreaList = apiService.getApiModels(apiMethod, new TypeReference<Collection<DaTabSubjAreaVO>>() {}
                , inParam);
        model.addAttribute("daTabSubjAreaList", daTabSubjAreaList);

        return returnPath;
    }

}
