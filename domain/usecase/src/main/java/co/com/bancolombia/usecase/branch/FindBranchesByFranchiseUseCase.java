package co.com.bancolombia.usecase.branch;

import co.com.bancolombia.model.branch.gateway.BranchGateway;
import co.com.bancolombia.usecase.branch.dto.BranchDto;
import reactor.core.publisher.Flux;

public class FindBranchesByFranchiseUseCase {
  private final BranchGateway gateway;

  public FindBranchesByFranchiseUseCase(BranchGateway gateway) {
    this.gateway = gateway;
  }

  public Flux<BranchDto> execute(Long franchiseId) {
    return gateway.findByFranchiseId(franchiseId)
      .map(br -> new BranchDto(br.getId().getValue(), br.getFranchiseId().getValue(), br.getName().getValue()));
  }
}


