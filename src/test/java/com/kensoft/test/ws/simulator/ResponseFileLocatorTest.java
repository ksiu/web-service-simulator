package com.kensoft.test.ws.simulator;


import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import java.io.File;
import javax.xml.soap.SOAPMessage;

import org.junit.Test;

import com.kensoft.test.ws.utils.ClassPathResourceUtils;
import com.kensoft.test.ws.utils.SoapMessageUtils;
import com.kensoft.test.ws.utils.SoapVersion;

public class ResponseFileLocatorTest {

    private ResponseFileLocator responseFileLocator = new ResponseFileLocator();;

    @Test
    public void getFile() throws Exception {
        SOAPMessage request = SoapMessageUtils.createMessage(ClassPathResourceUtils.getResourceAsFile("/multipart.txt"));
        File file = responseFileLocator.getFile(request, SoapVersion.SOAP11);
        assertThat(file.exists(), equalTo(true));
    }

    @Test
    public void getFaultFile() throws Exception {
        File file = responseFileLocator.getFileForSoapFault(SoapVersion.SOAP11);
        assertThat(file.exists(), equalTo(true));
    }
}