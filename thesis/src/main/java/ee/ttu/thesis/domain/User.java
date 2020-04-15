package ee.ttu.thesis.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue
    private Long id;

    private String username;

    private String email;

    private String password;

    @ManyToMany()
    @JoinColumn(name = "role_id", referencedColumnName="id")
    private Set<Role> roles = new HashSet<>();
}
