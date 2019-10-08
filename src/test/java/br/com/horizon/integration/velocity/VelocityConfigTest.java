package br.com.horizon.integration.velocity;

import static org.assertj.core.api.Assertions.assertThat;

import org.apache.velocity.app.VelocityEngine;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class VelocityConfigTest {

    @Autowired
    private VelocityEngine velocityEngine;

    @Test
    public void shouldHaveBeanToVelocityEngine() {
        assertThat(velocityEngine).isNotNull();
    }
}
