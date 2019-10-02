package br.com.horizon.mail;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import br.com.horizon.email.EmailBodyBuilder;
import br.com.horizon.exception.AppException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class EmailBodyBuilderTest {


    private EmailBodyBuilder bodyBuilder;

    @BeforeEach
    public void setup() {
        this.bodyBuilder = new EmailBodyBuilder();
    }

    @Test
    public void shouldBuildBodyEncodingValues() {
        String body = bodyBuilder.from("rosin1408@gmail.com").to("rosin1408@gmail.com").subject("assunto").text("texto do email").build();

        assertEquals("from=rosin1408%40gmail.com&to=rosin1408%40gmail.com&subject=assunto&text=texto+do+email", body);
    }

    @Test
    public void shouldBuildBodyWithHtml() {
        String body = bodyBuilder.from("rosin1408@gmail.com").to("rosin1408@gmail.com").subject("assunto").html("texto do email").build();

        assertEquals("from=rosin1408%40gmail.com&to=rosin1408%40gmail.com&subject=assunto&html=texto+do+email", body);
    }

    @Test
    public void shouldThrowsExceptionWhenFromIsEmpty() {
        AppException exception = assertThrows(AppException.class, () -> bodyBuilder.build());

        assertEquals("Parâmetro 'from' é obrigatório", exception.getMessage());
    }

    @Test
    public void shouldThrowsExceptionWhenToIsEmpty() {
        bodyBuilder.from("rosin1408@gmail.com");
        AppException exception = assertThrows(AppException.class, () -> bodyBuilder.build());

        assertEquals("Parâmetro 'to' é obrigatório", exception.getMessage());
    }

    @Test
    public void shouldThrowsExceptionWhenTextAndHtmlIsEmpty() {
        bodyBuilder.from("rosin1408@gmail.com").to("rosin1408@gmail.com").subject("assunto");

        AppException exception = assertThrows(AppException.class, () -> bodyBuilder.build());

        assertEquals("É obrigatório informar o texto ou o html do email", exception.getMessage());
    }
}
