package org.summerframework.web;

/**
 * @author Tenton Lien
 * @date 12/25/2020
 */
public class HttpRequest {
    private final String RAW;
    private String method;
    private String uri;

    public HttpRequest(String raw) {
        this.RAW = raw;
        String firstLine = raw.substring(0, raw.indexOf("\n"));
        String[] firstLineElements = firstLine.split(" ");
        if (firstLineElements.length == 3) {
            method = firstLineElements[0];
            uri = firstLineElements[1];
        }
        System.out.println(toString());
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    @Override
    public String toString() {
        return "HttpRequest{" +
                "method='" + method + '\'' +
                ", uri='" + uri + '\'' +
                '}';
    }
}
