package com.github.dream90er.htmltextanalyzer.downloader;

import java.net.URL;
import java.nio.file.Path;

/**
 * Web page downloader.
 * 
 * @author Sychev Alexey 
 */ 
public interface Downloader {

    /**
     * Download content of web page to a file on hard drive.
     * 
     * @param pageUrl URL of web page for downloading
     * @return path to the downloaded file
     * @throws DownloaderException if an exception occurred during downloading
     */
    Path download(URL pageUrl);

}
