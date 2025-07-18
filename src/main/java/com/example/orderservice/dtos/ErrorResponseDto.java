package com.example.orderservice.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
public class ErrorResponseDto {
    private String message;
    private int status;
    private Date timestamp;
    private String path;
}

