package com.onix.wordsmigration.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "words")
public class WordEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @JsonProperty("foreign_id")
    private Integer foreignId;
    private String word;
    private Date created;

}
