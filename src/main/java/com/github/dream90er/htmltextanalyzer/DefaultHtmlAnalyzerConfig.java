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
import com.github.dream90er.htmltextanalyzer.service.ServiceException;

/**
 * Default configuration of Html Text Analyzer.
 * 
 * @author Sychev Alexey 
 */ public class DefaultHtmlAnalyzerConfig implements HtmlTextAnalyzerConfig {

    /**
     * Default path to the temporary file.
     */
    private static final String TEMP_FILE_PATH = "temp/temp.html";

    /**
     * Default database connection string.
     */
    private static final String SQLITE_CONNECTION_STRING = 
    "jdbc:sqlite:htmlTextAnalyzer.db";

    /**
     * Default {@link Downloader} instances.
     */
    private final Downloader downloader;

    /**
     * Default {@link Analyzer} instances.
     */
    private final Analyzer analyzer;

    /**
     * Default list of {@link ResultHandler} instances.
     */
    private final List<ResultHandler> resultHandlers = new ArrayList<>();

    /**
     * Construct new {@code DefaultHtmlAnalyzerConfig} with {@link DefaultDownloader} as 
     * {@link Downloader} implementation, {@link DefaultAnalyzer} as 
     * {@link Analyzer} implementation and {@link SystemOutputResultHandler}, 
     * {@link ServiceResultHandler}(with {@link SQLiteAnalyzeRezultService} under the hood) 
     * as handlers.
     * 
     * @throws {@link DownloaderException} if {@code DefaultDownloader} can't be instaciated with 
     * {@value #TEMP_FILE_PATH} path to temporary file.
     */
    protected DefaultHtmlAnalyzerConfig() {
        this.downloader = DefaultDownloader.getInstance(TEMP_FILE_PATH);
        this.analyzer = DefaultAnalyzer.getInstance();
        this.resultHandlers.add(SystemOutputResultHandler.getInstance());
        try {
            SQLiteAnalyzeRezultService service = SQLiteAnalyzeRezultService
                .getInstance(SQLITE_CONNECTION_STRING);
            this.resultHandlers.add(ServiceResultHandler.getInstance(service));
        } catch (ServiceException e) {
            //TODO log
            System.err.println(e.getMessage());
        }
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

    /**
     * Get a {@code DefaultHtmlAnalyzerConfig} instance.
     * 
     * @return {@code DefaultHtmlAnalyzerConfig} instance
     * @see {@link #DefaultHtmlAnalyzerConfig()}
     */
    public static DefaultHtmlAnalyzerConfig getInstance() {
        return new DefaultHtmlAnalyzerConfig();
    }
    
}
