package com.satdio.dataIntegration.controllers;

import com.satdio.dataIntegration.service.ITfIdfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/tf-idf")
public class TfIdfController {
    @Autowired
    ITfIdfService tfIdfService;

    @RequestMapping(value = "analyze")
    public void analyze(){
        tfIdfService.analyze();
    }
}
