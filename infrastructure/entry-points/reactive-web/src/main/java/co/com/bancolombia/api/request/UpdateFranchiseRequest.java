package co.com.bancolombia.api.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class UpdateFranchiseRequest {
  @NotNull(message = "Id cannot be null")
  private Long id;

  @NotBlank(message = "Name cannot be empty")
  @Size(max = 50, message = "Name cannot be greater than 50 characters")
  private String name;

  public UpdateFranchiseRequest() {
  }

  public UpdateFranchiseRequest(Long id, String name) {
    this.id = id;
    this.name = name;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}


