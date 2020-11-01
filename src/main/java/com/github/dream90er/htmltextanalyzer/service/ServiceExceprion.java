package com.github.dream90er.htmltextanalyzer.service;

import com.github.dream90er.htmltextanalyzer.HtmlTextAnalyzerException;

@SuppressWarnings("serial")
public class ServiceExceprion extends HtmlTextAnalyzerException {

    public ServiceExceprion(String msg) {
        super(msg);
    }

    public ServiceExceprion(String msg, Throwable cause) { 
        super(msg, cause); 
    }
    
}
