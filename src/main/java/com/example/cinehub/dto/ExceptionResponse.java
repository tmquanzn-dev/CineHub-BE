package com.example.cinehub.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ExceptionResponse {
    private String error;
    private List<String> details = new ArrayList<>();
    private LocalDateTime timestamp = LocalDateTime.now();
}
