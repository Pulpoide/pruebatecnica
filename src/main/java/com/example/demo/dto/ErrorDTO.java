package com.example.demo.dto;

import java.util.Date;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ErrorDTO {

    private Date timestamp;
    private int status;
    private String message;
    private String path;
}
