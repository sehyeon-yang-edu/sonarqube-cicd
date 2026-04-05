package com.example.demo.controller;

import com.example.demo.service.NewsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@Controller
public class WelcomeController {

    private final NewsService newsService;

    public WelcomeController(NewsService newsService) {
        this.newsService = newsService;
    }

    @GetMapping("/")
    public String welcome(Model model) {
        // Current Time in Seoul
        ZonedDateTime nowInSeoul = ZonedDateTime.now(ZoneId.of("Asia/Seoul"));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss z");
        String formattedTime = nowInSeoul.format(formatter);

        model.addAttribute("currentTime", formattedTime);
        model.addAttribute("newsItems", newsService.getLatestNews());

        return "welcome";
    }
}
