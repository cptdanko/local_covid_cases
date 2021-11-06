package com.mydaytodo.covid.controller;

import com.mydaytodo.covid.models.CasesByDate;
import com.mydaytodo.covid.models.CovidCase;
import com.mydaytodo.covid.models.Dataset;
import com.mydaytodo.covid.service.CSVParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ExecutionException;
import java.util.function.Supplier;


@RestController
@RequestMapping("data")
public class ProcessDataController {

    @Autowired
    private CSVParser csvParser;

    @GetMapping("/raw")
    public Dataset parseToJson() {
        return csvParser.parseDataset("cnfrm_case_table4_location_likely_source.csv");
    }

    @GetMapping("/byPostcode/{postcode}")
    public Dataset casesByPostcode(@PathVariable("postcode") Integer postcode) {
        return csvParser.byPostcode(postcode);
    }

    @GetMapping("/byPostcode/{postcode}/aggregate")
    public List<CasesByDate> aggregateCasesByPcode(@PathVariable("postcode") Integer postcode) {
        return csvParser.caseAggregateByInf(postcode);
    }
}
