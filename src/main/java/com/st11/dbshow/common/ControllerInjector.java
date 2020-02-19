package com.st11.dbshow.common;

import com.st11.dbshow.config.SideBarConfig;
import com.st11.dbshow.repository.DaDbVO;
import com.st11.dbshow.service.DbShowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ControllerAdvice
public class ControllerInjector {
/*
    공통 변수 선언 하는 클래스
    - DB List
    - SideBar 등 config 정보
    */

    private HashMap<Integer, String> daDbList = new HashMap<>();

//    @Autowired
//    SideBarConfig sideBarConfig;

    @Autowired
    DbShowService dbShowService;

/*
    @ModelAttribute
    public void injectMenu(Model model) {
        String map = "AAAA";

        model.addAttribute("menu_ZZ", map);

        List<Map> sideBar = sideBarConfig.getMenuA_A();
        model.addAttribute("g-sideBar", sideBar);
    }
*/

    @ModelAttribute
    public void setDaDbList(Model model) throws IOException, URISyntaxException {

        Collection<DaDbVO> daDbVOList = dbShowService.getDaDbList("Y");

        for (DaDbVO dbVO: daDbVOList) {
            this.daDbList.put(dbVO.getDbId(), dbVO.getDbNm());
        }
        model.addAttribute("GdaDbList", daDbList);
        model.addAttribute("daDbList", daDbVOList);
//        System.out.println("[dadbList]" + model.toString());
    }

}