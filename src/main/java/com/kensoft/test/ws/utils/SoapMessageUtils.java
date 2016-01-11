package com.kensoft.test.ws.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.MimeHeaders;
import javax.xml.soap.SOAPBodyElement;
import javax.xml.soap.SOAPConstants;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.UnhandledException;

import com.kensoft.test.ws.simulator.InvalidMessageException;

public final class SoapMessageUtils {

    private SoapMessageUtils() {}

    public static SOAPMessage createMessage(File file) {
        try (InputStream stream = new FileInputStream(file)) {
            return createMessage(stream);
        } catch (IOException e) {
            throw new UnhandledException(e);
        }
    }

    public static SOAPMessage createMessage(InputStream stream) {
        return createMessage(stream, new MimeHeaders());
    }

    public static SOAPMessage createMessage(InputStream stream, MimeHeaders headers) {
        try {
            byte[] bytes = IOUtils.toByteArray(stream);
            MessageFactory messageFactory = getMessageFactory(bytes);
            return messageFactory.createMessage(headers, new ByteArrayInputStream(bytes));
        } catch (SOAPException | IOException e) {
            throw new UnhandledException(e);
        }
    }

    private static MessageFactory getMessageFactory(byte[] bytes) throws SOAPException {
        String messageAsText = new String(bytes);
        if (messageAsText.contains(SoapVersion.SOAP12.getNamespace())) {
            return MessageFactory.newInstance(SOAPConstants.SOAP_1_2_PROTOCOL);
        } else if (messageAsText.contains(SoapVersion.SOAP11.getNamespace())) {
            return MessageFactory.newInstance();
        } else {
            throw new InvalidMessageException("Invalid SOAP message: " + messageAsText);
        }
    }

    public static String toString(SOAPMessage message) {
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        try {
            message.writeTo(bout);
            return bout.toString("UTF-8");
        } catch (SOAPException | IOException e) {
            throw new UnhandledException(e);
        }
    }

    public static String toString(SOAPBodyElement element) {
        DOMSource source = new DOMSource(element);
        StringWriter stringResult = new StringWriter();
        try {
            TransformerFactory.newInstance().newTransformer().transform(source, new StreamResult(stringResult));
            return stringResult.toString();
        } catch (TransformerException e) {
            throw new UnhandledException(e);
        }
    }

    public static SoapVersion getSoapVersion(SOAPMessage message) {
        try {
            String namespace = message.getSOAPPart().getEnvelope().getNamespaceURI();
            return SoapVersion.getVersionByNamespace(namespace);
        } catch (SOAPException e) {
            throw new UnhandledException(e);
        }
    }
}
