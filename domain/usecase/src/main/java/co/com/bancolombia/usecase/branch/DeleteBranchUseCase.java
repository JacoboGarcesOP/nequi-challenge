package co.com.bancolombia.usecase.branch;

import co.com.bancolombia.model.branch.gateway.BranchGateway;
import co.com.bancolombia.usecase.exception.BussinessException;
import reactor.core.publisher.Mono;

public class DeleteBranchUseCase {
  private final BranchGateway gateway;

  public DeleteBranchUseCase(BranchGateway gateway) {
    this.gateway = gateway;
  }

  public Mono<Boolean> execute(Long id) {
    return gateway.existsById(id)
      .flatMap(exists -> Boolean.TRUE.equals(exists)
        ? gateway.deleteById(id).thenReturn(Boolean.TRUE)
        : Mono.error(new BussinessException("The branch id has not been found."))
      );
  }
}


