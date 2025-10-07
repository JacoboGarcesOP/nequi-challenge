package co.com.bancolombia.r2dbc.adapter;

import co.com.bancolombia.model.product.Product;
import co.com.bancolombia.model.product.gateway.ProductGateway;
import co.com.bancolombia.r2dbc.entity.ProductEntity;
import co.com.bancolombia.r2dbc.repository.ProductRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public class ProductRepositoryAdapter implements ProductGateway {
  private final ProductRepository repository;

  public ProductRepositoryAdapter(ProductRepository repository) {
    this.repository = repository;
  }

  @Override
  public Mono<Product> save(Product product) {
    return repository.save(ProductEntity.builder()
        .branchId(product.getBranchId().getValue())
        .name(product.getName().getValue())
        .stock(product.getStock().getValue())
        .build())
      .map(entity -> new Product(entity.getId(), entity.getBranchId(), entity.getName(), entity.getStock()));
  }

  @Override
  public Flux<Product> findByBranchId(Long branchId) {
    return repository.findByBranchId(branchId)
      .map(entity -> new Product(entity.getId(), entity.getBranchId(), entity.getName(), entity.getStock()));
  }

  @Override
  public Mono<Product> findById(Long id) {
    return repository.findById(id)
      .map(entity -> new Product(entity.getId(), entity.getBranchId(), entity.getName(), entity.getStock()));
  }

  @Override
  public Mono<Boolean> existsById(Long id) {
    return repository.existsById(id);
  }

  @Override
  public Mono<Product> update(Product product) {
    return repository.save(ProductEntity.builder()
        .id(product.getId().getValue())
        .branchId(product.getBranchId().getValue())
        .name(product.getName().getValue())
        .stock(product.getStock().getValue())
        .build())
      .map(entity -> new Product(entity.getId(), entity.getBranchId(), entity.getName(), entity.getStock()));
  }

  @Override
  public Mono<Void> deleteById(Long id) {
    return repository.deleteById(id);
  }
}


