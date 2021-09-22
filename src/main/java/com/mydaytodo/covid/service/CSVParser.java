package com.mydaytodo.covid.service;

import com.mydaytodo.covid.models.CasesByDate;
import com.mydaytodo.covid.models.Dataset;

import java.util.List;

public interface CSVParser {

    public Dataset parseDataset(String filePath);

    public Dataset byPostcode(Integer postcode);

    // total cases on a date by source of infection
    public List<CasesByDate> caseAggregateByInf(Integer postcode);
}
