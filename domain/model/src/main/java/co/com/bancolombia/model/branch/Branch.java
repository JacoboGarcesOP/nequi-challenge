package co.com.bancolombia.model.branch;

import co.com.bancolombia.model.branch.values.FranchiseId;
import co.com.bancolombia.model.branch.values.Id;
import co.com.bancolombia.model.branch.values.Name;

public class Branch {
  private Id id;
  private FranchiseId franchiseId;
  private Name name;

  public Branch(Long id, Long franchiseId, String name) {
    this.id = new Id(id);
    this.franchiseId = new FranchiseId(franchiseId);
    this.name = new Name(name);
  }

  public Branch(Long franchiseId, String name) {
    this.franchiseId = new FranchiseId(franchiseId);
    this.name = new Name(name);
  }

  public Id getId() {
    return id;
  }

  public void setId(Id id) {
    this.id = id;
  }

  public FranchiseId getFranchiseId() {
    return franchiseId;
  }

  public void setFranchiseId(FranchiseId franchiseId) {
    this.franchiseId = franchiseId;
  }

  public Name getName() {
    return name;
  }

  public void setName(Name name) {
    this.name = name;
  }
}


