package co.com.bancolombia.usecase.franchise;

import co.com.bancolombia.model.franchise.Franchise;
import co.com.bancolombia.model.franchise.gateway.FranchiseGateway;
import co.com.bancolombia.usecase.exception.BussinessException;
import reactor.core.publisher.Mono;

public class CreateFranchiseUseCase {
  private final String FRANCHISE_DUPLICATED_MESSAGE = "The franchise name cannot be duplicated.";
  private final FranchiseGateway gateway;

  public CreateFranchiseUseCase(FranchiseGateway gateway) {
    this.gateway = gateway;
  }

  public Mono<Franchise> execute(String name) {
    return gateway.existsByName(name)
      .flatMap(exists -> Boolean.TRUE.equals(exists)
        ? Mono.error(new BussinessException(FRANCHISE_DUPLICATED_MESSAGE))
        : gateway.save(new Franchise(name)));
  }
}


