package ee.ttu.thesis.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@Entity
@Table(name = "reports")
@AllArgsConstructor
@NoArgsConstructor
public class Report {
    @Id
    @GeneratedValue
    private long id;

    @OneToMany(mappedBy = "report", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ReportRow> rows;

    private LocalDate startDate;

    private LocalDate dateMade;

    private LocalDate endDate;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
}
