package com.example.models;

import com.example.persistence.Conversion;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ConversionListResponse {

    public List<Conversion> conversionList;
}
