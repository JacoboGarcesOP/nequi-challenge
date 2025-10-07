package co.com.bancolombia.api.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class CreateProductRequest {
  @NotNull(message = "branchId cannot be null")
  private Long branchId;

  @NotBlank(message = "name cannot be empty")
  @Size(max = 100, message = "name cannot be greater than 100 characters")
  private String name;

  @NotNull(message = "stock cannot be null")
  @Min(value = 0, message = "stock cannot be negative")
  private Integer stock;

  public CreateProductRequest() {
  }

  public CreateProductRequest(Long branchId, String name, Integer stock) {
    this.branchId = branchId;
    this.name = name;
    this.stock = stock;
  }

  public Long getBranchId() {
    return branchId;
  }

  public void setBranchId(Long branchId) {
    this.branchId = branchId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Integer getStock() {
    return stock;
  }

  public void setStock(Integer stock) {
    this.stock = stock;
  }
}


