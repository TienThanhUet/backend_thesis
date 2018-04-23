package com.satdio.dataIntegration.controllers;

import com.satdio.dataIntegration.service.IStopwordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/normalize")
public class NormalizationController {

    @Autowired
    IStopwordService stopwordService;

    @RequestMapping(value = "/stopword-remove")
    public void analyze(){
        stopwordService.removeStopWord();
    }
}
