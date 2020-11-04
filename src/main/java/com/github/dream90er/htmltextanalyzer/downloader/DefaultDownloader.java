package com.github.dream90er.htmltextanalyzer.downloader;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Default implementation of {@link Downloader} interface.
 * 
 * @author Sychev Alexey 
 */ 
public class DefaultDownloader implements Downloader {

    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultDownloader.class);

    //Acceptable content type.
    private static final String CONTENT_TYPE = "text/html";

    private static final Pattern CONTENT_TYPE_PATTERN = Pattern.compile(CONTENT_TYPE);

    //Acceptable protocols.
    private static final String PROTOCOL_REGEX = "https?";

    private static final Pattern PROTOCOL_PATTERN = Pattern.compile(PROTOCOL_REGEX);

    private final Path pathToTempFile;

    protected DefaultDownloader(Path pathToTempFile) {
        this.pathToTempFile = pathToTempFile;
    }

    @Override
    public Path download(URL pageUrl) {
        try (FileChannel fileChannel = createChannelToFile(pathToTempFile);
            ReadableByteChannel urlChannel = createChannelFromPageUrl(pageUrl)) {
            LOGGER.info("Downloading from url: {} started.", pageUrl);
            fileChannel.transferFrom(urlChannel, 0, Long.MAX_VALUE);
            LOGGER.info("Downloading from url: {} ended successfully.", pageUrl);
            return pathToTempFile;
        } catch (IOException e) {
            throw new DownloaderException("An exception occurred while downloading", e);
        }
    }

    /**
     * Get NIO channel to the file on hard drive.
     */
    @SuppressWarnings("resource")
    private FileChannel createChannelToFile(Path pathToFile) {
        try {
            return new FileOutputStream(pathToFile.toFile(), false).getChannel();
        } catch (Exception e) {
            throw new DownloaderException("Can't create stream from file", e);
        }
    }

    /**
     * Get NIO channel to the resource represented by URL.
     */
    private ReadableByteChannel createChannelFromPageUrl(URL pageUrl) {
        try {
            verifyProtocol(pageUrl);
            HttpURLConnection connection = (HttpURLConnection) pageUrl.openConnection();
            verifyConnection(connection);
            return Channels.newChannel(connection.getInputStream());
        } catch (IOException e) {
            throw new DownloaderException("Can't connect to url: " + pageUrl, e);
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

    /**
     * Get a {@code DefaultDownloader} instance.
     * 
     * @param pathToTempFileString path to the temp file
     * @return {@code DefaultDownloader} instance
     */
    public static DefaultDownloader getInstance(String pathToTempFileString) {
        Path pathToTempFile = createTempFile(pathToTempFileString);
        return new DefaultDownloader(pathToTempFile);
    }

    /**
    * Create temp file and parent directories if they didn't exists. Return path to the 
    * file.
    */
    private static Path createTempFile(String pathToTempFileString) {
        try {
            Path tempFile = Paths.get(pathToTempFileString);
            Path tempDir = tempFile.getParent();
            if (tempDir != null) Files.createDirectories(tempDir);
            return Files.exists(tempFile)
                ? tempFile
                : Files.createFile(tempFile);
        } catch (Exception e) {
            throw new DownloaderException(
                "An exception occurred while creating temp file", e);
        }
    }

}
