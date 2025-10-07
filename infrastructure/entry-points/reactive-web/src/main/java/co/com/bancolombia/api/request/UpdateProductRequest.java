package co.com.bancolombia.api.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class UpdateProductRequest {
  @NotNull(message = "id cannot be null")
  private Long id;

  @NotNull(message = "stock cannot be null")
  @Min(value = 0, message = "stock cannot be negative")
  private Integer stock;

  @NotBlank(message = "name cannot be empty")
  @Size(max = 100, message = "name cannot be greater than 100 characters")
  private String name;

  public UpdateProductRequest() {
  }

  public UpdateProductRequest(Long id, Integer stock, String name) {
    this.id = id;
    this.stock = stock;
    this.name = name;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }


  public Integer getStock() {
    return stock;
  }

  public void setStock(Integer stock) {
    this.stock = stock;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}


