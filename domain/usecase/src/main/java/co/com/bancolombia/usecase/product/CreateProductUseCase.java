package co.com.bancolombia.usecase.product;

import co.com.bancolombia.model.product.Product;
import co.com.bancolombia.model.product.gateway.ProductGateway;
import co.com.bancolombia.model.branch.gateway.BranchGateway;
import co.com.bancolombia.usecase.exception.BussinessException;
import co.com.bancolombia.usecase.product.dto.ProductDto;
import reactor.core.publisher.Mono;

public class CreateProductUseCase {
  private static final String BRANCH_NOT_FOUND_MESSAGE = "The branch id has not been found.";
  private final ProductGateway gateway;
  private final BranchGateway branchGateway;

  public CreateProductUseCase(ProductGateway gateway, BranchGateway branchGateway) {
    this.gateway = gateway;
    this.branchGateway = branchGateway;
  }

  public Mono<ProductDto> execute(Long branchId, String name, Integer stock) {
    return branchGateway.existsById(branchId)
      .flatMap(exists -> Boolean.TRUE.equals(exists)
        ? gateway.save(new Product(branchId, name, stock))
          .map(p -> new ProductDto(p.getId().getValue(), p.getBranchId().getValue(), p.getName().getValue(), p.getStock().getValue()))
        : Mono.error(new BussinessException(BRANCH_NOT_FOUND_MESSAGE))
      );
  }
}


