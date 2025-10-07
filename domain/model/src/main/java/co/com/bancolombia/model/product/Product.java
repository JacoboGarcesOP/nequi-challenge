package co.com.bancolombia.model.product;

import co.com.bancolombia.model.product.values.BranchId;
import co.com.bancolombia.model.product.values.Id;
import co.com.bancolombia.model.product.values.Name;
import co.com.bancolombia.model.product.values.Stock;

public class Product {
  private Id id;
  private BranchId branchId;
  private Name name;
  private Stock stock;

  public Product(Long id, Long branchId, String name, Integer stock) {
    this.id = new Id(id);
    this.branchId = new BranchId(branchId);
    this.name = new Name(name);
    this.stock = new Stock(stock);
  }

  public Product(Long branchId, String name, Integer stock) {
    this.branchId = new BranchId(branchId);
    this.name = new Name(name);
    this.stock = new Stock(stock);
  }

  public Id getId() {
    return id;
  }

  public void setId(Id id) {
    this.id = id;
  }

  public BranchId getBranchId() {
    return branchId;
  }

  public void setBranchId(BranchId branchId) {
    this.branchId = branchId;
  }

  public Name getName() {
    return name;
  }

  public void setName(Name name) {
    this.name = name;
  }

  public Stock getStock() {
    return stock;
  }

  public void setStock(Stock stock) {
    this.stock = stock;
  }
}


