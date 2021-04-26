package com.onix.wordsmigration.service;

import com.onix.wordsmigration.BaseTest;
import com.onix.wordsmigration.entity.WordEntity;
import com.onix.wordsmigration.repository.WordRepository;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ContextConfiguration(classes = {WordService.class})
public class WordServiceTest extends BaseTest {

    private static final int FOREIGN_ID = 1;
    private static final String WORD = "word";
    private static final String WORD_2 = "word2";
    private static final Date CREATED = new Date();

    @Autowired
    private WordService wordService;

    @MockBean
    private WordRepository wordRepository;
    @MockBean
    private WordCacheService wordCacheService;
    @MockBean
    private PossiblySameWordService possiblySameWordService;

    @Test
    public void save() {
        this.wordService.save(FOREIGN_ID, WORD, CREATED);

        final ArgumentCaptor<WordEntity> wordEntityArgumentCaptor = ArgumentCaptor.forClass(WordEntity.class);
        verify(this.wordRepository).save(wordEntityArgumentCaptor.capture());
        assertEquals(FOREIGN_ID, wordEntityArgumentCaptor.getValue().getForeignId());
        assertEquals(WORD, wordEntityArgumentCaptor.getValue().getWord());
        assertEquals(CREATED, wordEntityArgumentCaptor.getValue().getCreated());
    }

    @Test
    public void findAll() {
        final List<WordEntity> wordEntities = new ArrayList<>();
        when(this.wordRepository.findAll()).thenReturn(wordEntities);

        final List<WordEntity> result = this.wordService.findAll();

        assertEquals(wordEntities, result);

        verify(this.wordRepository).findAll();
    }

    @Test
    public void findAllFromCache() {
        final List<WordEntity> wordEntities = new ArrayList<>();
        when(this.wordCacheService.findAll()).thenReturn(wordEntities);

        final List<WordEntity> result = this.wordService.findAllFromCache();

        assertEquals(wordEntities, result);

        verify(this.wordCacheService).findAll();
    }

    @Test
    public void findByForeignIdFromCache() {
        final Optional<WordEntity> wordOptional = Optional.of(new WordEntity());
        when(this.wordCacheService.findByForeignId(FOREIGN_ID)).thenReturn(wordOptional);

        final Optional<WordEntity> result = this.wordService.findByForeignIdFromCache(FOREIGN_ID);

        assertEquals(wordOptional, result);

        verify(this.wordCacheService).findByForeignId(FOREIGN_ID);
    }

    @Test
    public void cacheEvict() {
        this.wordService.cacheEvict();

        verify(this.wordCacheService).cacheEvict();
    }

    @Test
    public void isPossiblySame() {
        when(this.possiblySameWordService.isSame(eq(WORD), any())).thenReturn(true);

        when(this.wordCacheService.findAll()).thenReturn(
                Arrays.asList(new WordEntity(FOREIGN_ID, WORD, CREATED), new WordEntity(FOREIGN_ID, WORD_2, CREATED))
        );

        assertTrue(this.wordService.isPossiblySame(WORD));

        verify(this.wordCacheService).findAll();

        final ArgumentCaptor<List<String>> listArgumentCaptor = ArgumentCaptor.forClass(List.class);
        verify(this.possiblySameWordService).isSame(eq(WORD), listArgumentCaptor.capture());
        assertEquals(2, listArgumentCaptor.getValue().size());
        assertEquals(WORD, listArgumentCaptor.getValue().get(0));
        assertEquals(WORD_2, listArgumentCaptor.getValue().get(1));
    }

}
