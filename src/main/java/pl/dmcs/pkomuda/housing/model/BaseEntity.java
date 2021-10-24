package pl.dmcs.pkomuda.housing.model;

import lombok.Data;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Data
@MappedSuperclass
public abstract class BaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Setter(lombok.AccessLevel.NONE)
    private Long id;

    @Version
    @Setter(lombok.AccessLevel.NONE)
    private Long version;
}
