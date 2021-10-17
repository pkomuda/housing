package pl.dmcs.pkomuda.model;

import lombok.Data;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@MappedSuperclass
public abstract class BaseEntity implements Serializable {

    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Setter(lombok.AccessLevel.NONE)
    private Long id;

    @Version
    @Setter(lombok.AccessLevel.NONE)
    private Long version;
}
