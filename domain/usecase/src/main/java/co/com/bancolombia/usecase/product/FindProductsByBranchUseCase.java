package co.com.bancolombia.usecase.product;

import co.com.bancolombia.model.product.gateway.ProductGateway;
import co.com.bancolombia.usecase.product.dto.ProductDto;
import reactor.core.publisher.Flux;

public class FindProductsByBranchUseCase {
  private final ProductGateway gateway;

  public FindProductsByBranchUseCase(ProductGateway gateway) {
    this.gateway = gateway;
  }

  public Flux<ProductDto> execute(Long branchId) {
    return gateway.findByBranchId(branchId)
      .map(p -> new ProductDto(p.getId().getValue(), p.getBranchId().getValue(), p.getName().getValue(), p.getStock().getValue()));
  }
}


