package com.kensoft.test.ws.simulator;

import static com.kensoft.test.ws.utils.ClassPathResourceUtils.getResourceAsStream;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.soap.SOAPMessage;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.mock.web.DelegatingServletInputStream;

@RunWith(MockitoJUnitRunner.class)
public class SoapServletTest {

    @Mock
    private ResponseGenerator responseGenerator;
    @Mock
    private PrintWriter printWriter;
    @InjectMocks
    private SoapServlet  soapServlet;

    @Test
    public void testPost() throws IOException, ServletException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
//        when(request.getHeaderNames()).thenReturn(new StringTokenizer(""));
        when(request.getInputStream()).thenReturn(new DelegatingServletInputStream(getResourceAsStream("soap-request.xml")));
        when(responseGenerator.createResponse(any())).thenReturn(mock(SOAPMessage.class));
        when(response.getWriter()).thenReturn(printWriter);

        soapServlet.doPost(request, response);

        verify(printWriter).write("");
    }
}