package com.onix.wordsmigration.validation;

import com.onix.wordsmigration.ValidationHelper;
import com.onix.wordsmigration.request.MigrationOcrRequest;
import lombok.Getter;
import lombok.Setter;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import java.util.Calendar;
import java.util.Date;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Getter
@Setter
public class MigrationOcrRequestTest {

    private static final int FOREIGN_ID = 1;
    private static final String WORD = "word1";
    private static final Date CREATED = new Date();

    @Test
    public void success() {
        final MigrationOcrRequest migrationOcrRequest = new MigrationOcrRequest();
        migrationOcrRequest.setForeignId(FOREIGN_ID);
        migrationOcrRequest.setWord(WORD);
        migrationOcrRequest.setCreated(CREATED);

        final Set<ConstraintViolation<MigrationOcrRequest>> validates = ValidationHelper.validator
                .validate(migrationOcrRequest);
        assertTrue(validates.isEmpty());
    }

    @Test
    public void failForeignIdNull() {
        final MigrationOcrRequest migrationOcrRequest = new MigrationOcrRequest();
        migrationOcrRequest.setWord(WORD);
        migrationOcrRequest.setCreated(CREATED);

        final Set<ConstraintViolation<MigrationOcrRequest>> validates = ValidationHelper.validator
                .validate(migrationOcrRequest);
        assertEquals(1, validates.size());
    }

    @Test
    public void failWordNull() {
        final MigrationOcrRequest migrationOcrRequest = new MigrationOcrRequest();
        migrationOcrRequest.setForeignId(FOREIGN_ID);
        migrationOcrRequest.setCreated(CREATED);

        final Set<ConstraintViolation<MigrationOcrRequest>> validates = ValidationHelper.validator
                .validate(migrationOcrRequest);
        assertEquals(1, validates.size());
    }

    @Test
    public void failWordEmpty() {
        final MigrationOcrRequest migrationOcrRequest = new MigrationOcrRequest();
        migrationOcrRequest.setForeignId(FOREIGN_ID);
        migrationOcrRequest.setWord("");
        migrationOcrRequest.setCreated(CREATED);

        final Set<ConstraintViolation<MigrationOcrRequest>> validates = ValidationHelper.validator
                .validate(migrationOcrRequest);
        assertEquals(1, validates.size());
    }

    @Test
    public void failWordInvalid() {
        final MigrationOcrRequest migrationOcrRequest = new MigrationOcrRequest();
        migrationOcrRequest.setForeignId(FOREIGN_ID);
        migrationOcrRequest.setWord(WORD + "@");
        migrationOcrRequest.setCreated(CREATED);

        final Set<ConstraintViolation<MigrationOcrRequest>> validates = ValidationHelper.validator
                .validate(migrationOcrRequest);
        assertEquals(1, validates.size());
    }

    @Test
    public void failCreatedInvalid() {
        final MigrationOcrRequest migrationOcrRequest = new MigrationOcrRequest();
        migrationOcrRequest.setForeignId(FOREIGN_ID);
        migrationOcrRequest.setWord(WORD);
        migrationOcrRequest.setCreated(new Date(new Calendar.Builder()
                .setDate(2014, 11, 31)
                .build()
                .getTimeInMillis()
        ));

        final Set<ConstraintViolation<MigrationOcrRequest>> validates = ValidationHelper.validator
                .validate(migrationOcrRequest);
        assertEquals(1, validates.size());
        assertEquals("Invalid created datetime.", validates.iterator().next().getMessage());
    }

}
