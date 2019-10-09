package br.com.horizon.unit.mail;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import br.com.horizon.email.EmailBodyBuilder;
import br.com.horizon.email.HttpClientEmailBuilder;
import br.com.horizon.email.HttpRequestEmailBuilder;
import br.com.horizon.email.MailSender;
import br.com.horizon.email.UriEmailBuilder;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class MailSenderTest {

    private static final String API_KEY = "de8c97cb282da488abee3c5413c3299a-baa55c84-9ef0a34c";
    private static final String DOMAIN_NAME = "sandbox15d801020ea5484e931a6cfa1f86a4c5.mailgun.org";

    @Mock
    private HttpRequestEmailBuilder httpRequestBuilder;
    @Mock
    private HttpClientEmailBuilder httpClientBuilder;
    @Mock
    private EmailBodyBuilder bodyBuilder;
    @Mock
    private UriEmailBuilder uriBuilder;
    @InjectMocks
    private MailSender mailSender;

    @Mock
    private HttpClient client;
    @Mock
    private HttpRequest request;
    @Mock
    private HttpResponse<String> response;

    @BeforeEach
    public void mockHttp() throws IOException, InterruptedException {
        when(httpRequestBuilder.build(any(), anyString(), anyString())).thenReturn(request);
        when(httpClientBuilder.build()).thenReturn(client);
        when(client.send(request, BodyHandlers.ofString())).thenReturn(response);
        when(response.statusCode()).thenReturn(200);
    }

    @BeforeEach
    public void mockUriBuider() throws URISyntaxException {
        when(uriBuilder.build(anyString())).thenCallRealMethod();
    }

    @BeforeEach
    public void mockBodyBuilder() {
        when(bodyBuilder.build()).thenReturn("");
    }

    @Test
    public void shouldReturnTrueWhenHttpStatus200() throws InterruptedException, IOException, URISyntaxException {
        boolean hasSend = mailSender.from("rosin1408@gmail.com").to("rosin1408@gmail.com").subject("assunto").text("texto do email!@#$%*()").send();

        assertTrue(hasSend);
    }

    @Test
    public void shouldUseHttpClientBuilderToBuildHttpClient() throws InterruptedException, IOException, URISyntaxException {
        mailSender.from("rosin1408@gmail.com").to("rosin1408@gmail.com").subject("assunto").text("texto do email!@#$%*()").send();

        verify(httpClientBuilder, only()).build();
    }

    @Test
    public void shouldBuildUrlWithDomainName() throws InterruptedException, IOException, URISyntaxException {
        mailSender.from("rosin1408@gmail.com").to("rosin1408@gmail.com").cc("roberto.souza@gmail.com").subject("assunto").html("texto do email!@#$%*()").send();

        verify(httpRequestBuilder, only()).build(any(URI.class), anyString(), anyString());
    }
}
