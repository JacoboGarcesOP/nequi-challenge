package co.com.bancolombia.usecase.product;

import co.com.bancolombia.model.product.gateway.ProductGateway;
import co.com.bancolombia.usecase.exception.BussinessException;
import reactor.core.publisher.Mono;

public class DeleteProductUseCase {
  private final ProductGateway gateway;

  public DeleteProductUseCase(ProductGateway gateway) {
    this.gateway = gateway;
  }

  public Mono<Boolean> execute(Long id) {
    return gateway.existsById(id)
      .flatMap(exists -> Boolean.TRUE.equals(exists)
        ? gateway.deleteById(id).thenReturn(Boolean.TRUE)
        : Mono.error(new BussinessException("The product id has not been found."))
      );
  }
}


