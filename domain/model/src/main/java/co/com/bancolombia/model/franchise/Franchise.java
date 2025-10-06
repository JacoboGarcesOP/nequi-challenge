package co.com.bancolombia.model.franchise;

import co.com.bancolombia.model.franchise.values.Id;
import co.com.bancolombia.model.franchise.values.Name;

public class Franchise {
  private Id id;
  private Name name;

  public Franchise(Long id, String name) {
    this.id = new Id(id);
    this.name = new Name(name);
  }

  public Franchise(String name) {
    this.name = new Name(name);
  }

  public Id getId() {
    return id;
  }

  public void setId(Id id) {
    this.id = id;
  }

  public Name getName() {
    return name;
  }

  public void setName(Name name) {
    this.name = name;
  }
}


