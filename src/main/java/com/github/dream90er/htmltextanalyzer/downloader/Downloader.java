package com.github.dream90er.htmltextanalyzer.downloader;

import java.nio.file.Path;

public interface Downloader {

    void download(String pageUrl, Path pathToFile);

}
