package co.com.bancolombia.model.franchise.values;

import co.com.bancolombia.model.franchise.exceptions.DomainException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NameTest {

  @Test
  void shouldCreateNameSuccessfully() {
    Name name = new Name("FastFood");
    assertEquals("FastFood", name.getValue());
  }

  @Test
  void shouldFailWhenNameIsNull() {
    DomainException ex = assertThrows(DomainException.class, () -> new Name(null));
    assertEquals("Franchise name cannot be null.", ex.getMessage());
  }

  @Test
  void shouldFailWhenNameIsEmpty() {
    DomainException ex = assertThrows(DomainException.class, () -> new Name(" "));
    assertEquals("Franchise name cannot be null.", ex.getMessage());
  }

  @Test
  void shouldFailWhenNameExceedsMaxLength() {
    String longName = "a".repeat(51);
    DomainException ex = assertThrows(DomainException.class, () -> new Name(longName));
    assertEquals("Franchise name cannot be greater than 50.", ex.getMessage());
  }
}


