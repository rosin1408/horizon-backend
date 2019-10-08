package br.com.horizon.velocity;

import java.util.Map;
import java.util.Objects;
import org.apache.velocity.VelocityContext;
import org.springframework.stereotype.Component;

@Component
public class VelocityContextBuilder {

    private Map<String, Object> params;

    public VelocityContextBuilder params(Map<String, Object> params) {
        this.params = params;
        return this;
    }

    public VelocityContext build() {
        return new VelocityContext(Objects.requireNonNull(params));
    }

}
