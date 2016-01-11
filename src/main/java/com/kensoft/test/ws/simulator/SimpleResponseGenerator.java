package com.kensoft.test.ws.simulator;

import java.io.File;
import java.io.IOException;
import java.util.Locale;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;

import org.apache.commons.lang.UnhandledException;

import com.kensoft.test.ws.utils.SoapMessageUtils;
import com.kensoft.test.ws.utils.SoapVersion;

public class SimpleResponseGenerator implements ResponseGenerator {
    private ResponseFileLocator responseFileLocator = new ResponseFileLocator();

    @Override
    public SOAPMessage createResponse(SOAPMessage request) {
        SoapVersion soapVersion = SoapMessageUtils.getSoapVersion(request);
        try {
            File responseFile = responseFileLocator.getFile(request, soapVersion);
            return SoapMessageUtils.createMessage(responseFile);
        } catch (IOException e) {
            return createResponse(e, soapVersion);
        }
    }

    @Override
    public SOAPMessage createResponse(Exception exception, SoapVersion soapVersion) {
        File responseFile = responseFileLocator.getFileForSoapFault(soapVersion);
        SOAPMessage message = SoapMessageUtils.createMessage(responseFile);
        try {
            message.getSOAPBody().getFault().addFaultReasonText(exception.getMessage(), Locale.US);
            return message;
        } catch (SOAPException e) {
            throw new UnhandledException(e);
        }
    }
}
