package com.github.dream90er.htmltextanalyzer;

import java.net.URL;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

import com.github.dream90er.htmltextanalyzer.analyzer.Analyzer;
import com.github.dream90er.htmltextanalyzer.downloader.Downloader;
import com.github.dream90er.htmltextanalyzer.entity.AnalyzeResult;
import com.github.dream90er.htmltextanalyzer.resulthandler.ResultHandler;

/**
 * Facade class for accesing Html Text Analyzer functionality.
 * 
 * @author Sychev Alexey 
 */
public class HtmlTextAnalyzer {

    private final Downloader downloader;

    private final Analyzer analyzer;

    private List<ResultHandler> resultHandlers;

    /**
     * Create a Html Text Analyzer facade using the given configuration.
     * 
     * @param config {@link HtmlTextAnalyzerConfig} class
     */
    public HtmlTextAnalyzer(HtmlTextAnalyzerConfig config) {
        this.downloader = config.getDownloader();
        this.analyzer = config.getAnalyzer();
        this.resultHandlers = config.getResultHandlers();
    }

    /**
     * Create a Html Text Analyzer facade using the default configuration.
     * 
     * @see {@link DefaultHtmlAnalyzerConfig} 
     */
    public HtmlTextAnalyzer() {
        this(DefaultHtmlAnalyzerConfig.getInstance());
    }

    /**
     * Download a web page from given URL to temp file, analyze and handle results.
     * 
     * @param pageUrl URL of a web page
     * @throws DownloaderException if an exception occurred during the download phase
     * @throws AnalyzerException if an exception occurred during the analysis phase
     */
    public void analyze(URL pageUrl) {
        Path pathToDownloadedFile = downloader.download(pageUrl);
        Map<String, Integer> resultMap = analyzer.analyze(pathToDownloadedFile);
        AnalyzeResult analyzeResult = AnalyzeResult.getInstance(pageUrl.toString(), resultMap);
        for (ResultHandler resultHandler : resultHandlers) {
            resultHandler.handle(analyzeResult);
        }
    }

}
