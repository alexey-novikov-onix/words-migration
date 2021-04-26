package com.onix.wordsmigration.controller;

import com.onix.wordsmigration.BaseTest;
import com.onix.wordsmigration.entity.WordEntity;
import com.onix.wordsmigration.request.CachedPossiblySameRequest;
import com.onix.wordsmigration.request.MigrationOcrRequest;
import com.onix.wordsmigration.responce.CachedPossiblySameResponse;
import com.onix.wordsmigration.service.WordService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ContextConfiguration(classes = {ApiController.class})
public class ApiControllerTest extends BaseTest {

    private static final int FOREIGN_ID = 1;
    private static final String WORD = "word";
    private static final Date CREATED = new Date();

    @Autowired
    private ApiController apiController;

    @MockBean
    private WordService wordService;

    @Test
    public void migrationOcr() {
        final MigrationOcrRequest migrationOcrRequest = new MigrationOcrRequest();
        migrationOcrRequest.setForeignId(FOREIGN_ID);
        migrationOcrRequest.setWord(WORD);
        migrationOcrRequest.setCreated(CREATED);

        final ResponseEntity<Void> result = this.apiController.migrationOcr(migrationOcrRequest);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertNull(result.getBody());

        verify(this.wordService).save(FOREIGN_ID, WORD, CREATED);
    }

    @Test
    public void all() {
        final List<WordEntity> wordEntities = new ArrayList<>();
        when(this.wordService.findAll()).thenReturn(wordEntities);

        final ResponseEntity<List<WordEntity>> result = this.apiController.all();

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(wordEntities, result.getBody());

        verify(this.wordService).findAll();
    }

    @Test
    public void cachedAll() {
        final List<WordEntity> wordEntities = new ArrayList<>();
        when(this.wordService.findAllFromCache()).thenReturn(wordEntities);

        final ResponseEntity<List<WordEntity>> result = this.apiController.cachedAll();

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(wordEntities, result.getBody());

        verify(this.wordService).findAllFromCache();
    }

    @Test
    public void cachedDetails() {
        final Optional<WordEntity> wordOptional = Optional.of(new WordEntity());
        when(this.wordService.findByForeignIdFromCache(FOREIGN_ID)).thenReturn(wordOptional);

        final ResponseEntity<WordEntity> result = this.apiController.cachedDetails(FOREIGN_ID);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(wordOptional.get(), result.getBody());

        verify(this.wordService).findByForeignIdFromCache(FOREIGN_ID);
    }

    @Test
    public void cachedDetailsNotFound() {
        final Optional<WordEntity> wordOptional = Optional.empty();
        when(this.wordService.findByForeignIdFromCache(FOREIGN_ID)).thenReturn(wordOptional);

        final ResponseEntity<WordEntity> result = this.apiController.cachedDetails(FOREIGN_ID);

        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
        assertNull(result.getBody());

        verify(this.wordService).findByForeignIdFromCache(FOREIGN_ID);
    }

    @Test
    public void cacheEvict() {
        final ResponseEntity<Void> result = this.apiController.cachedFlush();

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertNull(result.getBody());

        verify(this.wordService).cacheEvict();
    }

    @Test
    public void cachedPossiblySame() {
        final CachedPossiblySameRequest cachedPossiblySameRequest = new CachedPossiblySameRequest();
        cachedPossiblySameRequest.setWord(WORD);
        when(this.wordService.isPossiblySame(WORD)).thenReturn(true);

        final ResponseEntity<CachedPossiblySameResponse> result = this.apiController
                .cachedPossiblySame(cachedPossiblySameRequest);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertNotNull(result.getBody());
        assertTrue(result.getBody().isSame());

        verify(this.wordService).isPossiblySame(WORD);
    }

}
