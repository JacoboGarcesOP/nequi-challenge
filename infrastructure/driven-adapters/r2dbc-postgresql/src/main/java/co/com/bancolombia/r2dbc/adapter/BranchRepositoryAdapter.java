package co.com.bancolombia.r2dbc.adapter;

import co.com.bancolombia.model.branch.Branch;
import co.com.bancolombia.model.branch.gateway.BranchGateway;
import co.com.bancolombia.r2dbc.entity.BranchEntity;
import co.com.bancolombia.r2dbc.repository.BranchRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public class BranchRepositoryAdapter implements BranchGateway {
  private final BranchRepository repository;

  public BranchRepositoryAdapter(BranchRepository repository) {
    this.repository = repository;
  }

  @Override
  public Mono<Branch> save(Branch branch) {
    return repository.save(BranchEntity.builder().franchiseId(branch.getFranchiseId().getValue()).name(branch.getName().getValue()).build())
      .map(entity -> new Branch(entity.getId(), entity.getFranchiseId(), entity.getName()));
  }

  @Override
  public Flux<Branch> findByFranchiseId(Long franchiseId) {
    return repository.findByFranchiseId(franchiseId)
      .map(entity -> new Branch(entity.getId(), entity.getFranchiseId(), entity.getName()));
  }

  @Override
  public Mono<Branch> findById(Long id) {
    return repository.findById(id)
      .map(entity -> new Branch(entity.getId(), entity.getFranchiseId(), entity.getName()));
  }

  @Override
  public Mono<Boolean> existsById(Long id) {
    return repository.existsById(id);
  }

  @Override
  public Mono<Branch> update(Branch branch) {
    return repository.save(BranchEntity.builder()
        .id(branch.getId().getValue())
        .franchiseId(branch.getFranchiseId().getValue())
        .name(branch.getName().getValue())
        .build())
      .map(entity -> new Branch(entity.getId(), entity.getFranchiseId(), entity.getName()));
  }

  @Override
  public Mono<Void> deleteById(Long id) {
    return repository.deleteById(id);
  }
}


