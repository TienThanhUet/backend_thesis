package com.satdio.dataIntegration.controllers;

import jvntextpro.JVnTextPro;
import jvntextpro.conversion.CompositeUnicode2Unicode;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.File;

@Controller
@RequestMapping(value = "/tokenizer")
public class tokenizerController {
    @RequestMapping(value = "/analyze",method = RequestMethod.GET)
    public String analyze(){
        Option option = new Option();
        JVnTextPro vnTextPro = new JVnTextPro();
        CompositeUnicode2Unicode conversion = new CompositeUnicode2Unicode();

        vnTextPro.initSenTokenization();
        vnTextPro.initSenSegmenter(option.modelDir.getPath() + File.separator + "jvnsensegmenter");
        vnTextPro.initSegmenter(option.modelDir.getPath() + File.separator + "jvnsegmenter");
        vnTextPro.initPosTagger(option.modelDir.getPath() + File.separator + "jvnpostag" + File.separator + "maxent");

        String output = vnTextPro.process(option.inFile);
        return output;
    }
}

class Option {
    File modelDir;
    boolean doSenSeg = true;
    boolean doWordSeg = true;
    boolean doSenToken = true;
    boolean doPosTagging = true;
    File inFile;
    String fileType=".txt";

    public Option(){
        modelDir = new File("/media/sm/DOWNLOAD/JVnTextPro-v.2.0/models");
        inFile = new File("/home/sm/Public/thesis/article.txt");
    }
}
