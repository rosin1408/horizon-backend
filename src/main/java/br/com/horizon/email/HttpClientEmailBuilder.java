package br.com.horizon.email;

import java.net.http.HttpClient;
import java.time.Duration;
import org.springframework.stereotype.Component;

@Component
public class HttpClientEmailBuilder {

    public HttpClient build() {
        return HttpClient.newBuilder().connectTimeout(Duration.ofMillis(15000)).build();
    }
}
