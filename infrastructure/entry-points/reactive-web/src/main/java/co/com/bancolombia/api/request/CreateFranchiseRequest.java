package co.com.bancolombia.api.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class CreateFranchiseRequest {
  @NotBlank(message = "Name cannot be empty")
  @Size(max = 50, message = "Name cannot be greater than 50 characters")
  private String name;

  public CreateFranchiseRequest() {
  }

  public CreateFranchiseRequest(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}


