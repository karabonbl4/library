package com.library.controller;

import com.library.model.dto.PublisherDto;
import com.library.service.PublisherService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/publishers")
public class PublisherController {

    private final PublisherService publisherService;

    @GetMapping
    public List<PublisherDto> getAllWithPageable(@RequestParam Integer page, @RequestParam Integer sizeOnPage){
        return publisherService.findAllWithPageable(page, sizeOnPage);
    }

    @GetMapping(value = "/{id}")
    public PublisherDto getPublisher(@PathVariable(name = "id") Long publisherId){
        return publisherService.findById(publisherId);
    }

    @PostMapping
    public ResponseEntity<PublisherDto> savePublisher(@RequestBody PublisherDto publisherDto){
        return ResponseEntity.ok(publisherService.saveOrUpdate(publisherDto));
    }

    @PutMapping
    public ResponseEntity<PublisherDto> updatePublisher(@RequestBody PublisherDto publisherDto){
        return ResponseEntity.ok(publisherService.saveOrUpdate(publisherDto));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> deletePublisher(@PathVariable("id") Long publisherId){
        publisherService.deleteById(publisherId);
        return ResponseEntity.ok("Publisher deleted successfully!");
    }

    @PutMapping(value = "/delete")
    public ResponseEntity<PublisherDto> softDeletePublisher(@RequestBody PublisherDto publisherDto){
        return ResponseEntity.ok(publisherService.softDelete(publisherDto));
    }
}
