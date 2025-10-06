package co.com.bancolombia.usecase.franchise;

import co.com.bancolombia.model.franchise.Franchise;
import co.com.bancolombia.model.franchise.gateway.FranchiseGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.*;

class FindAllFranchisesUseCaseTest {

  private FranchiseGateway gateway;
  private FindAllFranchisesUseCase useCase;

  @BeforeEach
  void setUp() {
    gateway = mock(FranchiseGateway.class);
    useCase = new FindAllFranchisesUseCase(gateway);
  }

  @Test
  void shouldReturnAllFranchises() {
    when(gateway.findAll()).thenReturn(Flux.just(new Franchise(1L, "A"), new Franchise(2L, "B")));

    StepVerifier.create(useCase.execute())
      .expectNextCount(2)
      .verifyComplete();
  }

  @Test
  void shouldPropagateErrors() {
    when(gateway.findAll()).thenReturn(Flux.error(new RuntimeException("db")));

    StepVerifier.create(useCase.execute())
      .expectErrorMatches(t -> t instanceof RuntimeException && t.getMessage().equals("db"))
      .verify();
  }
}


