package com.onix.wordsmigration.service;

import com.onix.wordsmigration.BaseTest;
import com.onix.wordsmigration.entity.WordEntity;
import com.onix.wordsmigration.repository.WordRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ContextConfiguration(classes = {WordCacheService.class})
public class WordCacheServiceTest extends BaseTest {

    private static final int FOREIGN_ID = 1;

    @Autowired
    private WordCacheService wordCacheService;

    @MockBean
    private WordRepository wordRepository;

    @Test
    public void findAll() {
        final List<WordEntity> wordEntities = new ArrayList<>();
        when(this.wordRepository.findAll()).thenReturn(wordEntities);

        final List<WordEntity> result = this.wordCacheService.findAll();

        assertEquals(wordEntities, result);

        verify(this.wordRepository).findAll();
    }

    @Test
    public void findByForeignId() {
        final Optional<WordEntity> wordOptional = Optional.of(new WordEntity());
        when(this.wordRepository.findById(FOREIGN_ID)).thenReturn(wordOptional);

        final Optional<WordEntity> result = this.wordCacheService.findByForeignId(FOREIGN_ID);

        assertEquals(wordOptional, result);

        verify(this.wordRepository).findById(FOREIGN_ID);
    }

    @Test
    public void cacheEvict() {
        this.wordCacheService.cacheEvict();
    }

}
