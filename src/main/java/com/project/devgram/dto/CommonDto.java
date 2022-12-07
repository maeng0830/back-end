package com.project.devgram.dto;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CommonDto<T> {

    private int statusCode;
    private T data;
}
