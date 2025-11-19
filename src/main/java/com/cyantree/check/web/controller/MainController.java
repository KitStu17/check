package com.cyantree.check.web.controller;

import java.util.ArrayList;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;

import com.cyantree.check.web.model.FileExtension.CustomFileVM;
import com.cyantree.check.web.model.FileExtension.FixFileVM;


@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/web")
public class MainController {

    @GetMapping("/main")
    public String mainPage(Model model) {
        List<FixFileVM> fixedList = new ArrayList<>();
        List<CustomFileVM> flexList = new ArrayList<>();
        // 테스트 값 삽입
        fixedList.add(new FixFileVM("bat", true));
        fixedList.add(new FixFileVM("cmd", true));
        fixedList.add(new FixFileVM("com", true));
        fixedList.add(new FixFileVM("cpl", true));

        flexList.add(new CustomFileVM("sh", true));
        flexList.add(new CustomFileVM("ju", true));
        flexList.add(new CustomFileVM("ch", true));
        flexList.add(new CustomFileVM("test", true));
        model.addAttribute("fixed", fixedList);
        model.addAttribute("custom", flexList);
        return "main";
    }
    
}
