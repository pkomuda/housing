package pl.dmcs.pkomuda.housing.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@Entity
@EqualsAndHashCode(callSuper = true)
public class Utility extends BaseEntity {

    @NotNull
    private BigDecimal price;

    @Enumerated(EnumType.STRING)
    private UtilityType type;

    @ManyToOne
    @JoinColumn(name = "bill_id")
    private Bill bill;
}
