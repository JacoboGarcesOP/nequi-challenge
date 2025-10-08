package co.com.bancolombia.usecase.franchise;

import co.com.bancolombia.model.franchise.gateway.FranchiseGateway;
import co.com.bancolombia.usecase.exception.BussinessException;
import reactor.core.publisher.Mono;

public class DeleteFranchiseUseCase {
  private final FranchiseGateway gateway;
  private static final String FRANCHISE_NOT_FOUND_MESSAGE = "The franchise id has not been found.";

  public DeleteFranchiseUseCase(FranchiseGateway gateway) {
    this.gateway = gateway;
  }

  public Mono<Boolean> execute(Long id) {
    return gateway.existsById(id)
      .flatMap(exists -> Boolean.TRUE.equals(exists)
        ? gateway.deleteById(id).thenReturn(Boolean.TRUE)
        : Mono.error(new BussinessException(FRANCHISE_NOT_FOUND_MESSAGE))
      );
  }
}


