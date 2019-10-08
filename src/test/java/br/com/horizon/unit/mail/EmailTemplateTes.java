package br.com.horizon.unit.mail;

import br.com.horizon.email.VelocityCompilerBkp;
import java.io.FileNotFoundException;
import org.junit.jupiter.api.Test;

public class EmailTemplateTes {

    private VelocityCompilerBkp velocityCompiler;

    @Test
    public void shouldCompileTemplate() throws FileNotFoundException {
        velocityCompiler = new VelocityCompilerBkp();
        String template = velocityCompiler.compile();

        System.out.println(template);
    }
}
