package com.github.dream90er.htmltextanalyzer.analyzer;

import java.nio.file.Path;
import java.util.Map;

/**
 * Web page analyzer.
 * 
 * @author Sychev Alexey 
 */ 
public interface Analyzer {

    /**
     * Parse given file, count occurrences of unique words and return result as a map.
     * 
     * @param pathToFile path to the file
     * @return map of results
     * @throws AnalyzerException if an exception occurred during analyzing
     */
    Map<String, Integer> analyze(Path pathToFile);

}
