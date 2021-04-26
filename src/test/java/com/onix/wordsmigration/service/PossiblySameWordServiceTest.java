package com.onix.wordsmigration.service;

import com.onix.wordsmigration.BaseTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ContextConfiguration(classes = {PossiblySameWordService.class})
public class PossiblySameWordServiceTest extends BaseTest {

    @Autowired
    private PossiblySameWordService possiblySameWordService;

    @Test
    public void isSame() {
        final List<String> words = Arrays.asList(
                "Test", "So2thing", "Superma1", "spring", "1ava", "springb55t", "unit6t"
        );
        assertTrue(this.possiblySameWordService.isSame("spring", words));
        assertTrue(this.possiblySameWordService.isSame("Superman", words));
        assertTrue(this.possiblySameWordService.isSame("something", words));
        assertTrue(this.possiblySameWordService.isSame("java", words));
        assertTrue(this.possiblySameWordService.isSame("springboot", words));
        assertFalse(this.possiblySameWordService.isSame("sommething", words));
        assertFalse(this.possiblySameWordService.isSame("superban", words));
        assertFalse(this.possiblySameWordService.isSame("unittest", words));
    }

}
