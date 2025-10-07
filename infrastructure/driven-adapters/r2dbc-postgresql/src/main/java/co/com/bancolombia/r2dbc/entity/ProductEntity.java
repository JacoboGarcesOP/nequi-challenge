package co.com.bancolombia.r2dbc.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table(name = "product", schema = "nequi_schema")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductEntity {
  @Id
  @Column("product_id")
  private Long id;

  @Column("branch_id")
  private Long branchId;

  private String name;

  private Integer stock;
}


