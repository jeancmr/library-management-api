package com.jeancmr.library_management.service;

import com.jeancmr.library_management.dto.PublisherDto;

import java.util.List;

public interface IPublisherService {
    List<PublisherDto> findAll();
    PublisherDto findById(Long id);
    PublisherDto save(PublisherDto publisherDto);
    PublisherDto update(Long id, PublisherDto publisherDto);
    void deleteById(Long id);
}