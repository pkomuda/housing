package pl.dmcs.pkomuda.housing.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.text.MessageFormat;
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
    @Size(max = 32)
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
    @Pattern(regexp = "(\\+[0-9]{2})?(\\s?[0-9]{3}){3}")
    private String phoneNumber;

    @NotBlank
    @Size(max = 32)
    private String firstName;

    @NotBlank
    @Size(max = 32)
    private String lastName;

    private Boolean active;

    private Boolean confirmed;

    private String token;

    @OneToOne
    @JoinColumn(name = "flat_id")
    private Flat flat;

    @OneToMany(cascade = CascadeType.PERSIST, mappedBy = "account")
    private List<Bill> bills = new ArrayList<>();

    @ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinTable(
            name = "account_access_levels",
            joinColumns = @JoinColumn(name = "account_id"),
            inverseJoinColumns = @JoinColumn(name = "access_level_id")
    )
    private Set<AccessLevel> accessLevels = new HashSet<>();

    public void addBill(Bill bill) {
        bills.add(bill);
        bill.setAccount(this);
    }

    public void deleteBill(Bill bill) {
        bills.remove(bill);
        bill.setAccount(null);
    }

    public void addAccessLevel(AccessLevelType accessLevelType) {
        AccessLevel accessLevel = new AccessLevel(accessLevelType);
        accessLevels.add(accessLevel);
        accessLevel.getAccounts().add(this);
    }

    public void deleteAccessLevel(AccessLevelType accessLevelType) {
        AccessLevel accessLevel = new AccessLevel(accessLevelType);
        accessLevels.remove(accessLevel);
        accessLevel.getAccounts().remove(this);
    }

    public String formattedPhoneNumber() {
        return switch (phoneNumber.length()) {
            case 9 -> MessageFormat.format("{0} {1} {2}",
                    phoneNumber.substring(0, 3),
                    phoneNumber.substring(3, 6),
                    phoneNumber.substring(6, 9));
            case 12 -> MessageFormat.format("{0} {1} {2} {3}",
                    phoneNumber.substring(0, 3),
                    phoneNumber.substring(3, 6),
                    phoneNumber.substring(6, 9),
                    phoneNumber.substring(9, 12));
            default -> phoneNumber;
        };
    }
}
