package pl.dmcs.pkomuda.housing.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@EqualsAndHashCode(callSuper = true)
public class Building extends BaseEntity {

    @NotBlank
    @Size(max = 32)
    private String city;

    @NotBlank
    @Size(max = 32)
    private String streetName;

    @NotBlank
    @Size(max = 32)
    private String streetNumber;

    @NotBlank
    @Pattern(regexp = "[0-9]{2}-?[0-9]{3}")
    private String postalCode;

    @OneToMany(mappedBy = "building", fetch = FetchType.EAGER)
    private List<Flat> flats = new ArrayList<>();

    public String formattedPostalCode() {
        return MessageFormat.format("{0}-{1}",
                postalCode.substring(0, 2),
                postalCode.substring(2, 5));
    }
}
