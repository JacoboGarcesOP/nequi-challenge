package co.com.bancolombia.model.product.gateway;

import co.com.bancolombia.model.product.Product;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductGateway {
  Mono<Product> save(Product product);
  Flux<Product> findByBranchId(Long branchId);
  Mono<Product> findById(Long id);
  Mono<Boolean> existsById(Long id);
  Mono<Product> update(Product product);
  Mono<Void> deleteById(Long id);
}


