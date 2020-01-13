package com.st11.dbshow.common;

import com.st11.dbshow.config.SideBarConfig;
import com.st11.dbshow.config.SideBarVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

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


    @Autowired
    SideBarConfig sideBarConfig;

    @ModelAttribute
    public void injectMenu(Model model) {
        String map = "AAAA";

        model.addAttribute("menu_ZZ", map);

        List<Map> sideBar = sideBarConfig.getMenuA_A();
        model.addAttribute("sideBar", sideBar);
    }

}