package pl.dmcs.pkomuda.housing.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@Entity
@EqualsAndHashCode(callSuper = true)
public class Flat extends BaseEntity {

    @Min(1)
    @NotNull
    private Integer number;

    @Min(1)
    @NotNull
    private Integer rooms;

    @NotNull
    @DecimalMin("0.01")
    @Digits(integer = 4, fraction = 2)
    private BigDecimal area;

    @JsonIgnore
    @OneToOne(mappedBy = "flat")
    private Account account;

    @JsonIgnore
    @NotNull
    @ManyToOne
    @JoinColumn(name = "building_id")
    private Building building;

    public void setAccount(Account account) {
        this.account = account;
        account.setFlat(this);
    }

    @Override
    public String toString() {
        return building.getStreetName()
                + " " + building.getStreetNumber()
                + "/" + number +
                ", " + building.formattedPostalCode()
                + " " + building.getCity();
    }
}
