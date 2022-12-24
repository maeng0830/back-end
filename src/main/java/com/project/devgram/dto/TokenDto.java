package com.project.devgram.dto;

import com.project.devgram.type.ROLE;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;


@Getter
@Setter
@NoArgsConstructor
public class TokenDto {

    @NotNull(message = "꼭 보내주세요 username")
    private String username;


    private ROLE role;
    private String email;
}
