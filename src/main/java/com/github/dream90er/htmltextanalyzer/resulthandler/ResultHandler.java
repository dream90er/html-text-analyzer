package com.github.dream90er.htmltextanalyzer.resulthandler;

import com.github.dream90er.htmltextanalyzer.entity.AnalyzeResult;

/**
 * {@link AnalyzeResult} handler.
 * 
 * @author Sychev Alexey 
 */ 
public interface ResultHandler {

    /**
     * Handle {@link AnalyzeResult} entity.
     * 
     * @param result @link AnalyzeResult} entity.
     */
    void handle(AnalyzeResult result);
    
}
