package com.github.dream90er.htmltextanalyzer.resulthandler;

import com.github.dream90er.htmltextanalyzer.entity.AnalyzeResult;
import com.github.dream90er.htmltextanalyzer.service.AnalyzeResultService;
import com.github.dream90er.htmltextanalyzer.service.ServiceException;

/**
 * {@link ResultHandler} implementation that persists the {@link AnalyzeResult} to the 
 * database using the underlying {@link AnalyzeResultService}.
 * 
 * @author Sychev Alexey 
 */ 
public class ServiceResultHandler implements ResultHandler {

    private final AnalyzeResultService analyzeResultService;

    protected ServiceResultHandler(AnalyzeResultService analyzeResultService) {
        this.analyzeResultService = analyzeResultService;
    }
    
    @Override
    public void handle(AnalyzeResult result) {
        try {
            analyzeResultService.saveAnalyzeResult(result);
        } catch (ServiceException e) {
            //TODO log
            System.err.println(e.getMessage());
        }
    }

    /**
     * Get a {@code ServiceResultHandler} instance.
     * 
     * @param analyzeResultService {@link AnalyzeResultService} instance that will be used 
     * for persistence.
     * @return {@code ServiceResultHandler} instance.
     */
    public static ServiceResultHandler getInstance(
            AnalyzeResultService analyzeResultService) {
        ServiceResultHandler sqLiteResultHandler = 
            new ServiceResultHandler(analyzeResultService);
        return sqLiteResultHandler;
    }
    
}
