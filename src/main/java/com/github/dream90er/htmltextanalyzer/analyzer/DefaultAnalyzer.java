package com.github.dream90er.htmltextanalyzer.analyzer;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.regex.Pattern;

import com.github.dream90er.htmltextanalyzer.analyzer.sax.IncludeFilterHandler;
import com.github.dream90er.htmltextanalyzer.analyzer.sax.ConsumerHandler;
import com.github.dream90er.htmltextanalyzer.analyzer.sax.ExcludeFilterHandler;

import org.ccil.cowan.tagsoup.Parser;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class DefaultAnalyzer implements Analyzer {

    private static final String DELIMETRS_REGEX_STRING = "[\\s,\\.\\!\\?\";:\\[\\]\\(\\)\\n\\r\\t]+";

    private static final Pattern DELIMETRS_REGEX_PATTERN = Pattern.compile(DELIMETRS_REGEX_STRING);

    @Override
    public Map<String, Integer> analyze(Path pathToFile) {
        Map<String, Integer> resultMap = new LinkedHashMap<>();
        DefaultHandler handler = createHandlersChain(resultMap);
        Parser parser = new Parser();
        parser.setContentHandler(handler);
        try (InputStream fileInputStream = new BufferedInputStream (
                new FileInputStream(pathToFile.toFile()))) {
            InputSource source = new InputSource(fileInputStream); 
            parser.parse(source);
        } catch (IOException e) {
            throw new AnalyzerException("An exception occurred while reading file" + pathToFile.toString(), e);
        } catch (SAXException e) {
            throw new AnalyzerException("An exception occurred while parsing file" + pathToFile.toString(), e);
        }
        return resultMap;
    }

    private DefaultHandler createHandlersChain(Map<String, Integer> resultMap) {
        DefaultHandler handler = ConsumerHandler.getInstance(getElementConsumer(resultMap));
        DefaultHandler bodyHandler = IncludeFilterHandler.getInstance(handler, "body");
        DefaultHandler styleHandler = ExcludeFilterHandler.getInstance(bodyHandler, "style");
        DefaultHandler scriptHandler = ExcludeFilterHandler.getInstance(styleHandler, "script");
        return scriptHandler;
    }

    private Consumer<String> getElementConsumer(Map<String, Integer> resultMap) {
        return (string) ->
            Arrays.stream(DELIMETRS_REGEX_PATTERN.split(string))
                .map(word -> word.toUpperCase())
                .filter(word -> !word.equals(""))
                .forEach(word -> resultMap.compute(word, 
                    (key, value) -> null == value ? 1 : value + 1));
    }
    
}
