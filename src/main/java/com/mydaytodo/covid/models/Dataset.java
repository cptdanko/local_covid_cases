package com.mydaytodo.covid.models;



import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class Dataset implements Serializable {

    private List<CovidCase> covidCases;

    public Dataset(List<CovidCase> cases) {
        this.covidCases = cases;
    }
}
