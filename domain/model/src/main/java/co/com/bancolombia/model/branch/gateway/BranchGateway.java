package co.com.bancolombia.model.branch.gateway;

import co.com.bancolombia.model.branch.Branch;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface BranchGateway {
  Mono<Branch> save(Branch branch);
  Flux<Branch> findByFranchiseId(Long franchiseId);
  Mono<Branch> findById(Long id);
  Mono<Boolean> existsById(Long id);
  Mono<Branch> update(Branch branch);
  Mono<Void> deleteById(Long id);
}


