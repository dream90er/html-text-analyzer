package com.github.dream90er.htmltextanalyzer.downloader;

import com.github.dream90er.htmltextanalyzer.HtmlTextAnalyzerException;

@SuppressWarnings("serial")
public class DownloaderException extends HtmlTextAnalyzerException {
    
    public DownloaderException(String msg) {
        super(msg);
    }

    public DownloaderException(String msg, Throwable cause) { 
        super(msg, cause); 
    }
    
}
