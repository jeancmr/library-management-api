package com.jeancmr.library_management.service;

import com.jeancmr.library_management.domain.Publisher;
import com.jeancmr.library_management.dto.PublisherDto;
import com.jeancmr.library_management.exception.ResourceAlreadyExistsException;
import com.jeancmr.library_management.exception.ResourceNotFoundException;
import com.jeancmr.library_management.mapper.PublisherMapper;
import com.jeancmr.library_management.repository.PublisherRepository;
import com.jeancmr.library_management.service.interfaces.IPublisherService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PublisherService implements IPublisherService {

    private final PublisherRepository publisherRepository;
    private final PublisherMapper publisherMapper;

    @Override
    public List<PublisherDto> findAll() {
        return publisherRepository.findAll()
                .stream()
                .map(publisherMapper::toDto)
                .toList();
    }

    @Override
    public PublisherDto findById(Long id) {
        return publisherMapper.toDto(findEntityById(id));
    }

    @Override
    public PublisherDto save(PublisherDto publisherDto) {
        if(publisherRepository.existsByName(publisherDto.getName())) {
            throw new ResourceAlreadyExistsException("Publisher", "name", publisherDto.getName());
        }

        Publisher publisher = publisherMapper.toEntity(publisherDto);
        Publisher savedPublisher = publisherRepository.save(publisher);

        return publisherMapper.toDto(savedPublisher);
    }

    @Override
    public PublisherDto update(Long id, PublisherDto publisherDto) {
        Publisher existingPublisher = publisherMapper.toEntity(findById(id));
        publisherMapper.update(publisherDto, existingPublisher);
        Publisher updatedPublisher = publisherRepository.save(existingPublisher);

        return publisherMapper.toDto(updatedPublisher);
    }

    @Override
    public void deleteById(Long id) {
        Long publisherToDeleteId = findById(id).getId();

        publisherRepository.deleteById(publisherToDeleteId);
    }

    @Override
    public Publisher findEntityById(Long id) {
        return publisherRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(Publisher.class, id));
    }
}