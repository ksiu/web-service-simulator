package com.kensoft.test.ws.simulator;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Enumeration;
import java.util.StringTokenizer;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.soap.MimeHeaders;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;

import com.kensoft.test.ws.utils.SoapMessageUtils;
import org.apache.commons.lang.UnhandledException;


public class SoapServlet extends HttpServlet {

    private ResponseGenerator responseGenerator;

    @Override
    public void init(ServletConfig servletConfig) throws ServletException {
        super.init(servletConfig);
        responseGenerator = new SimpleResponseGenerator();
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        SOAPMessage requestMessage = SoapMessageUtils.createMessage(request.getInputStream(), getHeaders(request));
        SOAPMessage responseMessage = responseGenerator.createResponse(requestMessage);
        response.addHeader("Content-Type", SoapMessageUtils.getSoapVersion(requestMessage).getContentType());

        response.getWriter().write(toString(responseMessage));
    }

    private MimeHeaders getHeaders(HttpServletRequest req) {
        Enumeration headerNames = req.getHeaderNames();
        MimeHeaders headers = new MimeHeaders();
        while (headerNames.hasMoreElements()) {
            String headerName = (String)headerNames.nextElement();
            String headerValue = req.getHeader(headerName);
            StringTokenizer values = new StringTokenizer(headerValue, ",");
            while (values.hasMoreTokens()) {
                headers.addHeader(headerName, values.nextToken().trim());
            }
        }
        return headers;
    }

    public String getServletInfo() {
        return "Ken's Web Service Servlet";
    }

    private static String toString(SOAPMessage message) {
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        try {
            message.writeTo(bout);
            return bout.toString("UTF-8");
        } catch (SOAPException | IOException e) {
            throw new UnhandledException(e);
        }
    }

}
