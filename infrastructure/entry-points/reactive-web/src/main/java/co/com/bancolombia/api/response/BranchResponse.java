package co.com.bancolombia.api.response;

public class BranchResponse {
  private Long id;
  private Long franchiseId;
  private String name;

  public BranchResponse(Long id, Long franchiseId, String name) {
    this.id = id;
    this.franchiseId = franchiseId;
    this.name = name;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
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


