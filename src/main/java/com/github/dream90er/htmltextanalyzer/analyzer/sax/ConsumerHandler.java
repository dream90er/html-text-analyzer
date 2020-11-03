package com.github.dream90er.htmltextanalyzer.analyzer.sax;

import java.util.function.Consumer;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * Content handler decorator that passes text of elements to the underlying consumer.
 * 
 * @author Sychev Alexey 
 */ 
public class ConsumerHandler extends HandlerDecorator {

    private final Consumer<String> consumer;

    private StringBuilder stringBuilder = new StringBuilder();

    protected ConsumerHandler(Consumer<String> consumer, DefaultHandler handler) {
        super(handler);
        this.consumer = consumer;
    }

    protected ConsumerHandler(Consumer<String> consumer) {
        this(consumer, new DefaultHandler());
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        String text = new String(ch, start, length);
        stringBuilder.append(text);
    }

    @Override
    public void startElement(String uri, String localName, String qName, 
            Attributes attributes) throws SAXException {
        processElement();
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        processElement();
    }

    /**
     * Pass text from buffer to the consumer and clear buffer.
     */
    private void processElement() {
        if (stringBuilder.length() > 0) {
            consumer.accept(stringBuilder.toString());
            stringBuilder = new StringBuilder();
        }
    }

    /**
     * Get a {@code ConsumerHandler} instance.
     * 
     * @param consumer that accepts elements text
     * @return {@code ConsumerHandler} instance
     */
    public static ConsumerHandler getInstance(Consumer<String> consumer) {
        return new ConsumerHandler(consumer);
    }

    /**
     * Get a {@code ConsumerHandler} instance.
     * 
     * @param consumer that accepts elements text
     * @param handler next handler in the decorators chain
     * @return {@code ConsumerHandler} instance
     */
    public static ConsumerHandler getInstance(Consumer<String> consumer, 
            DefaultHandler handler) {
        return new ConsumerHandler(consumer, handler);
    }

}