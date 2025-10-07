package co.com.bancolombia.r2dbc.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table(name = "branch", schema = "nequi_schema")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BranchEntity {
  @Id
  @Column("branch_id")
  private Long id;

  @Column("franchise_id")
  private Long franchiseId;

  private String name;
}


