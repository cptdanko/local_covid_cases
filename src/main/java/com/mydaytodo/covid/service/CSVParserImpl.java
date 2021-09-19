package com.mydaytodo.covid.service;

import com.mydaytodo.covid.models.Dataset;
import org.springframework.stereotype.Repository;
import org.springframework.util.ResourceUtils;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import java.util.stream.Collectors;
import com.mydaytodo.covid.models.CovidCase;

import javax.swing.text.DateFormatter;

@Repository
public class CSVParserImpl implements  CSVParser {

    private Dataset dataCache = new Dataset();
    private final String defaultFilename = "cnfrm_case_table4_location_likely_source.csv";
    public CSVParserImpl() {
        super();
        this.parseDataset(this.defaultFilename);
    }
    @Override
    public Dataset parseDataset(String filePath) {
        List<CovidCase> cases = new ArrayList<>();

        try {
            //cnfrm_case_table4_location_likely_source.csv
            File file = ResourceUtils.getFile("classpath:"+ filePath);
            BufferedReader reader = new BufferedReader(new FileReader(file));
            //skip the first line
            String headerStr = reader.readLine();
            String line = null;
            while((line = reader.readLine()) != null) {
                cases.add(new CovidCase(line.split(",")));
            }
            Collections.sort(cases, new Comparator<CovidCase>() {
                @Override
                public int compare(CovidCase o1, CovidCase o2) {
                    if(o1.getNotification_date() == null || o2.getNotification_date() == null){
                        return 0;
                    }

                    int comparison = o2.getNotification_date().compareTo(o1.getNotification_date());
                    return o1.getNotification_date().compareTo(o2.getNotification_date());
                }
            });
            dataCache.setCovidCases(cases);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return dataCache;
    }

    /**
     * @param postcode
     * @return
     */
    @Override
    public Dataset byPostcode(Integer postcode) {
        List<CovidCase> cases = new ArrayList<>();
        dataCache.getCovidCases()
                .stream()
                .forEach(c -> {
                    if((c.getPostcode() != null) && c.getPostcode().equals(postcode)) {
                        cases.add(c);
                    }
                });
        Dataset newDs = new Dataset();
        newDs.setCovidCases(cases);
        return newDs;
    }
}
