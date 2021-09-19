package com.mydaytodo.covid.service;

import com.mydaytodo.covid.models.Dataset;

public interface CSVParser {

    public Dataset parseDataset(String filePath);

    public Dataset byPostcode(Integer postcode);
}
