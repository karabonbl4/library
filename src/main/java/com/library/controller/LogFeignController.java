package com.library.controller;

import com.library.dto.LogDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/logs")
@RequiredArgsConstructor
public class LogFeignController {

    private final LogFeignClient logFeignClient;

    @GetMapping
    public List<LogDto> getLogs(@RequestParam(name = "page") Integer page, @RequestParam(name = "sizeOnPage") Integer sizeOnPage) {
        return logFeignClient.getAllLogs(page, sizeOnPage);
    }

    @GetMapping(value = "/type")
    public List<LogDto> getLogsByType(@RequestParam String type, @RequestParam Integer page, @RequestParam Integer sizeOnPage) {
        return logFeignClient.getLogsByType(type, page, sizeOnPage);
    }

    @GetMapping(value = "/date")
    public List<LogDto> getLogsByDate(@RequestParam String date, @RequestParam Integer page, @RequestParam Integer sizeOnPage) {
        return logFeignClient.getLogsByDate(date, page, sizeOnPage);
    }
}