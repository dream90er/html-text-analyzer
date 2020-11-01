package com.github.dream90er.htmltextanalyzer.cli;

import com.github.dream90er.htmltextanalyzer.HtmlTextAnalyzer;

public class HtmlTextAnalyzerCLI {

    public static void main(String...args) {
        if (args.length == 0) {
            //TODO
            return;
        }
        HtmlTextAnalyzer htmlTextAnalyzer = new HtmlTextAnalyzer();
        htmlTextAnalyzer.analyze(args[0]);
    }

}
