package co.com.bancolombia.r2dbc.adapter;

import co.com.bancolombia.model.franchise.Franchise;
import co.com.bancolombia.model.franchise.gateway.FranchiseGateway;
import co.com.bancolombia.r2dbc.entity.FranchiseEntity;
import co.com.bancolombia.r2dbc.repository.FranchiseRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Repository
public class FranchiseRepositoryAdapter implements FranchiseGateway {
  private final FranchiseRepository repository;

  public FranchiseRepositoryAdapter(FranchiseRepository repository) {
    this.repository = repository;
  }

  @Override
  public Mono<Franchise> save(Franchise franchise) {
    return repository.save(FranchiseEntity.builder().name(franchise.getName().getValue()).build())
      .doOnSubscribe(s -> log.info("Saving franchise name={}", franchise.getName().getValue()))
      .map(entity -> new Franchise(entity.getId(), entity.getName()));
  }

  @Override
  public Flux<Franchise> findAll() {
    return repository.findAll()
      .doOnSubscribe(s -> log.info("Finding all franchises"))
      .map(entity -> new Franchise(entity.getId(), entity.getName()));
  }

  @Override
  public Mono<Franchise> findById(Long id) {
    return repository.findById(id)
      .doOnSubscribe(s -> log.info("Finding franchise by id={}", id))
      .map(entity -> new Franchise(entity.getId(), entity.getName()));
  }

  @Override
  public Mono<Boolean> existsByName(String name) {
    return repository.existsByName(name)
      .doOnSubscribe(s -> log.debug("Checking existence of franchise name={}", name));
  }

  @Override
  public Mono<Boolean> existsById(Long id) {
    return repository.existsById(id)
      .doOnSubscribe(s -> log.debug("Checking existence of franchise id={}", id));
  }

  @Override
  public Mono<Franchise> update(Franchise franchise) {
    return repository.save(FranchiseEntity.builder().id(franchise.getId().getValue()).name(franchise.getName().getValue()).build())
      .doOnSubscribe(s -> log.info("Updating franchise id={} name={}", franchise.getId().getValue(), franchise.getName().getValue()))
      .map(entity -> new Franchise(entity.getId(), entity.getName()));
  }

  @Override
  public Mono<Void> deleteById(Long id) {
    return repository.deleteById(id)
      .doOnSubscribe(s -> log.info("Deleting franchise id={}", id));
  }
}