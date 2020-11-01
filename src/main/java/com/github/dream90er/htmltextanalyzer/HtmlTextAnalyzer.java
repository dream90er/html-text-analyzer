package com.github.dream90er.htmltextanalyzer;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

import com.github.dream90er.htmltextanalyzer.analyzer.Analyzer;
import com.github.dream90er.htmltextanalyzer.downloader.Downloader;
import com.github.dream90er.htmltextanalyzer.entity.AnalyzeResult;
import com.github.dream90er.htmltextanalyzer.resulthandler.ResultHandler;

public class HtmlTextAnalyzer {

    private static final String TEMP_DIRECTORY_NAME = "temp";

    private static final String TEMP_FILE_NAME = "temp.html";
    
    private final Downloader downloader;

    private final Analyzer analyzer;

    private List<ResultHandler> resultHandlers;

    public HtmlTextAnalyzer() {
        this(DefaultHtmlAnalyzerConfig.getInstance());
    }

    public HtmlTextAnalyzer(HtmlTextAnalyzerConfig config) {
        this.downloader = config.getDownloader();
        this.analyzer = config.getAnalyzer();
        this.resultHandlers = config.getResultHandlers();
    }

    public void analyze(String pageUrl) {
        Path tempDir = cteateTempDirectory();
        Path tempFile = createTempFile(tempDir);
        downloader.download(pageUrl, tempFile);
        Map<String, Integer> resultMap = analyzer.analyze(tempFile);
        AnalyzeResult analyzeResult = AnalyzeResult.getInstance(pageUrl, resultMap);
        for (ResultHandler resultHandler : resultHandlers) {
            resultHandler.handle(analyzeResult);
        }
    }

    private static Path createTempFile(Path tempDir) {
        try {
            Path tempFile = tempDir.resolve(TEMP_FILE_NAME);
            if (Files.exists(tempFile)) Files.delete(tempFile);
            return Files.createFile(tempFile);
        } catch (Exception e) {
            throw new HtmlTextAnalyzerException("An exception occurred while creating temp file", e);
        }
    }

    private static Path cteateTempDirectory() {
        try {
            Path tempDir = Paths.get(TEMP_DIRECTORY_NAME);
            return  Files.createDirectories(tempDir);
        } catch (Exception e) {
            throw new HtmlTextAnalyzerException("An exception occurred while creating temp directory", e);
        }
    }

}
