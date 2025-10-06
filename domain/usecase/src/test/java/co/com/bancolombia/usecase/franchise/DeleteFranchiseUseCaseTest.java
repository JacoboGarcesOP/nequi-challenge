package co.com.bancolombia.usecase.franchise;

import co.com.bancolombia.model.franchise.gateway.FranchiseGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.*;

class DeleteFranchiseUseCaseTest {

  private FranchiseGateway gateway;
  private DeleteFranchiseUseCase useCase;

  @BeforeEach
  void setUp() {
    gateway = mock(FranchiseGateway.class);
    useCase = new DeleteFranchiseUseCase(gateway);
  }

  @Test
  void shouldDeleteFranchise() {
    when(gateway.existsById(1L)).thenReturn(Mono.just(true));
    when(gateway.deleteById(1L)).thenReturn(Mono.empty());

    StepVerifier.create(useCase.execute(1L))
      .expectNext(Boolean.TRUE)
      .verifyComplete();
  }

  @Test
  void shouldPropagateErrors() {
    when(gateway.existsById(1L)).thenReturn(Mono.just(true));
    when(gateway.deleteById(1L)).thenReturn(Mono.error(new RuntimeException("db")));

    StepVerifier.create(useCase.execute(1L))
      .expectErrorMatches(t -> t instanceof RuntimeException && t.getMessage().equals("db"))
      .verify();
  }

  @Test
  void shouldFailWhenIdNotFound() {
    when(gateway.existsById(2L)).thenReturn(Mono.just(false));

    StepVerifier.create(useCase.execute(2L))
      .expectErrorMatches(t -> t.getClass().getSimpleName().equals("BussinessException"))
      .verify();
  }
}


