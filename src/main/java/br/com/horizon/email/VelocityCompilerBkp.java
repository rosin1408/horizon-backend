package br.com.horizon.email;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;
import lombok.Setter;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;
import org.springframework.stereotype.Component;

@Component
@Setter
public class VelocityCompilerBkp {

    private String template;
    private Map<String, Object> params;

    public String compile() {
        VelocityEngine velocityEngine = new VelocityEngine();
        velocityEngine.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
        velocityEngine.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
        velocityEngine.init();
        // templates/email_confirm.vm

        Template t = velocityEngine.getTemplate("templates/email/query.vm");

        VelocityContext context = new VelocityContext();
        context.put("name", "Roberto Rosin");
        context.put("token", "this_is_my_fucking_token");

        StringWriter writer = new StringWriter();
        t.merge( context, writer );

        return writer.toString();
    }

    public static void main(String[] args) {
        var startTime = System.currentTimeMillis();

        var compiler = new VelocityCompilerBkp();
        compiler.setTemplate("email_confirm");
        compiler.setParams(new HashMap<>());
        var query = compiler.compile();

        var endTime = System.currentTimeMillis();

        var duration = (endTime - startTime);

        System.out.println(duration);
        System.out.println(query);
    }
}
