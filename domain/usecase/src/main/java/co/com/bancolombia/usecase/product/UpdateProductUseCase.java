package co.com.bancolombia.usecase.product;

import co.com.bancolombia.model.product.Product;
import co.com.bancolombia.model.product.gateway.ProductGateway;
import co.com.bancolombia.usecase.exception.BussinessException;
import co.com.bancolombia.usecase.product.dto.ProductDto;
import reactor.core.publisher.Mono;

public class UpdateProductUseCase {
  private final ProductGateway gateway;
  private static final String PRODUCT_NOT_FOUND_MESSAGE = "The product id has not been found.";

  public UpdateProductUseCase(ProductGateway gateway) {
    this.gateway = gateway;
  }

  public Mono<ProductDto> execute(Long id, String name, Integer stock) {
    return gateway.findById(id)
      .switchIfEmpty(Mono.error(new BussinessException(PRODUCT_NOT_FOUND_MESSAGE)))
      .flatMap(existing -> gateway.update(new Product(id, existing.getBranchId().getValue(), name != null ? name : existing.getName().getValue(), stock)))
      .map(p -> new ProductDto(p.getId().getValue(), p.getBranchId().getValue(), p.getName().getValue(), p.getStock().getValue()));
  }
}


