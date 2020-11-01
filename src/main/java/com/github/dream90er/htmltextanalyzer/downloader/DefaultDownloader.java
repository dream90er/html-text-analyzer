package com.github.dream90er.htmltextanalyzer.downloader;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Path;
import java.util.regex.Pattern;

public class DefaultDownloader implements Downloader {
    
    public static final String CONTENT_TYPE = "text/html";

    public static final Pattern CONTENT_TYPE_PATTERN = Pattern.compile(CONTENT_TYPE);

    public static final String PROTOCOL_REGEX = "https?";

    public static final Pattern PROTOCOL_PATTERN = Pattern.compile(PROTOCOL_REGEX);

    @Override
    public void download(String pageUrlString, Path pathToFile) {
        try (FileChannel fileChannel = createChannelToFile(pathToFile);
            ReadableByteChannel urlChannel = createChannelFromPageUrl(pageUrlString)) {
            fileChannel.transferFrom(urlChannel, 0, Long.MAX_VALUE);
        } catch (IOException e) {
            throw new DownloaderException("An exception occurred while downloading", e);
        }
    }

    @SuppressWarnings("resource")
    private FileChannel createChannelToFile(Path pathToFile) {
        try {
            return new FileOutputStream(pathToFile.toFile()).getChannel();
        } catch (Exception e) {
            throw new DownloaderException("Can't create stream from file", e);
        }
    }

    private ReadableByteChannel createChannelFromPageUrl(String pageUrlString) {
        try {
            URL pageUrl = getUrlFromString(pageUrlString);
            verifyProtocol(pageUrl);
            HttpURLConnection connection = (HttpURLConnection) pageUrl.openConnection();
            verifyConnection(connection);
            return Channels.newChannel(connection.getInputStream());
        } catch (IOException e) {
            throw new DownloaderException("Can't connect to url: " + pageUrlString, e);
        }
    }

    private void verifyProtocol(URL url) {
        String protocol = url.getProtocol();
        if (!PROTOCOL_PATTERN.matcher(protocol).matches())
        throw new DownloaderException(
            "Invalid protocol (" + protocol + ")");
    }

    private void verifyConnection(HttpURLConnection connection) throws IOException {
        int responseCode = connection.getResponseCode();
        String contentType = connection.getContentType();
        if (responseCode != HttpURLConnection.HTTP_OK) 
            throw new DownloaderException(
                "Bad http response code (" + responseCode + ")");
        if (!CONTENT_TYPE_PATTERN.matcher(contentType).find())
            throw new DownloaderException(
                "Bad content type (" + contentType + ")");
    }

    private URL getUrlFromString(String urlString) {
        try {
            return new URL(urlString);
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException("Malformed URL:" + urlString, e);
        }
    }

}
