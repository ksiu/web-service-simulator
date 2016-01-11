package com.kensoft.test.ws.utils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.UnhandledException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public final class XPathUtils {
    private static final ThreadLocal<DocumentBuilder> BUILDER = new ThreadLocal<DocumentBuilder>() {
        @Override
        protected DocumentBuilder initialValue() {
            return createDocumentBuilder();
        }
    };
    private static final ThreadLocal<XPath> XPATH = new ThreadLocal<XPath>() {
        @Override
        protected XPath initialValue() {
            return createXPath();
        }
    };

    private static XPath createXPath() {
        return XPathFactory.newInstance().newXPath();
    }

    private static DocumentBuilder createDocumentBuilder() {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(false);
        try {
            return factory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            throw new UnhandledException(e);
        }
    }

    private XPathUtils() {
    }

    public static String getNodeValue(Node node, String xpath) {
        try {
            return XPATH.get().evaluate(xpath, node);
        } catch (XPathExpressionException e) {
            throw new UnhandledException(e);
        }
    }

    public static String getNodeValue(String xml, String xpath) {
        try {
            Document doc = BUILDER.get().parse(new ByteArrayInputStream(xml.getBytes("UTF-8")));
            return XPATH.get().evaluate(xpath, doc);
        } catch (IOException | XPathExpressionException | SAXException e) {
            throw new UnhandledException(e);
        }
    }

    public static NodeList getNodeList(Node node, String xpath) {
        try {
            return (NodeList) XPATH.get().evaluate(xpath, node, XPathConstants.NODESET);
        } catch (XPathExpressionException e) {
            throw new UnhandledException(e);
        }
    }

    public static NodeList getNodeList(String xml, String xpath) {
        try {
            Document doc = BUILDER.get().parse(new ByteArrayInputStream(xml.getBytes("UTF-8")));
            return (NodeList) XPATH.get().evaluate(xpath, doc, XPathConstants.NODESET);
        } catch (IOException | SAXException | XPathExpressionException e) {
            throw new UnhandledException(e);
        }
    }

    public static List<String> getTextValues(String xml, String xpath) {
        NodeList nodeList = getNodeList(xml, xpath);
        List<String> result = new ArrayList<String>();
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node item = nodeList.item(i);
            String text = item.getTextContent();
            if (StringUtils.isNotBlank(text)) result.add(text);
        }
        return result;
    }
}

