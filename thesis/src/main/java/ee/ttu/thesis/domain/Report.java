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

    @OneToMany
    @JoinTable(name="reports",
            joinColumns = { @JoinColumn(name = "id") },
            inverseJoinColumns = { @JoinColumn(name = "reportRow_id") })
    private List<ReportRow> rows;

    private LocalDate dateMade;
}
