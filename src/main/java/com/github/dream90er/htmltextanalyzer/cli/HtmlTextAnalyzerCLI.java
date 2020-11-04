package com.github.dream90er.htmltextanalyzer.cli;

import java.net.MalformedURLException;
import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.dream90er.htmltextanalyzer.HtmlTextAnalyzer;

/**
 * Command line interface for {@link HtmlTextAnalyzer} 
 * 
 * @author Sychev Alexey 
 */ 
public class HtmlTextAnalyzerCLI {

    private static final Logger LOGGER = LoggerFactory.getLogger(HtmlTextAnalyzerCLI.class);

    public static void main(String...args) {
        try {
            URL pageUrl = getUrlFromArgs(args);
            HtmlTextAnalyzer htmlTextAnalyzer = new HtmlTextAnalyzer();
            htmlTextAnalyzer.analyze(pageUrl);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    /**
     * Check input arguments and return URL instance.
     * 
     * @param args input arguments
     * @return URL instance
     */
    private static URL getUrlFromArgs(String...args) {
        if (args.length == 0) {
            throw new IllegalArgumentException("Blank argument string");
        }
        try {
            return new URL(args[0]);
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException("Malformed URL: " + args[0], e);
        }
    }

}
