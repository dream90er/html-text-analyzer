package com.github.dream90er.htmltextanalyzer.analyzer;

import static org.junit.jupiter.api.Assertions.*;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class DefaultAnalyzerTest {

    private static final  DefaultAnalyzer defaultAnalyzer = DefaultAnalyzer.getInstance();

    private static final Path resourceDirectory = Paths.get("src","test","resources");

    private static final String EXAMPLE_HTML = "example.html";

    private static final Map<String, Integer> EXAMPLE_RESULT;
    
    static {
        EXAMPLE_RESULT = new HashMap<>();
        EXAMPLE_RESULT.put("EXAMPLE", 1);
        EXAMPLE_RESULT.put("DOMAIN", 3);
        EXAMPLE_RESULT.put("THIS", 2);
        EXAMPLE_RESULT.put("IS", 1);
        EXAMPLE_RESULT.put("FOR", 2);
        EXAMPLE_RESULT.put("USE", 2);
        EXAMPLE_RESULT.put("IN", 3);
        EXAMPLE_RESULT.put("ILLUSTRATIVE", 1);
        EXAMPLE_RESULT.put("EXAMPLES", 1);
        EXAMPLE_RESULT.put("DOCUMENTS", 1);
        EXAMPLE_RESULT.put("YOU", 1);
        EXAMPLE_RESULT.put("MAY", 1);
        EXAMPLE_RESULT.put("LITERATURE", 1);
        EXAMPLE_RESULT.put("WITHOUT", 1);
        EXAMPLE_RESULT.put("PRIOR", 1);
        EXAMPLE_RESULT.put("COORDINATION", 1);
        EXAMPLE_RESULT.put("OR", 1);
        EXAMPLE_RESULT.put("ASKING", 1);
        EXAMPLE_RESULT.put("PERMISSION", 1);
        EXAMPLE_RESULT.put("MORE", 1);
        EXAMPLE_RESULT.put("INFORMATION", 1);
    }
    
    @Test
    public void shouldAnswerWithRightAnalyzeOfExample() {
        //given
        Path testFile = resourceDirectory.resolve(EXAMPLE_HTML);

        //when
        Map<String, Integer> analyzeResult = defaultAnalyzer.analyze(testFile);

        //then
        assertEquals(EXAMPLE_RESULT, analyzeResult);
    }

    @ParameterizedTest
    @CsvSource({
            "example.html, 21",
            "SAX.html, 686",
            "TagSoup.html, 822"
    })
    public void shouldAnswerWithRightCountOfUniqueWords(String fileName, 
            Integer uniqueWords) {
        //given
        Path testFile = resourceDirectory.resolve(fileName);

        //when
        Map<String, Integer> analyzeResult = defaultAnalyzer.analyze(testFile);

        //then
        assertEquals(uniqueWords, analyzeResult.size());
    }

    @ParameterizedTest
    @CsvSource({
            "example.html, 28",
            "SAX.html, 1856",
            "TagSoup.html, 2439"
    })
    public void shouldAnswerWithRightCountOfTotalWords(String fileName, 
            Integer totalWords) {
        //given
        Path testFile = resourceDirectory.resolve(fileName);

        //when
        Map<String, Integer> analyzeResult = defaultAnalyzer.analyze(testFile);

        //then
        assertEquals(totalWords, 
            analyzeResult.values()
                .stream()
                .reduce(0, (acc, next) -> acc + next));
    }
    
}
