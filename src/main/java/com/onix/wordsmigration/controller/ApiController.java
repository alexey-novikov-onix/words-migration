package com.onix.wordsmigration.controller;

import com.onix.wordsmigration.entity.WordEntity;
import com.onix.wordsmigration.request.CachedPossiblySameRequest;
import com.onix.wordsmigration.request.MigrationOcrRequest;
import com.onix.wordsmigration.responce.CachedPossiblySameResponse;
import com.onix.wordsmigration.service.WordService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public final class ApiController {

    private final WordService wordService;

    @PostMapping("/migration/ocr")
    public ResponseEntity<Void> migrationOcr(final @Valid @RequestBody MigrationOcrRequest migrationOcrRequest) {
        this.wordService.save(
                migrationOcrRequest.getForeignId(),
                migrationOcrRequest.getWord(),
                migrationOcrRequest.getCreated()
        );

        return ResponseEntity.ok().build();
    }

    @GetMapping("/all")
    public ResponseEntity<List<WordEntity>> all() {
        return ResponseEntity.ok(this.wordService.findAll());
    }

    @GetMapping("/cached/all")
    public ResponseEntity<List<WordEntity>> cachedAll() {
        return ResponseEntity.ok(this.wordService.findAllFromCache());
    }

    @GetMapping("/cached/details/{foreignId}")
    public ResponseEntity<WordEntity> cachedDetails(final @PathVariable int foreignId) {
        return this.wordService.findByForeignIdFromCache(foreignId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/cached/flush")
    public ResponseEntity<Void> cachedFlush() {
        this.wordService.cacheEvict();

        return ResponseEntity.ok().build();
    }

    @GetMapping("/cached/possibly-same")
    public ResponseEntity<CachedPossiblySameResponse> cachedPossiblySame(
            final @Valid CachedPossiblySameRequest cachedPossiblySameRequest
    ) {
        return ResponseEntity.ok(
                new CachedPossiblySameResponse(
                        this.wordService.isPossiblySame(cachedPossiblySameRequest.getWord())
                )
        );
    }

}
