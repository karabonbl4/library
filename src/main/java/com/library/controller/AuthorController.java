package com.library.controller;

import com.library.dto.AuthorDto;
import com.library.dto.AuthorNameDto;
import com.library.dto.ResponseMessage;
import com.library.service.AuthorService;
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

import static com.library.constant.ApplicationConstant.AUTHOR_IS_DELETED;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/authors")
@Tag(name = "Authors", description = "Author management APIs")
public class AuthorController {

    private final AuthorService authorService;

    @GetMapping
    @Operation(summary = "Get all authors with pagination")
    @ApiResponse(responseCode = "200",
            description = "Found the list of authors",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = AuthorNameDto.class)))
    public List<AuthorNameDto> getAllAuthors(@Valid @RequestParam Integer page, @Valid @RequestParam Integer sizeOnPage) {
        return authorService.findAllWithPageable(page, sizeOnPage);
    }

    @GetMapping(value = "/{id}")
    @Operation(summary = "Get a book by its id")
    @ApiResponse(responseCode = "200",
            description = "Found the author",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = AuthorDto.class)))
    public AuthorDto getAuthorById(@Valid @PathVariable("id") Long authorId) {
        return authorService.findById(authorId);
    }

    @PostMapping
    @Operation(summary = "Save getting author")
    @ApiResponse(responseCode = "200",
            description = "Save the author",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = AuthorDto.class)))
    public AuthorDto saveAuthor(@Valid @RequestBody AuthorDto authorDto) {
        return authorService.saveOrUpdate(authorDto);
    }

    @PutMapping
    @Operation(summary = "Update getting author")
    @ApiResponse(responseCode = "200",
            description = "Update the author",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = AuthorDto.class)))
    public AuthorDto updateAuthor(@Valid @RequestBody AuthorDto authorDto) {
        return authorService.saveOrUpdate(authorDto);
    }

    @DeleteMapping(value = "/{id}")
    @Operation(summary = "Delete a author by its id")
    @ApiResponse(responseCode = "200",
            description = "Delete the author by id",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseMessage.class)))
    public ResponseMessage softDeleteAuthor(@Valid @PathVariable(name = "id") Long authorId) {
        authorService.softDelete(authorId);
        return new ResponseMessage(AUTHOR_IS_DELETED);
    }
}