package pl.dmcs.pkomuda.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.Transient;

@Data
@Entity
@EqualsAndHashCode(callSuper = true)
public class Account extends BaseEntity {

    private String username;

    private String password;

    @Transient
    private String confirmPassword;

    private String email;

    private String firstName;

    private String lastName;

    private Boolean active;
}
