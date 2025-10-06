package co.com.bancolombia.model.franchise.gateway;

import co.com.bancolombia.model.franchise.Franchise;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface FranchiseGateway {
  Mono<Franchise> save(Franchise franchise);
  Flux<Franchise> findAll();
  Mono<Franchise> findById(Long id);
  Mono<Boolean> existsByName(String name);
  Mono<Boolean> existsById(Long id);
  Mono<Franchise> update(Franchise franchise);
  Mono<Void> deleteById(Long id);
}


