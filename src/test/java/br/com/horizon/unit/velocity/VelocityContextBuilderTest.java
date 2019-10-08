package br.com.horizon.unit.velocity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import br.com.horizon.velocity.VelocityContextBuilder;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.apache.velocity.VelocityContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class VelocityContextBuilderTest {

    private VelocityContextBuilder builder;

    @BeforeEach
    public void setup() {
        this.builder = new VelocityContextBuilder();
    }

    @Test
    public void shouldBuildTemplateWithParams() {
        builder.build();
        VelocityContext context = builder.params(new HashMap<>()).build();

        assertThat(context).isNotNull();

    }

    @Test
    public void shouldSetParamsToContext() {
        Map<String, Object> params = Map.of("param1", "value_param_1", "param2", 234L, "param3", Collections.emptyList());
        VelocityContext context = builder.params(params).build();

        assertThat(context.get("param1")).isEqualTo("value_param_1");
        assertThat(context.get("param2")).isEqualTo(234L);
        assertThat(context.get("param3")).isEqualTo(Collections.emptyList());
    }

    @Test
    public void shouldNotBuildContextWithNoParams() {
        assertThatThrownBy(() -> builder.build()).hasCauseInstanceOf(NullPointerException.class);
    }
}
