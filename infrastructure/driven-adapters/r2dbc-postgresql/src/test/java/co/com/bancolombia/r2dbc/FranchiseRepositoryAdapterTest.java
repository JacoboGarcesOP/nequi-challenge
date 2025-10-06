package co.com.bancolombia.r2dbc;

import co.com.bancolombia.model.franchise.Franchise;
import co.com.bancolombia.r2dbc.adapter.FranchiseRepositoryAdapter;
import co.com.bancolombia.r2dbc.entity.FranchiseEntity;
import co.com.bancolombia.r2dbc.repository.FranchiseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FranchiseRepositoryAdapterTest {

  @Mock
  private FranchiseRepository repository;

  private FranchiseRepositoryAdapter adapter;

  @BeforeEach
  void setUp() {
    adapter = new FranchiseRepositoryAdapter(repository);
  }

  @Test
  void shouldSaveFranchise() {
    when(repository.save(any(FranchiseEntity.class)))
      .thenReturn(Mono.just(FranchiseEntity.builder().id(1L).name("FastFood").build()));

    StepVerifier.create(adapter.save(new Franchise("FastFood")))
      .expectNextMatches(fr -> fr.getId().getValue().equals(1L) && fr.getName().getValue().equals("FastFood"))
      .verifyComplete();
  }

  @Test
  void shouldFindAllFranchises() {
    when(repository.findAll()).thenReturn(Flux.just(
      FranchiseEntity.builder().id(1L).name("A").build(),
      FranchiseEntity.builder().id(2L).name("B").build()
    ));

    StepVerifier.create(adapter.findAll())
      .expectNextCount(2)
      .verifyComplete();
  }

  @Test
  void shouldFindById() {
    when(repository.findById(1L))
      .thenReturn(Mono.just(FranchiseEntity.builder().id(1L).name("A").build()));

    StepVerifier.create(adapter.findById(1L))
      .expectNextMatches(franchise -> franchise.getId().getValue().equals(1L))
      .verifyComplete();
  }

  @Test
  void shouldUpdateFranchise() {
    when(repository.save(any(FranchiseEntity.class)))
      .thenReturn(Mono.just(FranchiseEntity.builder().id(1L).name("X").build()));

    StepVerifier.create(adapter.update(new Franchise(1L, "X")))
      .expectNextMatches(fr -> fr.getName().getValue().equals("X"))
      .verifyComplete();
  }

  @Test
  void shouldDeleteFranchise() {
    when(repository.deleteById(1L)).thenReturn(Mono.empty());

    StepVerifier.create(adapter.deleteById(1L))
      .verifyComplete();
  }

  @Test
  void shouldReturnExistsByName() {
    when(repository.existsByName("A")).thenReturn(Mono.just(true));
    StepVerifier.create(adapter.existsByName("A"))
      .expectNext(true)
      .verifyComplete();
  }

  @Test
  void shouldPropagateRepositoryErrors() {
    when(repository.findAll()).thenReturn(Flux.error(new RuntimeException("db error")));
    StepVerifier.create(adapter.findAll())
      .expectErrorMatches(t -> t instanceof RuntimeException && t.getMessage().equals("db error"))
      .verify();
  }
}


