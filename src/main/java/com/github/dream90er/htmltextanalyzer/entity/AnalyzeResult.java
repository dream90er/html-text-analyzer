package com.github.dream90er.htmltextanalyzer.entity;

import java.util.Collections;
import java.util.Map;

public class AnalyzeResult {

    private final String pageUrl;

    private final Map<String, Integer> resultMap;

    protected AnalyzeResult(String pageUrl, Map<String, Integer> resultMap) {
        this.pageUrl = pageUrl;
        this.resultMap = Collections.unmodifiableMap(resultMap);
    }

    public String getPageUrl() {
        return pageUrl;
    }

    public Map<String, Integer> getResultMap() {
        return resultMap;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((pageUrl == null) ? 0 : pageUrl.hashCode());
        result = prime * result + ((resultMap == null) ? 0 : resultMap.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        AnalyzeResult other = (AnalyzeResult) obj;
        if (pageUrl == null) {
            if (other.pageUrl != null)
                return false;
        } else if (!pageUrl.equals(other.pageUrl))
            return false;
        if (resultMap == null) {
            if (other.resultMap != null)
                return false;
        } else if (!resultMap.equals(other.resultMap))
            return false;
        return true;
    }

    public static AnalyzeResult getInstance(String pageUrl, Map<String, Integer> resultMap) {
        return new AnalyzeResult(pageUrl, resultMap);
    }

    public String getResultMapAsString() {
        StringBuilder stringBuilder = new StringBuilder();
        resultMap.forEach((string, count) -> stringBuilder
            .append(string)
            .append(" - ")
            .append(count)
            .append("\n"));
        if (!resultMap.isEmpty())
            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        return stringBuilder.toString();
    }
    
}
