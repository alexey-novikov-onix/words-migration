package com.onix.wordsmigration.service;

import com.onix.wordsmigration.entity.WordEntity;
import com.onix.wordsmigration.repository.WordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WordService {

    private final WordRepository wordRepository;
    private final WordCacheService wordCacheService;
    private final PossiblySameWordService possiblySameWordService;

    public void save(final int foreignId, final String word, final Date date) {
        this.wordRepository.save(new WordEntity(foreignId, word, date));
    }

    public List<WordEntity> findAll() {
        return this.wordRepository.findAll();
    }

    public List<WordEntity> findAllFromCache() {
        return this.wordCacheService.findAll();
    }

    public Optional<WordEntity> findByForeignIdFromCache(final int foreignId) {
        return this.wordCacheService.findByForeignId(foreignId);
    }

    public void cacheEvict() {
        this.wordCacheService.cacheEvict();
    }

    public boolean isPossiblySame(final String possiblySameWord) {
        return this.possiblySameWordService.isSame(
                possiblySameWord,
                this.wordCacheService.findAll().stream().map(WordEntity::getWord).collect(Collectors.toList())
        );
    }

}
