package com.github.dream90er.htmltextanalyzer;

import java.util.List;

import com.github.dream90er.htmltextanalyzer.analyzer.Analyzer;
import com.github.dream90er.htmltextanalyzer.downloader.Downloader;
import com.github.dream90er.htmltextanalyzer.resulthandler.ResultHandler;

public interface HtmlTextAnalyzerConfig {

    Downloader getDownloader();

    Analyzer getAnalyzer();

    List<ResultHandler> getResultHandlers();
    
}
