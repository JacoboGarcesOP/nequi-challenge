package co.com.bancolombia.r2dbc.adapter;

import co.com.bancolombia.model.branch.Branch;
import co.com.bancolombia.model.branch.gateway.BranchGateway;
import co.com.bancolombia.r2dbc.entity.BranchEntity;
import co.com.bancolombia.r2dbc.repository.BranchRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Repository
public class BranchRepositoryAdapter implements BranchGateway {
  private final BranchRepository repository;

  public BranchRepositoryAdapter(BranchRepository repository) {
    this.repository = repository;
  }

  @Override
  public Mono<Branch> save(Branch branch) {
    return repository.save(BranchEntity.builder().franchiseId(branch.getFranchiseId().getValue()).name(branch.getName().getValue()).build())
      .doOnSubscribe(s -> log.info("Saving branch franchiseId={} name={}", branch.getFranchiseId().getValue(), branch.getName().getValue()))
      .map(entity -> new Branch(entity.getId(), entity.getFranchiseId(), entity.getName()));
  }

  @Override
  public Flux<Branch> findByFranchiseId(Long franchiseId) {
    return repository.findByFranchiseId(franchiseId)
      .doOnSubscribe(s -> log.info("Finding branches by franchiseId={}", franchiseId))
      .map(entity -> new Branch(entity.getId(), entity.getFranchiseId(), entity.getName()));
  }

  @Override
  public Mono<Branch> findById(Long id) {
    return repository.findById(id)
      .doOnSubscribe(s -> log.info("Finding branch by id={}", id))
      .map(entity -> new Branch(entity.getId(), entity.getFranchiseId(), entity.getName()));
  }

  @Override
  public Mono<Boolean> existsById(Long id) {
    return repository.existsById(id)
      .doOnSubscribe(s -> log.debug("Checking existence of branch id={}", id));
  }

  @Override
  public Mono<Branch> update(Branch branch) {
    return repository.save(BranchEntity.builder()
        .id(branch.getId().getValue())
        .franchiseId(branch.getFranchiseId().getValue())
        .name(branch.getName().getValue())
        .build())
      .doOnSubscribe(s -> log.info("Updating branch id={} franchiseId={} name={}", branch.getId().getValue(), branch.getFranchiseId().getValue(), branch.getName().getValue()))
      .map(entity -> new Branch(entity.getId(), entity.getFranchiseId(), entity.getName()));
  }

  @Override
  public Mono<Void> deleteById(Long id) {
    return repository.deleteById(id)
      .doOnSubscribe(s -> log.info("Deleting branch id={}", id));
  }
}


