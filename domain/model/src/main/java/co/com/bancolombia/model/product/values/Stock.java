package co.com.bancolombia.model.product.values;

import co.com.bancolombia.model.franchise.exceptions.DomainException;

public class Stock {

  private static final String NEGATIVE_STOCK_ERROR_MESSAGE = "Product stock cannot be negative.";

  private final Integer value;

  public Stock(final Integer value) {

    if (value == null || value < 0) {
      throw new DomainException(NEGATIVE_STOCK_ERROR_MESSAGE);
    }

    this.value = value;
  }

  public Integer getValue() {
    return value;
  }
}


