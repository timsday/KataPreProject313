package ru.kata.spring.boot_security.demo.model;


import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class User implements UserDetails {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private long id;

   @Column(unique = true)
   @NotEmpty(message = "Please, type name")
   @Size(min = 3, max=30, message = "Correct length: 3 — 30 chars")
   private String username;

   @Size(min = 4, message = "Min. 4 chars")
   private String password;

   @NotEmpty(message = "Please, type last name")
   @Size(min = 3, max=30, message = "Correct length: 3 — 30 chars")
   private String lastName;

   @NotEmpty(message = "Please, type email")
   @Email(message = "Please, type correct email")
   private String email;

   @ManyToMany(fetch = FetchType.EAGER)
   @JoinTable(name = "user_roles",
           joinColumns = @JoinColumn(name = "user_id"),
           inverseJoinColumns = @JoinColumn(name = "role_id"))
   private Set<Role> roles = new HashSet<>();

   public User() {
   }

   public User(String username, String lastName, String email) {
      this.username = username;
      this.lastName = lastName;
      this.email = email;
   }

   public User(String username, String password, String lastName, String email, Set<Role> roles) {
      this.username = username;
      this.password = password;
      this.lastName = lastName;
      this.email = email;
      this.roles = roles;
   }

   public Long getId() {
      return id;
   }

   public void setId(long id) {
      this.id = id;
   }

   @Override
   public String getUsername() {
      return username;
   }

   public void setUsername(String username) {
      this.username = username;
   }

   @Override
   public String getPassword() {
      return password;
   }

   public void setPassword(String password) {
      this.password = password;
   }

   public String getLastName() {
      return lastName;
   }

   public void setLastName(String lastName) {
      this.lastName = lastName;
   }

   public String getEmail() {
      return email;
   }

   public void setEmail(String email) {
      this.email = email;
   }

   public Set<Role> getRoles() {
      return roles;
   }

   public void setRoles(Set<Role> roles) {
      this.roles = roles;
   }

   public void addRole(Role role) {
      roles.add(role);
   }

   public String getRolesNamesString() {
      StringBuilder rolesNamesString = new StringBuilder();
      List<String> rolesNamesList = roles.stream().map(Role::getRoleName).toList();
      for (String roleName : rolesNamesList) {
         rolesNamesString.append(" ").append(roleName);
      }
      return rolesNamesString.toString();
   }

   @Override
   public Collection<? extends GrantedAuthority> getAuthorities() {
      return getRoles();
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

   @Override
   public boolean isEnabled() {
      return true;
   }
}
