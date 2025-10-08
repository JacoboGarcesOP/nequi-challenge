package co.com.bancolombia.usecase.franchise;

import co.com.bancolombia.model.franchise.gateway.FranchiseGateway;
import co.com.bancolombia.usecase.franchise.dto.FranchiseDto;
import co.com.bancolombia.usecase.exception.BussinessException;
import reactor.core.publisher.Mono;

public class UpdateFranchiseUseCase {
  private final FranchiseGateway gateway;
  private static final String FRANCHISE_DUPLICATED_MESSAGE = "The franchise name cannot be duplicated.";
  private static final String FRANCHISE_NOT_FOUND_MESSAGE = "The franchise id has not been found.";

  public UpdateFranchiseUseCase(FranchiseGateway gateway) {
    this.gateway = gateway;
  }

  public Mono<FranchiseDto> execute(Long id, String name) {
    return gateway.existsById(id)
      .flatMap(exists -> Boolean.TRUE.equals(exists)
        ? gateway.existsByName(name)
          .flatMap(nameExists -> Boolean.TRUE.equals(nameExists)
            ? Mono.error(new BussinessException(FRANCHISE_DUPLICATED_MESSAGE))
            : gateway.update(new co.com.bancolombia.model.franchise.Franchise(id, name)))
        : Mono.error(new BussinessException(FRANCHISE_NOT_FOUND_MESSAGE))
      ).map(fr -> new FranchiseDto(fr.getId().getValue(), fr.getName().getValue()));
  }
}


