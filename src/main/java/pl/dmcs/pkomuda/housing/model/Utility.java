package pl.dmcs.pkomuda.housing.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@Entity
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Utility extends BaseEntity {

    public Utility(UtilityType type) {
        this.type = type;
    }

    @NotNull
    @DecimalMin("0.01")
    @Digits(integer = 6, fraction = 2)
    private BigDecimal price;

    @Enumerated(EnumType.STRING)
    private UtilityType type;

    @ManyToOne
    @JoinColumn(name = "bill_id")
    private Bill bill;
}
