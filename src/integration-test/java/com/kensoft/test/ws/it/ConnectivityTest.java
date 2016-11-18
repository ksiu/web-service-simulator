package com.kensoft.test.ws.it;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import org.junit.Test;

public class ConnectivityTest {
    @Test
    public void hello() throws IOException {
        String strUrl = "http://localhost:8080";

        URL url = new URL(strUrl);
        HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();

        assertEquals(HttpURLConnection.HTTP_OK, urlConn.getResponseCode());
    }
}
