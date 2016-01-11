package com.kensoft.test.ws.utils;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import java.io.IOException;
import javax.xml.soap.MimeHeaders;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;

import org.apache.commons.io.input.NullInputStream;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.kensoft.test.ws.simulator.InvalidMessageException;

public class SoapMessageUtilsTest {
    private static final String FAULT_STRING = "Message does not have necessary info";

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void createMessage() throws IOException, SOAPException {
        SOAPMessage result = SoapMessageUtils.createMessage(ClassPathResourceUtils.getResourceAsFile("/soap-request.xml"));
        assertThat(result.getSOAPPart().getEnvelope().getBody().hasFault(), equalTo(false));
    }

    @Test
    public void createMessageWithNull() throws IOException, SOAPException {
        expectedException.expect(InvalidMessageException.class);
        SOAPMessage result = SoapMessageUtils.createMessage(new NullInputStream(1));
    }

    @Test
    public void createFaultMessage() throws IOException, SOAPException {
        SOAPMessage result = SoapMessageUtils.createMessage(ClassPathResourceUtils.getResourceAsFile("/responses/1.2/soap-fault.xml"));
        assertThat(result.getSOAPPart().getEnvelope().getBody().hasFault(), equalTo(true));
        assertThat(result.getSOAPPart().getEnvelope().getBody().getFault().getFaultString(), equalTo(FAULT_STRING));
    }

    @Test
    public void createMultipartMessage() throws IOException, SOAPException {
        MimeHeaders headers = new MimeHeaders();
        headers.setHeader("Content-Type","multipart/related; type=\"application/xop+xml\"; start=\"soap\"; start-info=\"text/xml\"; boundary=\"----=_Part_0_1108821662.1421450143368\"\n");
        SOAPMessage result = SoapMessageUtils.createMessage(ClassPathResourceUtils.getResourceAsStream("/multipart.txt"), headers);
        assertThat(result.getSOAPBody().hasFault(), equalTo(false));
    }

    @Test
    public void serializeToXml() throws IOException, SOAPException {
        SOAPMessage result = SoapMessageUtils.createMessage(ClassPathResourceUtils.getResourceAsFile("/responses/1.2/soap-fault.xml"));
        assertThat(SoapMessageUtils.toString(result.getSOAPBody().getFault()), containsString(FAULT_STRING));
    }

    @Test
    public void getSoapVersion() throws IOException {
        SOAPMessage message = SoapMessageUtils.createMessage(ClassPathResourceUtils.getResourceAsFile("/responses/1.1/soap-fault.xml"));
        SoapVersion result = SoapMessageUtils.getSoapVersion(message);
        assertThat(result, equalTo(SoapVersion.SOAP11));

    }
}