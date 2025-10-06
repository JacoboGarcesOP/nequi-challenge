package co.com.bancolombia.r2dbc.adapter;

import co.com.bancolombia.model.franchise.Franchise;
import co.com.bancolombia.model.franchise.gateway.FranchiseGateway;
import co.com.bancolombia.r2dbc.entity.FranchiseEntity;
import co.com.bancolombia.r2dbc.repository.FranchiseRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public class FranchiseRepositoryAdapter implements FranchiseGateway {
  private final FranchiseRepository repository;

  public FranchiseRepositoryAdapter(FranchiseRepository repository) {
    this.repository = repository;
  }

  @Override
  public Mono<Franchise> save(Franchise franchise) {
    return repository.save(FranchiseEntity.builder().name(franchise.getName().getValue()).build())
      .map(entity -> new Franchise(entity.getId(), entity.getName()));
  }

  @Override
  public Flux<Franchise> findAll() {
    return repository.findAll().map(entity -> new Franchise(entity.getId(), entity.getName()));
  }

  @Override
  public Mono<Franchise> findById(Long id) {
    return repository.findById(id).map(entity -> new Franchise(entity.getId(), entity.getName()));
  }

  @Override
  public Mono<Boolean> existsByName(String name) {
    return repository.existsByName(name);
  }

  @Override
  public Mono<Boolean> existsById(Long id) {
    return repository.existsById(id);
  }

  @Override
  public Mono<Franchise> update(Franchise franchise) {
    return repository.save(FranchiseEntity.builder().id(franchise.getId().getValue()).name(franchise.getName().getValue()).build())
      .map(entity -> new Franchise(entity.getId(), entity.getName()));
  }

  @Override
  public Mono<Void> deleteById(Long id) {
    return repository.deleteById(id);
  }
}