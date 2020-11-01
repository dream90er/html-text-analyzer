package com.github.dream90er.htmltextanalyzer.analyzer.sax;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class ExcludeFilterHandler extends HandlerDecorator {

    private final String filterTag;

    private boolean inFilterTag = false;

    protected ExcludeFilterHandler(DefaultHandler handler, String filterTag) {
        super(handler);
        this.filterTag = filterTag;
    }


    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        if (!inFilterTag)
            super.characters(ch, start, length);
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (!inFilterTag)
            super.endElement(uri, localName, qName);
        if (filterTag.equals(localName)) inFilterTag = false;
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        if (filterTag.equals(localName)) inFilterTag = true;
        if (!inFilterTag)
            super.startElement(uri, localName, qName, attributes);
    }

    public static ExcludeFilterHandler getInstance(DefaultHandler handler, String filterTag) {
        return new ExcludeFilterHandler(handler, filterTag);
    }

}
