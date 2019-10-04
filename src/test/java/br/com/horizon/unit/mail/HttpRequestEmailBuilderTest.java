package br.com.horizon.unit.mail;

import static org.junit.jupiter.api.Assertions.assertEquals;

import br.com.horizon.email.HttpRequestEmailBuilder;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import org.apache.tomcat.util.codec.binary.Base64;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;

public class HttpRequestEmailBuilderTest {

    @Test
    public void shouldBuildHttpRequestWithUri() throws URISyntaxException {
        var builder = new HttpRequestEmailBuilder();

        var uri = new URI("https://api.mailgun.com");

        var httpRequest = builder.build(uri, "", "");

        assertEquals("api.mailgun.com", httpRequest.uri().getHost());
    }

    @Test
    public void shouldBuildWithBody() throws URISyntaxException {
        var builder = new HttpRequestEmailBuilder();

        var uri = new URI("https://api.mailgun.com");

        var body = "from=rosin1408@gmail.com&to=rosin1408@gmail.com";

        var httpRequest = builder.build(uri, body, "");

        assertEquals(47L, httpRequest.bodyPublisher().get().contentLength());
    }

    @Test
    public void shouBuildWithAuthenticationHeader() throws URISyntaxException {
        var apiKey = "123456794f65sd4f.6as5fd.f5s4afds.6fd4fdsf";
        var auth = "api" + ":" + apiKey;
        byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(StandardCharsets.ISO_8859_1));
        var authenticationHeader = "Basic " + new String(encodedAuth);

        var builder = new HttpRequestEmailBuilder();

        var uri = new URI("https://api.mailgun.com");

        var body = "from=rosin1408@gmail.com&to=rosin1408@gmail.com";

        var httpRequest = builder.build(uri, body, apiKey);

        assertEquals(1, httpRequest.headers().allValues(HttpHeaders.AUTHORIZATION).size());
        assertEquals(authenticationHeader, httpRequest.headers().allValues(HttpHeaders.AUTHORIZATION).get(0));
    }
}
