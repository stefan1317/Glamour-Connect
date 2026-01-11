package com.example.glamour_connect.controller;

import com.example.glamour_connect.service.AppUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("user")
public class AppUserController {

    private final AppUserService appUserService;

    //TODO: Implement a more complex authentification functionality based on JWT.
}
