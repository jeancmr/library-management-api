package com.jeancmr.library_management.controller;

import com.jeancmr.library_management.dto.Publisher.PublisherDto;
import com.jeancmr.library_management.service.interfaces.IPublisherService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/publishers")
@RequiredArgsConstructor
@Tag(name = "Publishers", description = "Operations related to publishers")
public class PublisherController {
    private final IPublisherService publisherService;

    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_LIBRARIAN', 'ROLE_MEMBER')")
    @Operation(summary = "Getting all publishers")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Publishers found"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized")
            }
    )
    public ResponseEntity<List<PublisherDto>> findAllPublishers(){
        List<PublisherDto> publisherList = publisherService.findAll();
        return ResponseEntity.ok(publisherList);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_LIBRARIAN')")
    @Operation(summary = "Getting publisher by id")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Publisher found"),
                    @ApiResponse(responseCode = "404", description = "Publisher not found"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized")
            }
    )
    public ResponseEntity<PublisherDto> findPublisherById(@PathVariable Long id){
        PublisherDto publisherDtoFound = publisherService.findById(id);
        return ResponseEntity.ok(publisherDtoFound);
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_LIBRARIAN')")
    @Operation(summary = "Save publisher")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "201", description = "Publisher saved successfully"),
                    @ApiResponse(responseCode = "409", description = "Publisher already registered"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized")
            }
    )
    public ResponseEntity<PublisherDto> savePublisher(@RequestBody PublisherDto publisherDto){
        PublisherDto publisherDtoFound = publisherService.save(publisherDto);
        return new ResponseEntity<>(publisherDtoFound, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_LIBRARIAN')")
    @Operation(summary = "Update publisher")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Publisher updated successfully"),
                    @ApiResponse(responseCode = "404", description = "Publisher not found"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized")
            }
    )
    public ResponseEntity<PublisherDto> updatePublisher(@PathVariable Long id, @RequestBody PublisherDto publisherDto){
        PublisherDto publisherUpdated = publisherService.update(id, publisherDto);
        return ResponseEntity.ok(publisherUpdated);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_LIBRARIAN')")
    @Operation(summary = "Delete publisher")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "404", description = "Publisher not found"),
                    @ApiResponse(responseCode = "204", description = "Publisher deleted successfully"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized")
            }
    )
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        publisherService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}