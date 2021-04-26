package com.onix.wordsmigration.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public final class CachedPossiblySameRequest {

    @NotBlank
    private String word;

}
