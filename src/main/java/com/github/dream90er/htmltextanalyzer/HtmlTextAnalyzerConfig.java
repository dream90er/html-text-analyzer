package com.github.dream90er.htmltextanalyzer;

import java.util.List;

import com.github.dream90er.htmltextanalyzer.analyzer.Analyzer;
import com.github.dream90er.htmltextanalyzer.downloader.Downloader;
import com.github.dream90er.htmltextanalyzer.resulthandler.ResultHandler;

/**
 * Configuration of Html Text Analyzer
 * 
 * @author Sychev Alexey 
 */ 
public interface HtmlTextAnalyzerConfig {

    /**
     * Return a specific {@link Downloader} according to the configuration
     */
    Downloader getDownloader();

        /**
     * Return a specific {@link Analyzer} according to the configuration
     */
    Analyzer getAnalyzer();

        /**
     * Return a list of specific {@link ResultHandler} according to the configuration
     */
    List<ResultHandler> getResultHandlers();
    
}
