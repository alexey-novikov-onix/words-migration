package com.onix.wordsmigration.validation;

import com.onix.wordsmigration.ValidationHelper;
import com.onix.wordsmigration.request.CachedPossiblySameRequest;
import lombok.Getter;
import lombok.Setter;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Getter
@Setter
public class CachedPossiblySameRequestTest {

    private static final String WORD = "word";

    @Test
    public void success() {
        final CachedPossiblySameRequest cachedPossiblySameRequest = new CachedPossiblySameRequest();
        cachedPossiblySameRequest.setWord(WORD);

        final Set<ConstraintViolation<CachedPossiblySameRequest>> validates = ValidationHelper.validator
                .validate(cachedPossiblySameRequest);
        assertTrue(validates.isEmpty());
    }

    @Test
    public void failEmpty() {
        final CachedPossiblySameRequest cachedPossiblySameRequest = new CachedPossiblySameRequest();
        cachedPossiblySameRequest.setWord("");

        final Set<ConstraintViolation<CachedPossiblySameRequest>> validates = ValidationHelper.validator
                .validate(cachedPossiblySameRequest);
        assertEquals(1, validates.size());
    }

    @Test
    public void failNull() {
        final CachedPossiblySameRequest cachedPossiblySameRequest = new CachedPossiblySameRequest();

        final Set<ConstraintViolation<CachedPossiblySameRequest>> validates = ValidationHelper.validator
                .validate(cachedPossiblySameRequest);
        assertEquals(1, validates.size());
    }

}
