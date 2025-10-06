package co.com.bancolombia.r2dbc.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table(name = "franchise", schema = "nequi_schema")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FranchiseEntity {
  @Id
  @Column("franchise_id")
  private Long id;
  private String name;
}


