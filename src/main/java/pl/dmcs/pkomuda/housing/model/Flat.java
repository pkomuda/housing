package pl.dmcs.pkomuda.housing.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@EqualsAndHashCode(callSuper = true)
public class Flat extends BaseEntity {

    @NotNull
    private Integer number;

    @OneToOne(mappedBy = "flat")
    private Account account;

    @ManyToOne
    @JoinColumn(name = "building_id")
    private Building building;

    @OneToMany(mappedBy = "flat")
    private List<Bill> bills = new ArrayList<>();
}
