package com.github.dream90er.htmltextanalyzer.resulthandler;

import com.github.dream90er.htmltextanalyzer.entity.AnalyzeResult;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * {@link ResultHandler} implementation that prints {@link AnalyzeResult} to the system 
 * console.
 * 
 * @author Sychev Alexey 
 */ 
public class SystemOutputResultHandler implements ResultHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(SystemOutputResultHandler.class);

    @Override
    public void handle(AnalyzeResult result) {
        String resultString = buildResultString(result);
        System.out.println(resultString);
        LOGGER.info("Result handled by {}", getClass().getName());
    }

    private String buildResultString(AnalyzeResult result) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Results for:\n")
            .append(result.getPageUrl())
            .append("\n")
            .append(result.getResultMapAsString());
        return stringBuilder.toString();
    }

    /**
     * Get a {@code SystemOutputResultHandler} instance.
     * 
     * @return {@code SystemOutputResultHandler} instance
     */
    public static SystemOutputResultHandler getInstance() {
        return new SystemOutputResultHandler();
    }
    
}
