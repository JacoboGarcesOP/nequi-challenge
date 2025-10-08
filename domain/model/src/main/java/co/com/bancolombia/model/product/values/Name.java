package co.com.bancolombia.model.product.values;

import co.com.bancolombia.model.franchise.exceptions.DomainException;

public class Name {

  private static final String NULL_NAME_ERROR_MESSAGE = "Product name cannot be null.";
  private static final String MAX_LENGTH_ERROR_MESSAGE = "Product name cannot be greater than 100.";
  private static final int MAX_NAME_LENGTH = 100;

  private final String value;

  public Name(final String value) {

    if (value == null || value.trim().isEmpty()) {
      throw new DomainException(NULL_NAME_ERROR_MESSAGE);
    }

    if (value.trim().length() > MAX_NAME_LENGTH) {
      throw new DomainException(MAX_LENGTH_ERROR_MESSAGE);
    }

    this.value = value.trim();
  }

  public String getValue() {
    return value;
  }
}


