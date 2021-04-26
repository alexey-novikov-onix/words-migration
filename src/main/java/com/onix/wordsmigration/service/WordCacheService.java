package com.onix.wordsmigration.service;

import com.onix.wordsmigration.entity.WordEntity;
import com.onix.wordsmigration.repository.WordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class WordCacheService {

    private final WordRepository wordRepository;

    @Cacheable(value = "words")
    public List<WordEntity> findAll() {
        return this.wordRepository.findAll();
    }

    @Cacheable(value = "words", key = "#foreignId")
    public Optional<WordEntity> findByForeignId(final int foreignId) {
        return this.wordRepository.findById(foreignId);
    }

    @CacheEvict(value = "words", allEntries = true)
    public void cacheEvict() {

    }

}
