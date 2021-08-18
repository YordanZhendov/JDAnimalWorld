package jdanimal.demo.data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Transient;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name="users")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class User extends BaseEntity implements UserDetails {

    @Column(name = "user_name",nullable = false, unique = true)
    private String username;
    @Column(name = "full_name")
    private String fullName;
    @Column(name = "password",nullable = false)
    private String password;
    @Column(name = "confirmed_password",nullable = false)
    private String confirmPassword;
    @Column(name = "email",nullable = false,unique = true)
    private String email;
    @Column(name = "phone_number",unique = true)
    private String phoneNumber;
    @Column(name = "country")
    private String country;
    @Column(name = "city")
    private String city;
    @Column(name = "postcode")
    private String postcode;
    @Column(name = "agree_policy")
    private boolean policyAgree;

    @ManyToMany(cascade= {CascadeType.PERSIST, CascadeType.REMOVE},fetch = FetchType.EAGER)
    @JoinTable(name = "users_roles",joinColumns = @JoinColumn(name = "user_id",referencedColumnName = "id")
            ,inverseJoinColumns = @JoinColumn(name = "role_id",referencedColumnName = "id"))
    private Set<Role> authorities;

    @OneToMany(mappedBy = "user")
    private Set<Animal> animals;

    @OneToMany(mappedBy = "user")
    private Set<Accessory> accessories;

    @Override
    @Transient
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    @Transient
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    @Transient
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    @Transient
    public boolean isEnabled() {
        return true;
    }

}
