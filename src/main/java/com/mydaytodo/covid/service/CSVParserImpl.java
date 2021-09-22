package com.mydaytodo.covid.service;

import com.mydaytodo.covid.models.CasesByDate;
import com.mydaytodo.covid.models.Dataset;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Repository;
import org.springframework.util.ResourceUtils;

import java.io.*;
import java.text.DateFormat;
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

    @Autowired
    ResourceLoader resourceLoader;

    public CSVParserImpl() {
        super();
        this.parseDataset(this.defaultFilename);
    }
    @Override
    public Dataset parseDataset(String filePath) {
        List<CovidCase> cases = new ArrayList<>();

        try {
            InputStream is = this.getClass().getClassLoader().getResourceAsStream(filePath);
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
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

    @Override
    public List<CasesByDate> caseAggregateByInf(Integer postcode) {
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        Map<String, CasesByDate> caseMap = new HashMap<>();
        for(CovidCase c: dataCache.getCovidCases()) {

            if((c.getPostcode() != null) && c.getPostcode().equals(postcode)) {
                String pattern = c.getLikely_source_of_infection()
                        .trim()
                        .toLowerCase(Locale.ROOT) + ":" + df.format(c.getNotification_date());
                CasesByDate caseByDate = caseMap.get(pattern);
                if (caseByDate == null) {
                    caseByDate = new CasesByDate();
                    caseByDate.setDate(c.getNotification_date());
                    caseByDate.setLhdName(c.getLhd_2010_name());
                    caseByDate.setSource(c.getLikely_source_of_infection());
                }
                caseByDate.setCount(caseByDate.getCount() + 1);
                caseMap.put(pattern, caseByDate);
            }
        }
        List<CasesByDate> cases = caseMap.values().stream().collect(Collectors.toList());
        cases.sort(new Comparator<CasesByDate>() {
            @Override
            public int compare(CasesByDate o1, CasesByDate o2) {
                return o2.getDate().compareTo(o1.getDate());
            }
        });
        return cases;
    }
}
