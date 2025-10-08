package co.com.bancolombia.usecase.franchise;

import co.com.bancolombia.model.franchise.Franchise;
import co.com.bancolombia.model.franchise.gateway.FranchiseGateway;
import co.com.bancolombia.usecase.exception.BussinessException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.*;

class CreateFranchiseUseCaseTest {

  private FranchiseGateway gateway;
  private CreateFranchiseUseCase useCase;

  @BeforeEach
  void setUp() {
    gateway = mock(FranchiseGateway.class);
    useCase = new CreateFranchiseUseCase(gateway);
  }

  @Test
  void shouldCreateFranchiseSuccessfully() {
    when(gateway.existsByName("FastFood")).thenReturn(Mono.just(false));
    when(gateway.save(any(Franchise.class))).thenReturn(Mono.just(new Franchise(1L, "FastFood")));

    StepVerifier.create(useCase.execute("FastFood"))
      .expectNextMatches(fr -> fr.getId().equals(1L) && fr.getName().equals("FastFood"))
      .verifyComplete();
  }

  @Test
  void shouldFailWhenDuplicatedName() {
    when(gateway.existsByName("FastFood")).thenReturn(Mono.just(true));

    StepVerifier.create(useCase.execute("FastFood"))
      .expectError(BussinessException.class)
      .verify();

    verify(gateway, never()).save(any());
  }

  @Test
  void shouldPropagateGatewayErrors() {
    when(gateway.existsByName("X")).thenReturn(Mono.error(new RuntimeException("db error")));

    StepVerifier.create(useCase.execute("X"))
      .expectErrorMatches(t -> t instanceof RuntimeException && t.getMessage().equals("db error"))
      .verify();
  }
}


