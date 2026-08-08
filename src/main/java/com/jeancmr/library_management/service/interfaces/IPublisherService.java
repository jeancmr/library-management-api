package com.jeancmr.library_management.service.interfaces;

import com.jeancmr.library_management.domain.Publisher;
import com.jeancmr.library_management.dto.Publisher.PublisherDto;

import java.util.List;

public interface IPublisherService {
    List<PublisherDto> findAll();
    PublisherDto findById(Long id);
    Publisher findEntityById(Long id);
    PublisherDto save(PublisherDto publisherDto);
    PublisherDto update(Long id, PublisherDto publisherDto);
    void deleteById(Long id);
}