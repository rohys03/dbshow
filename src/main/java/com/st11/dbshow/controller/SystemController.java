package com.st11.dbshow.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.st11.dbshow.repository.DaStdWordDicVO;
import com.st11.dbshow.service.ApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import static com.st11.dbshow.common.DbShow.getCurrentTime;
import static com.st11.dbshow.common.DbShow.getParameterMap;
import static com.st11.dbshow.common.DbShow.isNullOrEmpty;

@Controller
public class SystemController {
    private final Logger logger = Logger.getLogger(this.getClass().getName());

    @Autowired
    private ApiService apiService;

    @RequestMapping(value = "daStdWordDic", method = RequestMethod.GET)
    public String daStdWordDic (
            @RequestParam(value = "word", required = false) String word,
            Model model) throws URISyntaxException, IOException {
//        System.out.println("[Request ApiService Param] : " + request.getRequestURL().toString() + "/" + getParameterMap(request).toString());
        model.addAttribute("serverTime", getCurrentTime());

        final String apiMethod = "daStdWordDicList";

        HashMap<String, String> inParam = new HashMap<>();

        if (!isNullOrEmpty(word)) inParam.put("word", word);

        Collection<DaStdWordDicVO> modelCollection = null;
        modelCollection = apiService.getApiModels(apiMethod, new TypeReference<Collection<DaStdWordDicVO>>() {
                }
                , inParam);
        model.addAttribute("model", modelCollection);
        return "content/daStdWordDic";
    }

    @RequestMapping(value = "error", method = RequestMethod.GET)
    public Map<String, Object> errorPage (HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler, Model model) throws Exception {

        Map<String, Object> map = new HashMap<String, Object>();
        logger.info("[error] : " + request.getRequestURL().toString() + "/" + getParameterMap(request).toString());

        map.put("status", request.getAttribute("javax.servlet.error.status_code"));
        map.put("reason", request.getAttribute("javax.servlet.error.message"));

        model.addAttribute("serverTime", getCurrentTime());

        return map;
    }


}
