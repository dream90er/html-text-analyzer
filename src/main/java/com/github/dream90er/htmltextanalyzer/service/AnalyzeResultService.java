package com.github.dream90er.htmltextanalyzer.service;

import com.github.dream90er.htmltextanalyzer.entity.AnalyzeResult;

/**
 * Service that provides persistance for {@link AnalyzeResult}
 * 
 * @author Sychev Alexey 
 */ 
public interface AnalyzeResultService {
    
    /**
     * Save {@link AnalyzeResult} to a database.
     * 
     * @param analyzeResult {@link AnalyzeResult}
     */
    void saveAnalyzeResult(AnalyzeResult analyzeResult);

}
