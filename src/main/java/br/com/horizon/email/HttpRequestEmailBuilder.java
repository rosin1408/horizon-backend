package br.com.horizon.email;

import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.nio.charset.StandardCharsets;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

@Component
public class HttpRequestEmailBuilder {

    private static final String FORM_URLENCODED = "application/x-www-form-urlencoded";

    public HttpRequest build(URI uri, String body, String apiKey) {
        return HttpRequest.newBuilder()
                          .uri(uri)
                          .POST(BodyPublishers.ofString(body))
                          .setHeader(HttpHeaders.AUTHORIZATION, buildAuthString(apiKey))
                          .setHeader(HttpHeaders.CONTENT_TYPE, FORM_URLENCODED)
                          .build();
    }

    private String buildAuthString(String apiKey) {
        String auth = "api" + ":" + apiKey;
        byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(StandardCharsets.ISO_8859_1));
        return "Basic " + new String(encodedAuth);
    }
}
