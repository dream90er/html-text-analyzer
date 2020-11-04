package com.github.dream90er.htmltextanalyzer.analyzer;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * Default implementation of {@link Analyzer} interface.
 * Uses TagSoup parser and custom SAX handlers for html parsing.
 * Resulting text chunks passed to consumer that splits them into words, counts and adds 
 * to the result map.
 * 
 * @author Sychev Alexey 
 * @see IncludeFilterHandler
 * @see ExcludeFilterHandler
 * @see ConsumerHandler
 * @see #getElementConsumer(Map)
 */ 
public class DefaultAnalyzer implements Analyzer {

    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultAnalyzer.class);

    private static final String DELIMETERS_REGEX_STRING = 
        "[\\s,\\.\\!\\?\";:\\[\\]\\(\\)\\n\\r\\t]+";

    private static final Pattern DELIMETERS_REGEX_PATTERN = 
        Pattern.compile(DELIMETERS_REGEX_STRING);

    private static final Charset CHARSET = StandardCharsets.UTF_8;

    protected DefaultAnalyzer() {
    }

    @Override
    public Map<String, Integer> analyze(Path pathToFile) {
        Map<String, Integer> resultMap = new LinkedHashMap<>();
        DefaultHandler handler = createHandlersChain(resultMap);
        Parser parser = new Parser();
        parser.setContentHandler(handler);
        try (InputStream fileInputStream = new FileInputStream(pathToFile.toFile())) {
            InputSource source = createInputSource(fileInputStream);
            LOGGER.info(
                "File analysis started. File: {}", pathToFile.toAbsolutePath().toString());
            parser.parse(source);
            LOGGER.info(
                "File analysis ended successfully. File: {}", 
                pathToFile.toAbsolutePath().toString());
        } catch (IOException e) {
            throw new AnalyzerException(
                "An exception occurred while reading file" + pathToFile.toString(), e);
        } catch (SAXException e) {
            throw new AnalyzerException(
                "An exception occurred while parsing file" + pathToFile.toString(), e);
        }
        return resultMap;
    }

    /**
     * Create {@code InputSource} from {@code InputStream} with UTF-8 charset.
     */
    private InputSource createInputSource(InputStream fileInputStream) {
        InputStreamReader inputStreamReader = 
            new InputStreamReader(fileInputStream, CHARSET);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        InputSource source = new InputSource(bufferedReader);
        return source;
    }

    /**
     * Create handler chain that excludes content in the style, script elements and 
     * outside of the body element.
     */
    private DefaultHandler createHandlersChain(Map<String, Integer> resultMap) {
        DefaultHandler handler = ConsumerHandler.getInstance(getElementConsumer(resultMap));
        DefaultHandler bodyHandler = IncludeFilterHandler.getInstance(handler, "body");
        DefaultHandler styleHandler = 
            ExcludeFilterHandler.getInstance(bodyHandler, "style");
        DefaultHandler scriptHandler = 
            ExcludeFilterHandler.getInstance(styleHandler, "script");
        return scriptHandler;
    }

    /**
     * Create text consumer that counts unique words and adds them to the map.
     */
    private Consumer<String> getElementConsumer(Map<String, Integer> resultMap) {
        return (string) ->
            Arrays.stream(DELIMETERS_REGEX_PATTERN.split(string))
                .map(word -> word.toUpperCase())
                .filter(word -> !word.equals(""))
                .forEach(word -> resultMap.compute(word, 
                    (key, value) -> null == value ? 1 : value + 1));
    }

    /**
     * Get a {@code DefaultAnalyzer} instance.
     * 
     * @return {@code DefaultAnalyzer} instance
     */
    public static DefaultAnalyzer getInstance() {
        return new DefaultAnalyzer();
    }
    
}
