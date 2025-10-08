package co.com.bancolombia.api.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class UpdateBranchRequest {
  @NotNull(message = "id cannot be null")
  private Long id;

  @NotBlank(message = "name cannot be empty")
  @Size(max = 100, message = "name cannot be greater than 100 characters")
  private String name;

  public UpdateBranchRequest() {
  }

  public UpdateBranchRequest(Long id, String name) {
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


