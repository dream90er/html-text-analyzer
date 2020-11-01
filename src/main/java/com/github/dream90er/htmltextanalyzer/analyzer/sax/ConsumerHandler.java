package com.github.dream90er.htmltextanalyzer.analyzer.sax;

import java.util.function.Consumer;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class ConsumerHandler extends HandlerDecorator {

    private final Consumer<String> consumer;

    private StringBuilder stringBuilder = new StringBuilder();

    protected ConsumerHandler(DefaultHandler handler, Consumer<String> consumer) {
        super(handler);
        this.consumer = consumer;
    }

    protected ConsumerHandler(Consumer<String> consumer) {
        this(new DefaultHandler(), consumer);
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        String text = new String(ch, start, length);
            stringBuilder.append(text);
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes)
            throws SAXException {
        processElement();
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        processElement();
    }

    private void processElement() {
        if (stringBuilder.length() > 0) {
            consumer.accept(stringBuilder.toString());
            stringBuilder = new StringBuilder();
        }
    }

    public static ConsumerHandler getInstance(Consumer<String> consumer) {
        return new ConsumerHandler(consumer);
    }

    public static ConsumerHandler getInstance(DefaultHandler handler, Consumer<String> consumer) {
        return new ConsumerHandler(handler, consumer);
    }

}