package br.com.horizon.velocity;

import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Objects;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class VelocityCompiler {

    @Autowired
    private VelocityEngineBuilder engineBuilder;
    @Autowired
    private VelocityEngine engine;
    @Autowired
    private VelocityContextBuilder contextBuilder;


    public String compile(String templateUrl, Map<String, Object> params) {
        validateParams(templateUrl, params);

        final VelocityContext context = contextBuilder.params(params).build();

        final Template t = engine.getTemplate("templates/" + templateUrl, "UTF-8");

        StringWriter writer = new StringWriter();
        t.merge(context, writer);

        return writer.toString();
    }

    private void validateParams(String templateUrl, Map<String, Object> params) {
        Objects.requireNonNull(templateUrl);
        if (templateUrl.isEmpty() || templateUrl.isBlank()) {
            throw new RuntimeException("Param templateUrl should not be empty");
        }
        Objects.requireNonNull(params);
    }
}
