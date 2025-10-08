package co.com.bancolombia.usecase.branch;

import co.com.bancolombia.model.branch.Branch;
import co.com.bancolombia.model.branch.gateway.BranchGateway;
import co.com.bancolombia.model.franchise.gateway.FranchiseGateway;
import co.com.bancolombia.usecase.exception.BussinessException;
import co.com.bancolombia.usecase.branch.dto.BranchDto;
import reactor.core.publisher.Mono;

public class CreateBranchUseCase {
  private static final String FRANCHISE_NOT_FOUND_MESSAGE = "The franchise id has not been found.";
  private final BranchGateway gateway;
  private final FranchiseGateway franchiseGateway;

  public CreateBranchUseCase(BranchGateway gateway, FranchiseGateway franchiseGateway) {
    this.gateway = gateway;
    this.franchiseGateway = franchiseGateway;
  }

  public Mono<BranchDto> execute(Long franchiseId, String name) {
    return franchiseGateway.existsById(franchiseId)
      .flatMap(exists -> Boolean.TRUE.equals(exists)
        ? gateway.save(new Branch(franchiseId, name))
          .map(br -> new BranchDto(br.getId().getValue(), br.getFranchiseId().getValue(), br.getName().getValue()))
        : Mono.error(new BussinessException(FRANCHISE_NOT_FOUND_MESSAGE))
      );
  }
}


