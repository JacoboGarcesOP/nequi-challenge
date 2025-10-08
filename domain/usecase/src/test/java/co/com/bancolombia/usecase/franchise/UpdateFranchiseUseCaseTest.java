package co.com.bancolombia.usecase.franchise;

import co.com.bancolombia.model.franchise.Franchise;
import co.com.bancolombia.model.franchise.gateway.FranchiseGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.*;

class UpdateFranchiseUseCaseTest {

  private FranchiseGateway gateway;
  private UpdateFranchiseUseCase useCase;

  @BeforeEach
  void setUp() {
    gateway = mock(FranchiseGateway.class);
    useCase = new UpdateFranchiseUseCase(gateway);
  }

  @Test
  void shouldUpdateFranchise() {
    when(gateway.existsById(1L)).thenReturn(Mono.just(true));
    when(gateway.existsByName("X")).thenReturn(Mono.just(false));
    when(gateway.update(any(Franchise.class))).thenReturn(Mono.just(new Franchise(1L, "X")));

    StepVerifier.create(useCase.execute(1L, "X"))
      .expectNextMatches(fr -> fr.getId().equals(1L) && fr.getName().equals("X"))
      .verifyComplete();
  }

  @Test
  void shouldPropagateErrors() {
    when(gateway.existsById(1L)).thenReturn(Mono.just(true));
    when(gateway.existsByName("X")).thenReturn(Mono.error(new RuntimeException("db")));

    StepVerifier.create(useCase.execute(1L, "X"))
      .expectErrorMatches(t -> t instanceof RuntimeException && t.getMessage().equals("db"))
      .verify();
  }

  @Test
  void shouldFailWhenIdNotFound() {
    when(gateway.existsById(2L)).thenReturn(Mono.just(false));

    StepVerifier.create(useCase.execute(2L, "X"))
      .expectErrorMatches(t -> t.getClass().getSimpleName().equals("BussinessException"))
      .verify();
  }

  @Test
  void shouldFailWhenNameDuplicated() {
    when(gateway.existsById(1L)).thenReturn(Mono.just(true));
    when(gateway.existsByName("X")).thenReturn(Mono.just(true));

    StepVerifier.create(useCase.execute(1L, "X"))
      .expectErrorMatches(t -> t.getClass().getSimpleName().equals("BussinessException"))
      .verify();
  }
}


