package co.com.bancolombia.usecase.franchise.dto;

import java.util.List;

public class FranchiseWithBranchesResponse {
  private Long id;
  private String name;
  private List<BranchWithTopProductResponse> branches;

  public FranchiseWithBranchesResponse(Long id, String name, List<BranchWithTopProductResponse> branches) {
    this.id = id;
    this.name = name;
    this.branches = branches;
  }

  public Long getId() { return id; }
  public void setId(Long id) { this.id = id; }
  public String getName() { return name; }
  public void setName(String name) { this.name = name; }
  public List<BranchWithTopProductResponse> getBranches() { return branches; }
  public void setBranches(List<BranchWithTopProductResponse> branches) { this.branches = branches; }

  public static class BranchWithTopProductResponse {
    private Long branchId;
    private String branchName;
    private ProductResponse topProduct; // nullable

    public BranchWithTopProductResponse(Long branchId, String branchName, ProductResponse topProduct) {
      this.branchId = branchId;
      this.branchName = branchName;
      this.topProduct = topProduct;
    }

    public Long getBranchId() { return branchId; }
    public void setBranchId(Long branchId) { this.branchId = branchId; }
    public String getBranchName() { return branchName; }
    public void setBranchName(String branchName) { this.branchName = branchName; }
    public ProductResponse getTopProduct() { return topProduct; }
    public void setTopProduct(ProductResponse topProduct) { this.topProduct = topProduct; }
  }

  public static class ProductResponse {
    private Long id;
    private Long branchId;
    private String name;
    private Integer stock;

    public ProductResponse(Long id, Long branchId, String name, Integer stock) {
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
}


