package com.example.osintme;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Breach {

    @JsonProperty("Name")
    private String name;

    @JsonProperty("BreachDate")
    private String breachDate;

    @JsonProperty("DataClasses")
    private List<String> dataClasses;

    // Constructor
    public Breach() { }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBreachDate() {
        return breachDate;
    }

    public void setBreachDate(String breachDate) {
        this.breachDate = breachDate;
    }

    public List<String> getDataClasses() {
        return dataClasses;
    }

    public void setDataClasses(List<String> dataClasses) {
        this.dataClasses = dataClasses;
    }
}