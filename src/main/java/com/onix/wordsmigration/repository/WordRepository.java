package com.onix.wordsmigration.repository;

import com.onix.wordsmigration.entity.WordEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WordRepository extends JpaRepository<WordEntity, Integer> {

}
