package pl.dmcs.pkomuda.housing.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Bill extends BaseEntity {

    @NotNull
    private LocalDateTime issueDate;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;

    @Valid
    @ToString.Exclude
    @OneToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER, mappedBy = "bill")
    private List<Utility> utilities = new ArrayList<>();
}
