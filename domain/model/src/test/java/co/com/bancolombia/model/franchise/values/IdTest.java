package co.com.bancolombia.model.franchise.values;

import co.com.bancolombia.model.franchise.exceptions.DomainException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IdTest {

  @Test
  void shouldCreateIdSuccessfully() {
    Id id = new Id(1L);
    assertEquals(1L, id.getValue());
  }

  @Test
  void shouldFailWhenIdIsNull() {
    DomainException ex = assertThrows(DomainException.class, () -> new Id(null));
    assertEquals("The id cannot be null.", ex.getMessage());
  }
}


