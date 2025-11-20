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
import com.cyantree.check.web.repository.FileWebRepository;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/web")
public class MainController {

    private final FileWebRepository fileWebRepository;

    @GetMapping("/main")
    public String mainPage(Model model) {
        List<FixFileVM> fixedList = fileWebRepository.selectFixedExtensionList();
        List<CustomFileVM> flexList = fileWebRepository.selectCustomExtensionList();
        model.addAttribute("fixed", fixedList);
        model.addAttribute("custom", flexList);
        return "main";
    }

}
