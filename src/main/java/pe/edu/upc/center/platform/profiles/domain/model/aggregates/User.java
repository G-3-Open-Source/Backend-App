package pe.edu.upc.center.platform.profiles.domain.model.aggregates;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

import pe.edu.upc.center.platform.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;

@Entity
@Table(name = "users")
@NoArgsConstructor
public class User extends AuditableAbstractAggregateRoot<User> {

    @Getter
    @NotNull
    @Column(name = "name", length = 25, nullable = false)
    private String name;

    @Getter
    @NotNull
    @Column(name = "email", length = 25, nullable = false)
    private String email;

    @Getter
    @NotNull
    @Column(name = "password", length = 25, nullable = false)
    private String password;

    @Getter
    @NotNull
    @Column(name = "isActive", nullable = false)
    private Boolean isActive;

    @Getter
    @NotNull
    @Column(name = "birthDate", length = 25, nullable = false)
    private String birthDate;

    @Getter
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "userProfile_id", nullable = false)
    private UserProfile userProfile;

    public User(String name, String email, String password, Boolean isActive, String birthDate, UserProfile userProfile) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.isActive = isActive;
        this.birthDate = birthDate;
        this.userProfile = userProfile;
    }
}
