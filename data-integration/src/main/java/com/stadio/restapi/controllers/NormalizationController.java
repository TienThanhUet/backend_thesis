package com.stadio.restapi.controllers;


import com.stadio.restapi.service.IStopwordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/normalize")
public class NormalizationController {

    @Autowired
    IStopwordService stopwordService;

    @RequestMapping(value = "/stopword-remove")
    public String analyze(){
        stopwordService.removeStopWord();
        return "analyze done";
    }
}
