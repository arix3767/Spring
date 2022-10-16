package com.kowalczyk.studentclasses.entity;

import com.kowalczyk.studentclasses.enums.Role;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.Objects;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Getter
@Setter
@ToString
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class UserData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.PRIVATE)
    private Long id;
    @Column(unique = true, nullable = false)
    private String email;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private final Role role = Role.from(this.getClass());

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserData)) return false;
        UserData userData = (UserData) o;
        return !(id == null && userData.id == null)
                && Objects.equals(id, userData.id)
                && Objects.equals(email, userData.email)
                && Objects.equals(password, userData.password)
                && role == userData.role;
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, password, role);
    }
}
