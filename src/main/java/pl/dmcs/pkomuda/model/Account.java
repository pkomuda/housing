package pl.dmcs.pkomuda.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Entity
@EqualsAndHashCode(callSuper = true)
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = "username", name = "username_unique"),
        @UniqueConstraint(columnNames = "email", name = "email_unique"),
        @UniqueConstraint(columnNames = "token", name = "token_unique")
})
public class Account extends BaseEntity {

    @NotBlank
    private String username;

    @NotBlank
    @Size(min = 8)
    private String password;

    @Transient
    private String confirmPassword;

    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String phoneNumber;

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    private Boolean active;

    private String token;

    @OneToOne
    @JoinColumn(name = "flat_id")
    private Flat flat;

    @OneToMany(mappedBy = "account")
    private List<Bill> bills = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "account_access_levels",
            joinColumns = @JoinColumn(name = "account_id"),
            inverseJoinColumns = @JoinColumn(name = "access_level_id")
    )
    private Set<AccessLevel> accessLevels = new HashSet<>();
}
