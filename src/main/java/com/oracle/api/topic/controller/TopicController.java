package com.oracle.api.topic.controller;

import com.oracle.api.topic.dto.TopicPublicDto;
import com.oracle.api.topic.dto.TopicRegisterDto;
import com.oracle.api.topic.dto.TopicUpdateDto;
import com.oracle.api.topic.model.Topic;
import com.oracle.api.topic.service.TopicService;
import com.oracle.api.topic.util.mapper.TopicMapper;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/topics")
@AllArgsConstructor
public class TopicController {
    private final TopicService service;
    private final TopicMapper mapper;

    @PreAuthorize("hasAuthority('BASE_USER')")
    @PostMapping
    public ResponseEntity<TopicPublicDto> create(
        @RequestBody TopicRegisterDto body,
        @RequestHeader(name = "Authorization") String token
    ) {
        Topic entity = service.create(body, token.substring(7));
        TopicPublicDto response = mapper.toPublicDto(entity);

        return ResponseEntity
            .status(HttpStatus.CREATED)
            .headers(this.buildLocationHeader(response))
            .body(response);
    }

    @PreAuthorize("hasAuthority('BASE_USER')")
    @GetMapping("/{id}")
    public ResponseEntity<TopicPublicDto> readById(
        @PathVariable UUID id
    ){
        Topic entity = service.readById(id);
        TopicPublicDto response = mapper.toPublicDto(entity);

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(response);
    }

    @PreAuthorize("hasAuthority('BASE_USER')")
    @GetMapping("/course")
    public ResponseEntity<List<TopicPublicDto>> readByCourseName(
        @RequestParam("name") String name
    ) {
        List<TopicPublicDto> response = service.readByCourseName(name.toLowerCase())
            .stream()
            .map(mapper::toPublicDto)
            .toList();

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(response);
    }

    @PreAuthorize("hasAuthority('BASE_USER')")
    @GetMapping("/created")
    public ResponseEntity<List<TopicPublicDto>> readFirstsByDate(
        @RequestParam(name = "limit", defaultValue = "10") int limit
    ) {
        List<TopicPublicDto> response = service.readFirstsByDate(limit)
            .stream()
            .map(mapper::toPublicDto)
            .toList();

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(response);
    }

    @PreAuthorize("hasAuthority('BASE_USER')")
    @GetMapping("/pages")
    public ResponseEntity<Page<TopicPublicDto>> readPageable(
        @PageableDefault(size = 15, sort = "title", direction = Sort.Direction.ASC) Pageable pageable
    ) {
        Page<TopicPublicDto> response = service
            .readPageable(pageable)
            .map(mapper::toPublicDto);

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(response);
    }

    @PreAuthorize("hasAuthority('BASE_USER')")
    @GetMapping
    public ResponseEntity<List<TopicPublicDto>> readAll() {
        List<TopicPublicDto> response = service.readAll()
            .stream()
            .map(mapper::toPublicDto)
            .toList();

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(response);
    }

    @PreAuthorize("hasAuthority('BASE_USER')")
    @PutMapping("/{id}")
    public ResponseEntity<TopicPublicDto> update(
        @PathVariable UUID id,
        @RequestBody TopicUpdateDto body
    ) {
        Topic entity = service.update(id, body);
        TopicPublicDto response = mapper.toPublicDto(entity);

        return ResponseEntity
            .status(HttpStatus.CREATED)
            .headers(this.buildLocationHeader(response))
            .body(response);
    }

    @PreAuthorize("hasAuthority('BASE_USER')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
        @PathVariable UUID id
    ) {
        service.delete(id);

        return ResponseEntity
            .status(HttpStatus.NO_CONTENT)
            .build();
    }

    @NonNull
    private HttpHeaders buildLocationHeader(
        @NonNull TopicPublicDto response
    ) {
        URI location = UriComponentsBuilder
            .fromPath("/api/v1/topics")
            .path("/{id}")
            .buildAndExpand(response.id())
            .toUri();

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(location);

        return headers;
    }
}
