package com.github.dream90er.htmltextanalyzer.service;

import com.github.dream90er.htmltextanalyzer.HtmlTextAnalyzerException;

@SuppressWarnings("serial")
public class ServiceException extends HtmlTextAnalyzerException {

    public ServiceException(String msg) {
        super(msg);
    }

    public ServiceException(String msg, Throwable cause) { 
        super(msg, cause); 
    }
    
}
