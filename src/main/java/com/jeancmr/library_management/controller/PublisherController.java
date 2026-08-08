package com.jeancmr.library_management.controller;

import com.jeancmr.library_management.dto.Publisher.PublisherDto;
import com.jeancmr.library_management.service.interfaces.IPublisherService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/publishers")
@RequiredArgsConstructor
public class PublisherController {
    private final IPublisherService publisherService;

    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_LIBRARIAN', 'ROLE_MEMBER')")
    public ResponseEntity<List<PublisherDto>> findAllPublishers(){
        List<PublisherDto> publisherList = publisherService.findAll();
        return ResponseEntity.ok(publisherList);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_LIBRARIAN')")
    public ResponseEntity<PublisherDto> findPublisherById(@PathVariable Long id){
        PublisherDto publisherDtoFound = publisherService.findById(id);
        return ResponseEntity.ok(publisherDtoFound);
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_LIBRARIAN')")
    public ResponseEntity<PublisherDto> savePublisher(@RequestBody PublisherDto publisherDto){
        PublisherDto publisherDtoFound = publisherService.save(publisherDto);
        return new ResponseEntity<>(publisherDtoFound, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_LIBRARIAN')")
    public ResponseEntity<PublisherDto> updatePublisher(@PathVariable Long id, @RequestBody PublisherDto publisherDto){
        PublisherDto publisherUpdated = publisherService.update(id, publisherDto);
        return ResponseEntity.ok(publisherUpdated);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_LIBRARIAN')")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        publisherService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}