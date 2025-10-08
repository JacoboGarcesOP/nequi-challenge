package co.com.bancolombia.r2dbc.adapter;

import co.com.bancolombia.model.product.Product;
import co.com.bancolombia.model.product.gateway.ProductGateway;
import co.com.bancolombia.r2dbc.entity.ProductEntity;
import co.com.bancolombia.r2dbc.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
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
      .doOnSubscribe(s -> log.info("Saving product branchId={} name={} stock={}", product.getBranchId().getValue(), product.getName().getValue(), product.getStock().getValue()))
      .map(entity -> new Product(entity.getId(), entity.getBranchId(), entity.getName(), entity.getStock()));
  }

  @Override
  public Flux<Product> findByBranchId(Long branchId) {
    return repository.findByBranchId(branchId)
      .doOnSubscribe(s -> log.info("Finding products by branchId={}", branchId))
      .map(entity -> new Product(entity.getId(), entity.getBranchId(), entity.getName(), entity.getStock()));
  }

  @Override
  public Mono<Product> findById(Long id) {
    return repository.findById(id)
      .doOnSubscribe(s -> log.info("Finding product by id={}", id))
      .map(entity -> new Product(entity.getId(), entity.getBranchId(), entity.getName(), entity.getStock()));
  }

  @Override
  public Mono<Boolean> existsById(Long id) {
    return repository.existsById(id)
      .doOnSubscribe(s -> log.debug("Checking existence of product id={}", id));
  }

  @Override
  public Mono<Product> update(Product product) {
    return repository.save(ProductEntity.builder()
        .id(product.getId().getValue())
        .branchId(product.getBranchId().getValue())
        .name(product.getName().getValue())
        .stock(product.getStock().getValue())
        .build())
      .doOnSubscribe(s -> log.info("Updating product id={} branchId={} name={} stock={}", product.getId().getValue(), product.getBranchId().getValue(), product.getName().getValue(), product.getStock().getValue()))
      .map(entity -> new Product(entity.getId(), entity.getBranchId(), entity.getName(), entity.getStock()));
  }

  @Override
  public Mono<Void> deleteById(Long id) {
    return repository.deleteById(id)
      .doOnSubscribe(s -> log.info("Deleting product id={}", id));
  }
}


