package co.com.bancolombia.usecase.franchise;

import co.com.bancolombia.model.franchise.Franchise;
import co.com.bancolombia.model.franchise.gateway.FranchiseGateway;
import co.com.bancolombia.usecase.exception.BussinessException;
import reactor.core.publisher.Mono;

public class UpdateFranchiseUseCase {
  private final FranchiseGateway gateway;

  public UpdateFranchiseUseCase(FranchiseGateway gateway) {
    this.gateway = gateway;
  }

  public Mono<Franchise> execute(Long id, String name) {
    return gateway.existsById(id)
      .flatMap(exists -> Boolean.TRUE.equals(exists)
        ? gateway.existsByName(name)
          .flatMap(nameExists -> Boolean.TRUE.equals(nameExists)
            ? Mono.error(new BussinessException("The franchise name cannot be duplicated."))
            : gateway.update(new Franchise(id, name)))
        : Mono.error(new BussinessException("The franchise id has not been found."))
      );
  }
}


