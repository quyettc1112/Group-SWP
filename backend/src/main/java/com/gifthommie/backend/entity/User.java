package com.gifthommie.backend.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gifthommie.backend.dto.UserProfileDTO;

@Entity
@Table(name = "user")
public class User {
	@Id
	@Column(name = "email")
	private String email;

	@ManyToOne
	@JoinColumn(name = "role_id", referencedColumnName = "id")
	@JsonIgnore
	private Role role;

	@Column(name = "username")
	private String username;

	@Column(name = "password")
	@JsonIgnore
	private String password;

	@Column(name = "first_name")
	private String firstName;

	@Column(name = "last_name")
	private String lastName;

	@Column(name = "phone")
	private String phone;

	@Column(name = "yob")
	private Integer yob;

	@Column(name = "avatar")
	private String avatar;

	@Column(name = "address")
	private String address;

	@Column(name = "ward_code")
	private Integer wardCode;
	

	@Column(name = "enabled")
//	@JsonIgnore
	private boolean enabled;
	

	
	@Column(name = "reset_password_token")
	@JsonIgnore
	private String reset_password_token;
	
	@Column(name = "expired_vertification_code")
	@JsonIgnore
	private LocalDateTime expired_verification_code;

//	@Column(name = "gender")
//	private String gender;
	
//	public void setGender(String gender) {
//		this.gender = gender;
//	}

	public LocalDateTime getExpired_verification_code() {
		return expired_verification_code;
	}

	public void setExpired_verification_code(LocalDateTime expired_verification_code) {
		this.expired_verification_code = expired_verification_code;
	}

	

	public String getReset_password_token() {
		return reset_password_token;
	}

	public void setReset_password_token(String reset_password_token) {
		this.reset_password_token = reset_password_token;
	}

	
	// Update 
	public void updateExpiredVertificationCode () {
		 this.expired_verification_code = LocalDateTime.now().plusMinutes(5);		
	}
	
	// SEND DATA TO EDIT PROFILE
	public boolean editProfile(UserProfileDTO userProfile, Role role) {
		email = userProfile.getEmail();
		firstName = userProfile.getFirstName();
		lastName = userProfile.getLastName();
		password = userProfile.getPassword();
		this.role = role;
		phone = userProfile.getPhone();
		yob = userProfile.getYob();
		avatar = userProfile.getAvatar();
		address = userProfile.getAddress();
//		wardCode = userProfile.getWardCode();

		return true;
	}

//	public String getGender() {
//		return gender;
//		
//	}

	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUsername() {
		return username;
	}
	
	public String getId() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public Role getRole() {
		return role;
	}

	public Integer getRoleId() {
		return role.getId();
	}

	public String getAuthority() {
		return role.getName();
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public String getFirstName() {
		return firstName == null ? "" : firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName == null ? "" : lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Integer getYob() {
		return yob;
	}

	public void setYob(Integer yob) {
		this.yob = yob;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Integer getWardCode() {
		return wardCode;
	}

	public void setWardCode(Integer wardCode) {
		this.wardCode = wardCode;
	}
}