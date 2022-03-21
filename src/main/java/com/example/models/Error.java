package com.example.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Error {
    private int code;
    private String type;
    private String info;
}
