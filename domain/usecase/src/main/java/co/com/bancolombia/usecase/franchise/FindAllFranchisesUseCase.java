package co.com.bancolombia.usecase.franchise;

import co.com.bancolombia.model.franchise.Franchise;
import co.com.bancolombia.model.franchise.gateway.FranchiseGateway;
import reactor.core.publisher.Flux;

public class FindAllFranchisesUseCase {
  private final FranchiseGateway gateway;

  public FindAllFranchisesUseCase(FranchiseGateway gateway) {
    this.gateway = gateway;
  }

  public Flux<Franchise> execute() {
    return gateway.findAll();
  }
}


