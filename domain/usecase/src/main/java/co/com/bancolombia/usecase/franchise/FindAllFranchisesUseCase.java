package co.com.bancolombia.usecase.franchise;

import co.com.bancolombia.model.franchise.gateway.FranchiseGateway;
import co.com.bancolombia.usecase.franchise.dto.FranchiseDto;
import reactor.core.publisher.Flux;

public class FindAllFranchisesUseCase {
  private final FranchiseGateway gateway;

  public FindAllFranchisesUseCase(FranchiseGateway gateway) {
    this.gateway = gateway;
  }

  public Flux<FranchiseDto> execute() {
    return gateway.findAll().map(fr -> new FranchiseDto(fr.getId().getValue(), fr.getName().getValue()));
  }
}


