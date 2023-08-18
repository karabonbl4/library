package com.library.controller;

import com.library.model.dto.PublisherDto;
import com.library.model.dto.PublisherTitleDto;
import com.library.model.dto.ResponseMessage;
import com.library.service.PublisherService;
import jakarta.validation.Valid;
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

import static com.library.constant.ApplicationConstant.PUBLISHER_IS_DELETED;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/publishers")
public class PublisherController {

    private final PublisherService publisherService;

    @GetMapping
    public List<PublisherTitleDto> getAllWithPageable(@Valid @RequestParam Integer page, @Valid @RequestParam Integer sizeOnPage) {
        return publisherService.findAllWithPageable(page, sizeOnPage);
    }

    @GetMapping(value = "/{id}")
    public PublisherDto getPublisher(@Valid @PathVariable(name = "id") Long publisherId) {
        return publisherService.findById(publisherId);
    }

    @PostMapping
    public ResponseEntity<PublisherDto> savePublisher(@Valid @RequestBody PublisherDto publisherDto) {
        return ResponseEntity.ok(publisherService.saveOrUpdate(publisherDto));
    }

    @PutMapping
    public ResponseEntity<PublisherDto> updatePublisher(@Valid @RequestBody PublisherDto publisherDto) {
        return ResponseEntity.ok(publisherService.saveOrUpdate(publisherDto));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<ResponseMessage> softDeletePublisher(@Valid @PathVariable(name = "id") Long publisherId) {
        publisherService.softDelete(publisherId);
        return ResponseEntity.ok(new ResponseMessage(PUBLISHER_IS_DELETED));
    }
}
