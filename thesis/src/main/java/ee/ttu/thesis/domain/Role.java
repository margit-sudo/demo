package ee.ttu.thesis.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Builder
@Entity
@Table(name = "roles")
@AllArgsConstructor
@NoArgsConstructor
public class Role {

    @Id
    @GeneratedValue
    private Long id;
    private ERole name;
}
