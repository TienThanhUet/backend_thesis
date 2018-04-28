package com.stadio.restapi.controllers;


import com.stadio.restapi.response.ResponseResult;
import com.stadio.restapi.service.IArtistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/artist")
public class ArtistController {

    @Autowired
    IArtistService artistService;

    @RequestMapping(value = "/details", method = RequestMethod.GET)
    public ResponseResult details(@RequestParam(value = "nconst") String nconst){
        return artistService.ProcessGetDetails(nconst);
    }

    @RequestMapping(value = "/knownForTitles", method = RequestMethod.GET)
    public ResponseResult knownForTitles(@RequestParam(value = "knownForTitles") String knownForTitles){
        return artistService.knownForTitles(knownForTitles);
    }
}
