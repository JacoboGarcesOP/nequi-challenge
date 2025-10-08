package co.com.bancolombia.usecase.product.dto;

public class ProductDto {
  private Long id;
  private Long branchId;
  private String name;
  private Integer stock;

  public ProductDto(Long id, Long branchId, String name, Integer stock) {
    this.id = id;
    this.branchId = branchId;
    this.name = name;
    this.stock = stock;
  }

  public Long getId() { return id; }
  public void setId(Long id) { this.id = id; }
  public Long getBranchId() { return branchId; }
  public void setBranchId(Long branchId) { this.branchId = branchId; }
  public String getName() { return name; }
  public void setName(String name) { this.name = name; }
  public Integer getStock() { return stock; }
  public void setStock(Integer stock) { this.stock = stock; }
}


