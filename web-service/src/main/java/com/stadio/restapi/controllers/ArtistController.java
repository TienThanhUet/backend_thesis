package com.stadio.restapi.controllers;


import com.stadio.restapi.response.ResponseResult;
import com.stadio.restapi.service.IArtistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(value = "/artist")
public class ArtistController {

    @Autowired
    IArtistService artistService;

    public ResponseResult details(@RequestParam(value = "/nconst") String nconst){
        return artistService.ProcessGetDetails(nconst);
    }
}
