package com.onix.wordsmigration.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.onix.wordsmigration.validation.Created;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Date;

@Getter
@Setter
public final class MigrationOcrRequest {

    @NotNull
    @JsonSetter("foreign_id")
    private Integer foreignId;

    @NotNull
    @Pattern(regexp = "^[A-Za-z0-9]+$*")
    private String word;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Created
    private Date created;

}
