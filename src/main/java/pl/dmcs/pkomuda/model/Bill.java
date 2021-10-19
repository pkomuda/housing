package pl.dmcs.pkomuda.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@EqualsAndHashCode(callSuper = true)
public class Bill extends BaseEntity {

    private LocalDate issueDate;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;

    @ManyToOne
    @JoinColumn(name = "flat_id")
    private Flat flat;

    @OneToMany(mappedBy = "bill")
    private List<Utility> utilities = new ArrayList<>();
}
