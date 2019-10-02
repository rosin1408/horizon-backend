package br.com.horizon.email;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpResponse.BodyHandlers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MailSender {

    private static final String API_KEY = "de8c97cb282da488abee3c5413c3299a-baa55c84-9ef0a34c";
    private static final String DOMAIN_NAME = "sandbox15d801020ea5484e931a6cfa1f86a4c5.mailgun.org";

    @Autowired
    private EmailBodyBuilder bodyBuilder;
    @Autowired
    private HttpRequestEmailBuilder httpRequestBuilder;
    @Autowired
    private HttpClientEmailBuilder httpClientBuilder;
    @Autowired
    private UriEmailBuilder uriBuilder;

    public boolean send() throws URISyntaxException, IOException, InterruptedException {
        HttpClient client = httpClientBuilder.build();

        var uri = uriBuilder.build(DOMAIN_NAME);

        var request = httpRequestBuilder.build(uri, bodyBuilder.build(), API_KEY);

        var response = client.send(request, BodyHandlers.ofString());

        return response.statusCode() == 200;
    }

    public MailSender from(String from) {
        this.bodyBuilder.from(from);
        return this;
    }

    public MailSender to(String to) {
        this.bodyBuilder.to(to);
        return this;
    }

    public MailSender cc(String cc) {
        this.bodyBuilder.cc(cc);
        return this;
    }

    public MailSender subject(String subject) {
        this.bodyBuilder.subject(subject);
        return this;
    }

    public MailSender text(String text) {
        this.bodyBuilder.text(text);
        return this;
    }

    public MailSender html(String html) {
        this.bodyBuilder.html(html);
        return this;
    }
}
