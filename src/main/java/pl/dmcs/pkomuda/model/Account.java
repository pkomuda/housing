package pl.dmcs.pkomuda.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Entity
@EqualsAndHashCode(callSuper = true)
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = "username", name = "username_unique"),
        @UniqueConstraint(columnNames = "email", name = "email_unique")
})
public class Account extends BaseEntity {

    @NotBlank
    @Column(updatable = false)
    private String username;

    @NotBlank
    private String password;

    @Transient
    private String confirmPassword;

    @NotBlank
    @Column(updatable = false)
    private String email;

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @NotNull
    private Boolean active;
}
