package pl.dmcs.pkomuda.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToMany;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@EqualsAndHashCode(callSuper = false)
public class AccessLevel extends BaseEntity {

    @EqualsAndHashCode.Include
    @Enumerated(EnumType.ORDINAL)
    private AccessLevelType type;

    @EqualsAndHashCode.Exclude
    @ManyToMany(mappedBy = "accessLevels")
    private Set<Account> account = new HashSet<>();
}
