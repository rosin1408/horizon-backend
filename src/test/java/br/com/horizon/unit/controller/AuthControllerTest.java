package br.com.horizon.unit.controller;

import static org.assertj.core.api.Assertions.assertThat;

import br.com.horizon.controller.AuthController;
import br.com.horizon.dto.ApiResponse;
import br.com.horizon.service.service.AuthService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
public class AuthControllerTest {

    @Mock
    private AuthService authService;
    @InjectMocks
    private AuthController controller;

    @Test
    public void shouldValidateEmailWhenValidToken() {
        Mockito.when(authService.confirmEmail("valid_token")).thenReturn(true);
        ResponseEntity<ApiResponse> response = controller.confirmEmail("valid_token");

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isInstanceOf(ApiResponse.class);
        assertThat(response.getBody().getSuccess()).isTrue();
        assertThat(response.getBody().getMessage()).isEqualTo("Email confirmado");
    }

    @Test
    public void shouldNotValidateEmailWhenInvalidToken() {
        Mockito.when(authService.confirmEmail("invalid_token")).thenReturn(false);
        ResponseEntity<ApiResponse> response = controller.confirmEmail("invalid_token");

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isInstanceOf(ApiResponse.class);
        assertThat(response.getBody().getSuccess()).isFalse();
        assertThat(response.getBody().getMessage()).isEqualTo("Email confirmado");
    }
}
