package com.kensoft.test.ws.simulator;

import static com.kensoft.test.ws.utils.ClassPathResourceUtils.getResourceAsFile;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import java.io.IOException;
import java.util.Locale;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPFault;
import javax.xml.soap.SOAPMessage;

import com.kensoft.test.ws.utils.SoapMessageUtils;
import com.kensoft.test.ws.utils.SoapVersion;

import org.apache.commons.lang.RandomStringUtils;
import org.junit.Test;

public class SimpleResponseGeneratorTest {
    private SimpleResponseGenerator generator = new SimpleResponseGenerator();

    @Test
    public void testCreateResponse() throws SOAPException, IOException {
        SOAPMessage result = generator.createResponse(SoapMessageUtils.createMessage(getResourceAsFile("soap-request.xml")));
        assertThat(result.getSOAPBody().getFault(), is(nullValue()));
    }

    @Test
    public void testCreateResponseWithException() throws SOAPException {
        String randomMessage = RandomStringUtils.random(6);
        SOAPMessage result = generator.createResponse(new IOException(randomMessage), SoapVersion.SOAP12);
        assertThat(result.getSOAPBody().getFault(), instanceOf(SOAPFault.class));
        assertThat(result.getSOAPBody().getFault().getFaultReasonText(Locale.US), equalTo(randomMessage));
    }
}