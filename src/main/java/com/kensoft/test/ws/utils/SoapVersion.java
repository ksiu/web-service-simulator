package com.kensoft.test.ws.utils;

import java.util.Map;

import com.google.common.collect.ImmutableMap;

public enum SoapVersion {
    SOAP11("1.1", "http://schemas.xmlsoap.org/soap/envelope/", "text/xml; charset=\"utf-8\""),
    SOAP12("1.2", "http://www.w3.org/2003/05/soap-envelope", "application/soap+xml; charset=utf-8");

    private static final Map<String, SoapVersion> SOAP_VERSIONS = ImmutableMap.of(SOAP11.getNamespace(), SOAP11, SOAP12.getNamespace(), SOAP12);

    private final String versionNumber;
    private final String namespace;
    private final String contentType;

    SoapVersion(String versionNumber, String namespace, String contentType) {
        this.versionNumber = versionNumber;
        this.namespace = namespace;
        this.contentType = contentType;
    }

    public String getVersionNumber() {
        return versionNumber;
    }

    public String getNamespace() {
        return namespace;
    }

    public String getContentType() {
        return contentType;
    }

    public static SoapVersion getVersionByNamespace(String namespace) {
        return SOAP_VERSIONS.get(namespace);
    }

}
