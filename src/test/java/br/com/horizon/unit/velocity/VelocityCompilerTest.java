package br.com.horizon.unit.velocity;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import br.com.horizon.velocity.VelocityCompiler;
import br.com.horizon.velocity.VelocityContextBuilder;
import java.util.HashMap;
import org.apache.velocity.app.VelocityEngine;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class VelocityCompilerTest {

    @Mock
    private VelocityEngine engine;
    @Mock
    private VelocityContextBuilder contextBuilder;
    @InjectMocks
    private VelocityCompiler compiler;

    @Test
    public void shouldThrowsExceptionWhenParamTemplateIsNull() {
        assertThatThrownBy(() -> compiler.compile(null, new HashMap<>()));
    }

    @Test
    public void shouldThrowsExceptionWhenParamTemplateIsEmpty() {
        assertThatThrownBy(() -> compiler.compile("", new HashMap<>()));
    }

    @Test
    public void shouldThrowsExceptionWhenParamParamsIsNull() {
        assertThatThrownBy(() -> compiler.compile("", null));
    }

    @Test
    public void shouldCompileTemplate() {
        String compiledTemplate = compiler.compile("", new HashMap<>());
    }
}
