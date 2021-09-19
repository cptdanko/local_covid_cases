package com.mydaytodo.covid.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@NoArgsConstructor
@Getter
@Setter
public class CovidCase {
    private Date notification_date;
    private Integer postcode;
    private String likely_source_of_infection;
    private String lhd_2010_code;
    private String lhd_2010_name;
    private Integer lga_code19;
    private String lga_name19;

    public CovidCase(String notification_date,
                     Integer postcode,
                     String likely_source_of_infection,
                     String lhd_2010_code,
                     String lhd_2010_name,
                     Integer lga_code19,
                     String lga_name19) {

        this.postcode = postcode;
        this.likely_source_of_infection = likely_source_of_infection;
        this.lhd_2010_code = lhd_2010_code;
        this.lhd_2010_name = lhd_2010_name;
        this.lga_code19 = lga_code19;
        this.lga_name19 = lga_name19;
        try {

            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            //SimpleDateFormat.getDateInstance().pa
            this.notification_date = dateFormat.parse(notification_date);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public CovidCase(String[] values) {
        if(values.length > 6) {
            if(!values[1].equals("None")
            && !values[1].equals("Masked")) {
                this.postcode = Integer.parseInt(values[1]);
            }
            this.likely_source_of_infection = values[2];
            this.lhd_2010_code = values[3];
            this.lhd_2010_name = values[4];
            if((Integer.getInteger(values[5])) != null) {
                this.lga_code19 = Integer.parseInt(values[5]);
            }
            this.lga_name19 = values[6];

            try {
                String dateStr = values[0];
                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                this.notification_date = dateFormat.parse(dateStr);
            } catch (ParseException pe) {
                pe.printStackTrace();
            }
        }
    }
}
