package co.com.bancolombia.api;

import co.com.bancolombia.api.request.CreateFranchiseRequest;
import co.com.bancolombia.api.request.UpdateFranchiseRequest;
import co.com.bancolombia.usecase.exception.BussinessException;
import co.com.bancolombia.usecase.franchise.CreateFranchiseUseCase;
import co.com.bancolombia.usecase.franchise.DeleteFranchiseUseCase;
import co.com.bancolombia.usecase.franchise.FindAllFranchisesUseCase;
import co.com.bancolombia.usecase.franchise.UpdateFranchiseUseCase;
import co.com.bancolombia.usecase.franchise.dto.FranchiseDto;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RouterRestTest {

  @Mock private CreateFranchiseUseCase createFranchiseUseCase;
  @Mock private FindAllFranchisesUseCase findAllFranchisesUseCase;
  @Mock private UpdateFranchiseUseCase updateFranchiseUseCase;
  @Mock private DeleteFranchiseUseCase deleteFranchiseUseCase;

  @Mock private Validator validator;

  @InjectMocks
  private Handler handler;

  private WebTestClient webTestClient;

  @BeforeEach
  void setUp() {
    RouterRest routerRest = new RouterRest();
    RouterFunction<ServerResponse> routerFunction = (RouterFunction<ServerResponse>) routerRest.createFranchiseRouter(handler)
      .andOther(routerRest.findAllFranchisesRouter(handler))
      .andOther(routerRest.updateFranchiseRouter(handler))
      .andOther(routerRest.deleteFranchiseRouter(handler));

    webTestClient = WebTestClient.bindToRouterFunction(routerFunction).build();
  }

  // Franchise - success paths
  @Test
  @DisplayName("Should create franchise successfully")
  void shouldCreateFranchiseSuccessfully() {
    when(validator.validate(any(CreateFranchiseRequest.class))).thenReturn(Collections.emptySet());
    when(createFranchiseUseCase.execute(anyString())).thenReturn(Mono.just(new FranchiseDto(1L, "FastFood")));

    webTestClient.post().uri("/v1/api/franchise")
      .body(BodyInserters.fromValue(new CreateFranchiseRequest("FastFood")))
      .exchange()
      .expectStatus().isOk()
      .expectHeader().contentType(MediaType.APPLICATION_JSON)
      .expectBody()
      .jsonPath("$.id").isEqualTo("1")
      .jsonPath("$.name").isEqualTo("FastFood");
  }

  @Test
  @DisplayName("Should list franchises successfully")
  void shouldListFranchisesSuccessfully() {
    when(findAllFranchisesUseCase.execute()).thenReturn(Flux.just(new FranchiseDto(1L, "A"), new FranchiseDto(2L, "B")));

    webTestClient.get().uri("/v1/api/franchise")
      .exchange()
      .expectStatus().isOk()
      .expectHeader().contentType(MediaType.APPLICATION_JSON)
      .expectBody()
      .jsonPath("$[0].id").isEqualTo("1")
      .jsonPath("$[0].name").isEqualTo("A")
      .jsonPath("$[1].id").isEqualTo("2")
      .jsonPath("$[1].name").isEqualTo("B");
  }

  @Test
  @DisplayName("Should update franchise successfully")
  void shouldUpdateFranchiseSuccessfully() {
    when(validator.validate(any(UpdateFranchiseRequest.class))).thenReturn(Collections.emptySet());
    when(updateFranchiseUseCase.execute(eq(1L), anyString())).thenReturn(Mono.just(new FranchiseDto(1L, "X")));

    webTestClient.put().uri("/v1/api/franchise")
      .body(BodyInserters.fromValue(new UpdateFranchiseRequest(1L, "X")))
      .exchange()
      .expectStatus().isOk()
      .expectBody()
      .jsonPath("$.id").isEqualTo("1")
      .jsonPath("$.name").isEqualTo("X");
  }

  @Test
  @DisplayName("Should delete franchise successfully")
  void shouldDeleteFranchiseSuccessfully() {
    when(deleteFranchiseUseCase.execute(1L)).thenReturn(Mono.just(Boolean.TRUE));

    webTestClient.delete().uri("/v1/api/franchise/{id}", 1)
      .exchange()
      .expectStatus().isNoContent();
  }

  // Franchise - validation and error handling
  @Test
  @DisplayName("Should validate create franchise request")
  void shouldValidateCreateFranchiseRequest() {
    Set<ConstraintViolation<CreateFranchiseRequest>> violations = new HashSet<>();
    ConstraintViolation<CreateFranchiseRequest> violation = mock(ConstraintViolation.class);
    when(violation.getMessage()).thenReturn("Name cannot be empty");
    violations.add(violation);
    when(validator.validate(any(CreateFranchiseRequest.class))).thenReturn(violations);

    webTestClient.post().uri("/v1/api/franchise")
      .body(BodyInserters.fromValue(new CreateFranchiseRequest("")))
      .exchange()
      .expectStatus().isBadRequest()
      .expectBody()
      .jsonPath("$.error").isEqualTo("VALIDATION_ERROR")
      .jsonPath("$.message").isEqualTo("Name cannot be empty");
  }

  @Test
  @DisplayName("Should handle business error on create franchise")
  void shouldHandleBusinessErrorOnCreateFranchise() {
    when(validator.validate(any(CreateFranchiseRequest.class))).thenReturn(Collections.emptySet());
    when(createFranchiseUseCase.execute(anyString())).thenReturn(Mono.error(new BussinessException("Duplicated")));

    webTestClient.post().uri("/v1/api/franchise")
      .body(BodyInserters.fromValue(new CreateFranchiseRequest("FastFood")))
      .exchange()
      .expectStatus().isBadRequest()
      .expectBody()
      .jsonPath("$.error").isEqualTo("BUSINESS_ERROR")
      .jsonPath("$.message").isEqualTo("Duplicated");
  }

  @Test
  @DisplayName("Should validate update franchise request")
  void shouldValidateUpdateFranchiseRequest() {
    Set<ConstraintViolation<UpdateFranchiseRequest>> violations = new HashSet<>();
    ConstraintViolation<UpdateFranchiseRequest> violation = mock(ConstraintViolation.class);
    when(violation.getMessage()).thenReturn("Id cannot be null");
    violations.add(violation);
    when(validator.validate(any(UpdateFranchiseRequest.class))).thenReturn(violations);

    webTestClient.put().uri("/v1/api/franchise")
      .body(BodyInserters.fromValue(new UpdateFranchiseRequest(null, "X")))
      .exchange()
      .expectStatus().isBadRequest()
      .expectBody()
      .jsonPath("$.error").isEqualTo("VALIDATION_ERROR")
      .jsonPath("$.message").isEqualTo("Id cannot be null");
  }

  @Test
  @DisplayName("Should handle internal error on update franchise")
  void shouldHandleInternalErrorOnUpdateFranchise() {
    when(validator.validate(any(UpdateFranchiseRequest.class))).thenReturn(Collections.emptySet());
    when(updateFranchiseUseCase.execute(eq(1L), anyString())).thenReturn(Mono.error(new RuntimeException("DB fail")));

    webTestClient.put().uri("/v1/api/franchise")
      .body(BodyInserters.fromValue(new UpdateFranchiseRequest(1L, "X")))
      .exchange()
      .expectStatus().is5xxServerError()
      .expectBody()
      .jsonPath("$.error").isEqualTo("INTERNAL_ERROR");
  }

  // Technology routes are tested separately; this suite focuses on Franchise routes.
}


