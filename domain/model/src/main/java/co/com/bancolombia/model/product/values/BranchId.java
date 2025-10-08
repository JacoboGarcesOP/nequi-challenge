package co.com.bancolombia.model.product.values;

import co.com.bancolombia.model.franchise.exceptions.DomainException;

public class BranchId {

  private static final String NULL_ID_ERROR_MESSAGE = "The branch id cannot be null.";

  private final Long value;

  public BranchId(final Long value) {

    if (value == null) {
      throw new DomainException(NULL_ID_ERROR_MESSAGE);
    }

    this.value = value;
  }

  public Long getValue() {
    return value;
  }
}


