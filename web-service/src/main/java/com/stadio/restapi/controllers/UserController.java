package com.stadio.restapi.controllers;

import com.stadio.model.documents.User;
import com.stadio.model.dtos.UserDTO;
import com.stadio.restapi.response.ResponseResult;
import com.stadio.restapi.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    IUserService userService;



    @PostMapping("/register")
    public ResponseResult register(
            @RequestBody UserDTO userDTO)
    {
        return userService.processCreateNewUser(userDTO);
    }

    @PostMapping("/update")
    public ResponseResult update(@RequestBody UserDTO userDTO)
    {
        return userService.processUpdateUser(userDTO);
    }

    @PostMapping("/changePassword")
    public ResponseResult changePassword(
            @RequestHeader(value = "Authorization") String token,
            @RequestParam(value = "oldPass") String oldPass,
            @RequestParam(value = "newPass") String newPass)
    {
        return userService.processChangePassword(token, oldPass, newPass);
    }

    @GetMapping("/details")
    public ResponseResult details(@RequestParam(value = "id") String id)
    {
        return userService.processGetProfileUser(id);
    }

    @GetMapping("/profile")
    public ResponseResult profile(
            @RequestHeader(value = "Authorization") String token
    )
    {
        User user = userService.getCurrentUser(token);
        return ResponseResult.newInstance("00", "", user);
    }
}
