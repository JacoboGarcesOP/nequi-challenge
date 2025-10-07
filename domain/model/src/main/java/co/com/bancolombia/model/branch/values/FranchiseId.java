package co.com.bancolombia.model.branch.values;

import co.com.bancolombia.model.franchise.exceptions.DomainException;

public class FranchiseId {

  private static final String NULL_ID_ERROR_MESSAGE = "The franchise id cannot be null.";

  private final Long value;

  public FranchiseId(final Long value) {

    if (value == null) {
      throw new DomainException(NULL_ID_ERROR_MESSAGE);
    }

    this.value = value;
  }

  public Long getValue() {
    return value;
  }
}


