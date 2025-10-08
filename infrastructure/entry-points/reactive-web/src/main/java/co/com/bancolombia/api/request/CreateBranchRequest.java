package co.com.bancolombia.api.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class CreateBranchRequest {
  @NotNull(message = "franchiseId cannot be null")
  private Long franchiseId;
  @NotBlank(message = "name cannot be empty")
  @Size(max = 100, message = "name cannot be greater than 100 characters")
  private String name;

  public CreateBranchRequest() {
  }

  public CreateBranchRequest(Long franchiseId, String name) {
    this.franchiseId = franchiseId;
    this.name = name;
  }

  public Long getFranchiseId() {
    return franchiseId;
  }

  public void setFranchiseId(Long franchiseId) {
    this.franchiseId = franchiseId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}


