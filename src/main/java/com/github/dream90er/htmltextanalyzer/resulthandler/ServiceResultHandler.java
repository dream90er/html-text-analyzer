package com.github.dream90er.htmltextanalyzer.resulthandler;

import com.github.dream90er.htmltextanalyzer.entity.AnalyzeResult;
import com.github.dream90er.htmltextanalyzer.service.AnalyzeResultService;

public class ServiceResultHandler implements ResultHandler {

    private final AnalyzeResultService analyzeResultService;

    protected ServiceResultHandler(AnalyzeResultService analyzeResultService) {
        this.analyzeResultService = analyzeResultService;
    }
    
    @Override
    public void handle(AnalyzeResult result) {
        analyzeResultService.saveAnalyzeResult(result);
    }

    public static ServiceResultHandler getInstance(AnalyzeResultService analyzeResultService) {
        ServiceResultHandler sqLiteResultHandler = new ServiceResultHandler(analyzeResultService);
        return sqLiteResultHandler;
    }
    
}
