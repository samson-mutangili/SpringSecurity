package com.samson.user;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder //helps build the object
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "_user") //defines table name
public class User implements UserDetails{

	@Id
	@GeneratedValue
	private Integer id;
	private String firstname;
	private String lastname;
	private String email;
	private String password;
	
	@Enumerated(EnumType.STRING)
	private Role role; //defines roles for the user.
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		//return the role of the user
		return List.of(new SimpleGrantedAuthority(role.name()));
	}
	@Override
	public String getPassword() {
		// get the password of the user
		return password;
	}
	@Override
	public String getUsername() {
		// returns the username/email of the user
		return email;
	}
	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}
	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}
	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}
	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}
	
	
	
}
