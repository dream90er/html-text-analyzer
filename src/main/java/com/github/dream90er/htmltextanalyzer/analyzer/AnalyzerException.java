package com.github.dream90er.htmltextanalyzer.analyzer;

import com.github.dream90er.htmltextanalyzer.HtmlTextAnalyzerException;

@SuppressWarnings("serial")
public class AnalyzerException extends HtmlTextAnalyzerException {

    public AnalyzerException(String msg) {
        super(msg);
    }

    public AnalyzerException(String msg, Throwable cause) { 
        super(msg, cause); 
    }
    
}
