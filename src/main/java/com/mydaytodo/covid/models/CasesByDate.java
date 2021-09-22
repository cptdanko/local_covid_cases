package com.mydaytodo.covid.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@NoArgsConstructor
@Getter
@Setter
public class CasesByDate {

    private Date date;
    private int count;
    private String lhdName;
    private String source;
}
