package com.library.controller;

import com.library.model.dto.PublisherDto;
import com.library.service.PublisherService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/publisher")
public class PublisherController {

    private final PublisherService publisherService;

    @GetMapping(value = "/{id}")
    public PublisherDto getPublisher(@PathVariable(name = "id") Long publisherId){
        return publisherService.findById(publisherId);
    }

    @PostMapping
    public ResponseEntity<PublisherDto> savePublisher(@RequestBody PublisherDto publisherDto){
        return new ResponseEntity<>(publisherService.saveOrUpdate(publisherDto), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<PublisherDto> updatePublisher(@RequestBody PublisherDto publisherDto){
        return new ResponseEntity<>(publisherService.saveOrUpdate(publisherDto), HttpStatus.ACCEPTED);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> deletePublisher(@PathVariable("id") Long publisherId){
        publisherService.deleteById(publisherId);
        return new ResponseEntity<>("", HttpStatus.ACCEPTED);
    }

    @PutMapping(value = "/delete")
    public ResponseEntity<PublisherDto> softDeletePublisher(@RequestBody PublisherDto publisherDto){
        return new ResponseEntity<>(publisherService.saveOrUpdate(publisherDto), HttpStatus.ACCEPTED);
    }
}
