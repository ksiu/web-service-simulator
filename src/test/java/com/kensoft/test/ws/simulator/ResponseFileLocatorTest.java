package com.kensoft.test.ws.simulator;

import static com.kensoft.test.ws.utils.ClassPathResourceUtils.getResourceAsFile;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import java.io.File;
import java.io.IOException;
import javax.xml.soap.SOAPMessage;

import com.kensoft.test.ws.utils.SoapMessageUtils;
import com.kensoft.test.ws.utils.SoapVersion;
import org.junit.Test;

public class ResponseFileLocatorTest {

    private ResponseFileLocator responseFileLocator = new ResponseFileLocator();;

    @Test
    public void getFile() throws IOException {
        SOAPMessage request = SoapMessageUtils.createMessage(getResourceAsFile("/multipart.txt"));
        File file = responseFileLocator.getFile(request, SoapVersion.SOAP11);
        assertThat(file.exists(), equalTo(true));
    }

    @Test
    public void getFaultFile() throws IOException {
        File file = responseFileLocator.getFileForSoapFault(SoapVersion.SOAP11);
        assertThat(file.exists(), equalTo(true));
    }
}