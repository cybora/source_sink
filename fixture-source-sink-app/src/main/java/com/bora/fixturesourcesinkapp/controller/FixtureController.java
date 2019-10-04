package com.bora.fixturesourcesinkapp.controller;

import com.bora.fixturesourcesinkapp.service.FixtureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FixtureController {

    @Autowired
    private FixtureService fixtureService;


    @GetMapping("/start")
    public void start() {
        fixtureService.start();
    }
}
