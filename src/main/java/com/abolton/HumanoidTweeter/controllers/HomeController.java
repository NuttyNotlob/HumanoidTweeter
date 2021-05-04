package com.abolton.HumanoidTweeter.controllers;

import com.abolton.HumanoidTweeter.services.SteamSpyDataGrabberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @Autowired
    SteamSpyDataGrabberService steamSpyDataGrabberService;

    @GetMapping("/")
    public String homeMethod(Model model) {
        // Add the data taken from the SteamSpy API
        model.addAttribute("steamSpyGames", steamSpyDataGrabberService.getSteamSpyGames());
        return "home";
    }
}
