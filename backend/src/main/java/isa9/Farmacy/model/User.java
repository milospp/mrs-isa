package isa9.Farmacy.model;

import javax.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Collection;
import java.util.ArrayList;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
@Inheritance(strategy=InheritanceType.JOINED)
public abstract class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column
    private String surname;

    @Column
    private String email;

    @Column
    private String password;

    @OneToOne(cascade = CascadeType.ALL)
    private Address address;

    @Column
    private String phoneNumber;

    @ManyToOne
    private UserRole role;

    @Column
    private boolean enabled;

    @Column(name = "last_password_reset_date")
    private Timestamp lastPasswordResetDate;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return new ArrayList<UserRole>(Arrays.asList(this.role));
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    //    @Deprecated
//    public User(Long id, String name, String surname, String email, String password, Address address, String phoneNumber) {
//        this.address = address;
//        this.phoneNumber = phoneNumber;
//        this.id = id;
//        this.name = name;
//        this.surname = surname;
//        this.email = email;
//        this.password = password;
//    }

}
