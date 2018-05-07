package com.stadio.restapi.controllers;

import com.stadio.restapi.service.ITfIdfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/tf-idf")
public class TfIdfController {
    @Autowired
    ITfIdfService tfIdfService;

    @RequestMapping(value = "/analyze")
    public String analyze(){
        tfIdfService.analyze();
        return "analyze done";
    }

    @RequestMapping(value = "/word-library")
    public String createWordLibrary(){
        tfIdfService.createWordLibrary();
        return "analyze done";
    }

    @RequestMapping(value = "/calculate")
    public String calculateTfIdf(){
        tfIdfService.calculateTfIdf();
        return "analyze done";
    }
}
