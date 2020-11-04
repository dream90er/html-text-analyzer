package com.github.dream90er.htmltextanalyzer.resulthandler;

import com.github.dream90er.htmltextanalyzer.entity.AnalyzeResult;
import com.github.dream90er.htmltextanalyzer.service.AnalyzeResultService;
import com.github.dream90er.htmltextanalyzer.service.ServiceException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * {@link ResultHandler} implementation that persists the {@link AnalyzeResult} to the 
 * database using the underlying {@link AnalyzeResultService}.
 * 
 * @author Sychev Alexey 
 */ 
public class ServiceResultHandler implements ResultHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(ServiceResultHandler.class);

    private final AnalyzeResultService analyzeResultService;

    protected ServiceResultHandler(AnalyzeResultService analyzeResultService) {
        this.analyzeResultService = analyzeResultService;
    }
    
    @Override
    public void handle(AnalyzeResult result) {
        try {
            analyzeResultService.saveAnalyzeResult(result);
            LOGGER.info("Result handled by {}", getClass().getName());
        } catch (ServiceException e) {
            LOGGER.warn(e.getMessage(), e);
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
