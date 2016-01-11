package com.kensoft.test.ws.simulator;

import javax.xml.soap.SOAPMessage;

import com.kensoft.test.ws.utils.SoapVersion;

public interface ResponseGenerator {
    SOAPMessage createResponse(SOAPMessage request);
    SOAPMessage createResponse(Exception exception, SoapVersion soapVersion);
}
