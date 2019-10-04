package br.com.horizon.unit.mail;

import static org.junit.jupiter.api.Assertions.assertEquals;

import br.com.horizon.email.HttpClientEmailBuilder;
import org.junit.jupiter.api.Test;

public class HttpClientEmailBuilderTest {

    @Test
    public void shouldBuildHttpClientWithDuration15000() {
        var httpClientEmailBuilder = new HttpClientEmailBuilder();

        var httpClient = httpClientEmailBuilder.build();

        assertEquals(15L, httpClient.connectTimeout().get().getSeconds());
    }
}
