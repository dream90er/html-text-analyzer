package com.github.dream90er.htmltextanalyzer.analyzer;

import java.nio.file.Path;
import java.util.Map;

public interface Analyzer {

    Map<String, Integer> analyze(Path pathToFile);

}
