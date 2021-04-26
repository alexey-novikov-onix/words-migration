package com.onix.wordsmigration.repository;

import com.onix.wordsmigration.RepositoryTest;
import com.onix.wordsmigration.entity.WordEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class WordRepositoryTest extends RepositoryTest {

    private static final int FOREIGN_ID = 1;
    private static final String WORD = "word";
    private static final Date CREATED = new Date();

    @Autowired
    private WordRepository wordRepository;

    @Test
    public void saveAndFind() {
        final WordEntity wordEntity = new WordEntity();
        wordEntity.setForeignId(FOREIGN_ID);
        wordEntity.setWord(WORD);
        wordEntity.setCreated(CREATED);

        this.wordRepository.save(wordEntity);

        final Optional<WordEntity> wordOptional = this.wordRepository.findById(FOREIGN_ID);
        assertTrue(wordOptional.isPresent());
        assertEquals(FOREIGN_ID, wordOptional.get().getForeignId());
        assertEquals(WORD, wordOptional.get().getWord());
        assertEquals(CREATED, wordOptional.get().getCreated());
    }

}
