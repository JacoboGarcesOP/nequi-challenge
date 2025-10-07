package co.com.bancolombia.usecase.branch;

import co.com.bancolombia.model.branch.Branch;
import co.com.bancolombia.model.branch.gateway.BranchGateway;
import co.com.bancolombia.usecase.exception.BussinessException;
import co.com.bancolombia.usecase.branch.dto.BranchDto;
import reactor.core.publisher.Mono;

public class UpdateBranchUseCase {
  private final BranchGateway gateway;
  private static final String BRANCH_NOT_FOUND_MESSAGE = "The branch id has not been found.";

  public UpdateBranchUseCase(BranchGateway gateway) {
    this.gateway = gateway;
  }

  public Mono<BranchDto> execute(Long id, String name) {
    return gateway.findById(id)
      .switchIfEmpty(Mono.error(new BussinessException(BRANCH_NOT_FOUND_MESSAGE)))
      .flatMap(existing -> gateway.update(new Branch(id, existing.getFranchiseId().getValue(), name)))
      .map(br -> new BranchDto(br.getId().getValue(), br.getFranchiseId().getValue(), br.getName().getValue()));
  }
}


