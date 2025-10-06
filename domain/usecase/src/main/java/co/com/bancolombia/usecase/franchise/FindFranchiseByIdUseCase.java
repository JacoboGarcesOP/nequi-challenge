package co.com.bancolombia.usecase.franchise;

import co.com.bancolombia.model.franchise.Franchise;
import co.com.bancolombia.model.franchise.gateway.FranchiseGateway;
import reactor.core.publisher.Mono;

public class FindFranchiseByIdUseCase {
  private final FranchiseGateway gateway;

  public FindFranchiseByIdUseCase(FranchiseGateway gateway) {
    this.gateway = gateway;
  }

  public Mono<Franchise> execute(Long id) {
    return gateway.findById(id);
  }
}


