package br.com.horizon.email;

import br.com.horizon.exception.AppException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import javax.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class EmailBodyBuilder {

    private String from;
    private String to;
    private String cc;
    private String subject;
    private String text;
    private String html;

    @Autowired
    public EmailBodyBuilder() {
        this.from = "";
        this.to = "";
        this.cc = "";
        this.subject = "";
        this.text = "";
        this.html = "";
    }

    public String build() {
        validate();

        var body = new StringBuilder();

        body.append(from).append(to).append(cc).append(subject).append(text).append(html);

        return body.toString();
    }

    public EmailBodyBuilder from(@NotBlank String from) {
        this.from = "from=" + encode(from);
        return this;
    }

    public EmailBodyBuilder to(@NotBlank String to) {
        this.to = "&to=" + encode(to);
        return this;
    }

    public EmailBodyBuilder cc(@NotBlank String cc) {
        this.cc = "&cc=" + encode(cc);
        return this;
    }

    public EmailBodyBuilder subject(@NotBlank String subject) {
        this.subject = "&subject=" + encode(subject);
        return this;
    }

    public EmailBodyBuilder text(@NotBlank String text) {
        this.text = "&text=" + encode(text);
        return this;
    }

    public EmailBodyBuilder html(@NotBlank String html) {
        this.html = "&html=" + encode(html);
        return this;
    }

    private void validate() {
        if (StringUtils.isEmpty(this.from)) {
            throw new AppException("Parâmetro 'from' é obrigatório");
        }

        if (StringUtils.isEmpty(this.to)) {
            throw new AppException("Parâmetro 'to' é obrigatório");
        }

        if (StringUtils.isEmpty(this.text) && StringUtils.isEmpty(this.html)) {
            throw new AppException("É obrigatório informar o texto ou o html do email");
        }
    }

    private String encode(String text) {
        return URLEncoder.encode(text, StandardCharsets.UTF_8);
    }
}
