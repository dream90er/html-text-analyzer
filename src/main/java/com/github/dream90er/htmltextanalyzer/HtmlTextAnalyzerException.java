package com.github.dream90er.htmltextanalyzer;

/**
 * Base Html Text Analyzer exception
 * 
 * @author Sychev Alexey 
 */ 
@SuppressWarnings("serial")
public class HtmlTextAnalyzerException extends RuntimeException {

    public HtmlTextAnalyzerException(String msg) {
        super(msg);
    }

    public HtmlTextAnalyzerException(String msg, Throwable cause) { 
        super(msg, cause); 
    }
    
}
