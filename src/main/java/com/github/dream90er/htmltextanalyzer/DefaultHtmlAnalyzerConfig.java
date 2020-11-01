package com.github.dream90er.htmltextanalyzer;

import java.util.ArrayList;
import java.util.List;

import com.github.dream90er.htmltextanalyzer.analyzer.Analyzer;
import com.github.dream90er.htmltextanalyzer.analyzer.DefaultAnalyzer;
import com.github.dream90er.htmltextanalyzer.downloader.DefaultDownloader;
import com.github.dream90er.htmltextanalyzer.downloader.Downloader;
import com.github.dream90er.htmltextanalyzer.resulthandler.ResultHandler;
import com.github.dream90er.htmltextanalyzer.resulthandler.ServiceResultHandler;
import com.github.dream90er.htmltextanalyzer.resulthandler.SystemOutputResultHandler;
import com.github.dream90er.htmltextanalyzer.service.SQLiteAnalyzeRezultService;

public class DefaultHtmlAnalyzerConfig implements HtmlTextAnalyzerConfig {

    private final Downloader downloader;

    private final Analyzer analyzer;

    private final List<ResultHandler> resultHandlers = new ArrayList<>();

    private static final String SQLITE_CONNECTION_STRING = "jdbc:sqlite:htmlTextAnalyzer.db";

    protected DefaultHtmlAnalyzerConfig() {
        this.downloader = new DefaultDownloader();
        this.analyzer = new DefaultAnalyzer();
        this.resultHandlers.add(SystemOutputResultHandler.getInstance());
        SQLiteAnalyzeRezultService service = SQLiteAnalyzeRezultService.getInstance(SQLITE_CONNECTION_STRING);
        this.resultHandlers.add(ServiceResultHandler.getInstance(service));
    }

    @Override
    public Downloader getDownloader() {
        return downloader;
    }

    @Override
    public Analyzer getAnalyzer() {
        return analyzer;
    }

    @Override
    public List<ResultHandler> getResultHandlers() {
        return resultHandlers;
    }

    public static DefaultHtmlAnalyzerConfig getInstance() {
        return new DefaultHtmlAnalyzerConfig();
    }
    
}
