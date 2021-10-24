package pl.dmcs.pkomuda.housing.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToMany;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class AccessLevel extends BaseEntity {

    public AccessLevel(AccessLevelType type) {
        this.type = type;
    }

    @EqualsAndHashCode.Include
    @Enumerated(EnumType.STRING)
    private AccessLevelType type;

    @EqualsAndHashCode.Exclude
    @ManyToMany(mappedBy = "accessLevels")
    private Set<Account> accounts = new HashSet<>();
}
