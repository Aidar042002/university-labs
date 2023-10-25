package com.example.web_lab2.beans;

import java.util.ArrayList;
import java.util.List;

public class ResultsBean {
    private List<Results> results;

    public ResultsBean(){
        this(new ArrayList<>());
    }

    public ResultsBean(List<Results> results){
        this.results=results;
    }

    public List<Results> getResults() {
        return results;
    }

    public void setResults(List<Results> results) {
        this.results = results;
    }
}
