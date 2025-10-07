package co.com.bancolombia.usecase.franchise;

import co.com.bancolombia.model.branch.Branch;
import co.com.bancolombia.model.branch.gateway.BranchGateway;
import co.com.bancolombia.model.franchise.Franchise;
import co.com.bancolombia.model.franchise.gateway.FranchiseGateway;
import co.com.bancolombia.model.product.Product;
import co.com.bancolombia.model.product.gateway.ProductGateway;
import co.com.bancolombia.usecase.exception.BussinessException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import co.com.bancolombia.usecase.franchise.dto.FranchiseWithBranchesResponse;

import java.util.List;

public class GetFranchiseWithBranchesTopProductUseCase {
  private static final String FRANCHISE_NOT_FOUND_MESSAGE = "The franchise id has not been found.";

  private final FranchiseGateway franchiseGateway;
  private final BranchGateway branchGateway;
  private final ProductGateway productGateway;

  public GetFranchiseWithBranchesTopProductUseCase(
    FranchiseGateway franchiseGateway,
    BranchGateway branchGateway,
    ProductGateway productGateway
  ) {
    this.franchiseGateway = franchiseGateway;
    this.branchGateway = branchGateway;
    this.productGateway = productGateway;
  }

  public Mono<FranchiseWithBranchesResponse> execute(Long franchiseId) {
    return franchiseGateway.findById(franchiseId)
      .switchIfEmpty(Mono.error(new BussinessException(FRANCHISE_NOT_FOUND_MESSAGE)))
      .flatMap(franchise -> branchGateway.findByFranchiseId(franchiseId)
        .flatMap(this::toBranchWithTopProduct)
        .collectList()
        .map(branches -> new FranchiseWithBranchesResponse(
          franchise.getId().getValue(),
          franchise.getName().getValue(),
          branches
        ))
      );
  }

  private Mono<FranchiseWithBranchesResponse.BranchWithTopProductResponse> toBranchWithTopProduct(Branch branch) {
    return productGateway.findByBranchId(branch.getId().getValue())
      .reduce((p1, p2) -> p1.getStock().getValue() >= p2.getStock().getValue() ? p1 : p2)
      .map(top -> new FranchiseWithBranchesResponse.BranchWithTopProductResponse(
        branch.getId().getValue(),
        branch.getName().getValue(),
        top != null ? new FranchiseWithBranchesResponse.ProductResponse(
          top.getId().getValue(),
          top.getBranchId().getValue(),
          top.getName().getValue(),
          top.getStock().getValue()
        ) : null
      ))
      .defaultIfEmpty(new FranchiseWithBranchesResponse.BranchWithTopProductResponse(branch.getId().getValue(), branch.getName().getValue(), null));
  }
}


