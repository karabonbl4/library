package com.library.controller;

import com.library.dto.LogDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(value = "logFeignClient", url = "http://localhost:8081", path = "/logs")
public interface LogFeignClient {

    @GetMapping
    List<LogDto> getAllLogs(@RequestParam(name = "page") Integer page, @RequestParam(name = "sizeOnPage") Integer sizeOnPage);

    @GetMapping(value = "/type")
    List<LogDto> getLogsByType(@RequestParam(name = "type") String type, @RequestParam(name = "page") Integer page, @RequestParam(name = "sizeOnPage") Integer sizeOnPage);

    @GetMapping(value = "/date")
    List<LogDto> getLogsByDate(@RequestParam(name = "date") String date, @RequestParam(name = "page") Integer page, @RequestParam(name = "sizeOnPage") Integer sizeOnPage);
}
