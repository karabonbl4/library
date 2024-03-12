package com.library.controller;

import com.library.dto.PublisherDto;
import com.library.dto.PublisherTitleDto;
import com.library.dto.ResponseMessage;
import com.library.service.PublisherService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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
@Tag(name = "Publishers", description = "Publisher management APIs")
public class PublisherController {

    private final PublisherService publisherService;

    @GetMapping
    @Operation(summary = "Get all publishers with pagination")
    @ApiResponse(responseCode = "200",
            description = "Found the list of publishers",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = PublisherTitleDto.class)))
    public List<PublisherTitleDto> getAllWithPageable(@Valid @RequestParam Integer page, @Valid @RequestParam Integer sizeOnPage) {
        return publisherService.findAllWithPageable(page, sizeOnPage);
    }

    @GetMapping(value = "/{id}")
    @Operation(summary = "Get a publisher by its id")
    @ApiResponse(responseCode = "200",
            description = "Found the publisher",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = PublisherDto.class)))
    public PublisherDto getPublisher(@Valid @PathVariable(name = "id") Long publisherId) {
        return publisherService.findById(publisherId);
    }

    @PostMapping
    @Operation(summary = "Save getting publisher")
    @ApiResponse(responseCode = "200",
            description = "Save the publisher",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = PublisherDto.class)))
    public PublisherDto savePublisher(@Valid @RequestBody PublisherDto publisherDto) {
        return publisherService.saveOrUpdate(publisherDto);
    }

    @PutMapping
    @Operation(summary = "Update getting publisher")
    @ApiResponse(responseCode = "200",
            description = "Update the publisher",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = PublisherDto.class)))
    public PublisherDto updatePublisher(@Valid @RequestBody PublisherDto publisherDto) {
        return publisherService.saveOrUpdate(publisherDto);
    }

    @DeleteMapping(value = "/{id}")
    @Operation(summary = "Delete a publisher by its id")
    @ApiResponse(responseCode = "200",
            description = "Delete the publisher by id",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseMessage.class)))
    public ResponseMessage softDeletePublisher(@Valid @PathVariable(name = "id") Long publisherId) {
        publisherService.softDelete(publisherId);
        return new ResponseMessage(PUBLISHER_IS_DELETED);
    }
}