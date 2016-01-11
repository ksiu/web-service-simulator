package com.kensoft.test.ws.simulator;

import java.io.File;
import java.io.IOException;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;

import org.apache.commons.lang.UnhandledException;
import org.w3c.dom.Node;

import com.kensoft.test.ws.utils.ClassPathResourceUtils;
import com.kensoft.test.ws.utils.SoapVersion;
import com.kensoft.test.ws.utils.XPathUtils;

public class ResponseFileLocator {
    private static final String SOAP_FAULT_FILE = "/responses/1.2/soap-fault.xml";

    public File getFile(SOAPMessage request, SoapVersion soapVersion) throws IOException {
        return ClassPathResourceUtils.getResourceAsFile(getFilePath(request, soapVersion));
    }

    public File getFileForSoapFault(SoapVersion soapVersion){
        try {
            return ClassPathResourceUtils.getResourceAsFile(SOAP_FAULT_FILE);
        } catch (IOException e) {
            throw new UnhandledException(e);
        }
    }

    private String getFilePath(SOAPMessage request, SoapVersion soapVersion) {
        try {
            Node requestNode = XPathUtils.getNodeList(request.getSOAPPart().getEnvelope().getBody(), "./*[local-name()]").item(0);
            return "/responses/" + soapVersion.getVersionNumber() + "/" + requestNode.getLocalName() + ".xml";
        } catch (SOAPException e) {
            return SOAP_FAULT_FILE;
        }
    }
}
