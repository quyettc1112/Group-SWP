package com.gifthommie.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gifthommie.backend.dto.APIPageableResponseDTO;
import com.gifthommie.backend.dto.UserProfileDTO;
import com.gifthommie.backend.entity.Role;
import com.gifthommie.backend.entity.User;
import com.gifthommie.backend.exception.NotFoundException;
import com.gifthommie.backend.service.RoleService;
import com.gifthommie.backend.service.UserService;

@RestController
@RequestMapping("/manager/staff")
public class ManagerStaffController {
	@Autowired
	UserService userService;
	
	@Autowired
	RoleService roleService;
	
	//Default Password for new Staff is 123456
	final String DEFAULT_PASSWORD = "$2a$10$eiGJNzsBj.TKTG72BRRMteJlOIBv9x3KoaTAbzYKaX652FUB17pzG";
	//2 ROLE_STAFF IN DATABASE
	final String ROLE_STAFF = "ROLE_STAFF";
	// DELETE ENABLED
	final boolean BAN_ENABLED = false;
	// ACTIVE ENABLED
	final boolean ACTIVE_ENABLED = true;
	// DEAFULT PAGE VALUE
	final String PAGE = "0";
	final String PAGE_SIZE = "12";
	
	
	//VIEW ALL STAFF WITH PAGEABLE
	@GetMapping
	public APIPageableResponseDTO<User> getStaffList(
			@RequestParam(defaultValue = PAGE, name = "page") Integer pageNo,
			@RequestParam(defaultValue = PAGE_SIZE, name = "size") Integer pageSize,
			@RequestParam(defaultValue = "", name = "search") String search,
			@RequestParam(required = false, name = "status") Boolean status) {
		Role role = roleService.getRoleByRoleName(ROLE_STAFF);
		if (search == null) search = "";
		
		return status == null ? userService.searchUsers(pageNo, pageSize, role.getId(), search) : userService.searchUsers(pageNo, pageSize, role.getId(), status, search);
		
//		return userService.getPageableUsers(pageNo, pageSize, role.getId(), ACTIVE_ENABLED);
	}
	
	//VIEW A ENABLED STAFF WITH USERNAME OR EMAIL
	@GetMapping("/{check}")
	public User getStaff(@PathVariable String check) {
		User u1 = userService.getUserByEmailOrUsername(check, ACTIVE_ENABLED);
		User u2 = userService.getUserByEmailOrUsername(check, !ACTIVE_ENABLED);
		User u = u1 != null ? u1 : u2;
		//CANNOT FOUND USER
		if (u == null)
			throw new NotFoundException("Cannot find User");
		
		return u;
	}
	
	//DELETE ENABLED STAFF WITH USERNAME OR EMAIL
	@DeleteMapping("/{check}")
	public boolean deleteStaff(@PathVariable String check) {
		//GET USER WITH EMAIL OR USERNAME
		User user = userService.getUserByEmailOrUsername(check, ACTIVE_ENABLED);
		
		//CHECK ENABLED USER
		if (user == null)
			throw new NotFoundException("NOT FOUND USER");
		
		//READY TO DELETE
		return userService.setEnabledUserByEmail(check, BAN_ENABLED);
	}
	
	//CREATE STAFF
	@PostMapping
	public User createStaff(@RequestBody User user) {
		//USERNAME OR EMAIL IS EXIST
		if (userService.checkExistUser(user.getEmail()))
			throw new RuntimeException("EMAIL IS EXIST");
		if (userService.checkExistUser(user.getUsername()))
			throw new RuntimeException("USERNAME IS EXIST");
		
		//GET ROLE BY ROLE NAME
		Role role = roleService.getRoleByRoleName(ROLE_STAFF);
		
		//SET DEFAULT PASSWORD
		user.setPassword(DEFAULT_PASSWORD);
		//SET ENABLED STAFF ACTIVE
		user.setEnabled(ACTIVE_ENABLED);
		//SET ROLE STAFF
		user.setRole(role);
		
		return userService.createUser(user);
	}

	//UPDATE STAFF PROFILE
	@PutMapping("/{check}")
	public User updateStaffProfile(@PathVariable String check, 
								@RequestBody UserProfileDTO userProfile) {
		//userProfile USE TO GAIN NEW PROFILE OF USER STAFF
		//PASSWORD CAN BE CUSTOMIZE BUT I WANT TO DEFAULT PASSWORD
		//AFTER EDIT PROFILE OF STAFF
		User user = userService.getUserByEmailOrUsername(check, ACTIVE_ENABLED);
		
		//CANNOT FOUND THE USER
		if (user == null)
			throw new NotFoundException("CANNOT FOUND USER");
		
		//NEW EMAIL IS EXIST
		if (!user.getEmail().equals(userProfile.getEmail()) && userService.checkExistUser(userProfile.getEmail()))
			throw new RuntimeException("EMAIL IS EXIST");
		//SET DEFAULT PASSWORD
		
		if (userProfile.getEmail() == null)
			throw new RuntimeException("EMAIL CANNOT BE BLANK");
		
		//SET DEFAULT PASSWORD
		userProfile.setPassword(DEFAULT_PASSWORD);
		
		if (userProfile.getPassword() == null)
			throw new RuntimeException("PASSWORD CANNOT BE BLANK");
			
		//UPDATE PROFILE TO OLD USER
		//(userProfile, ROLE)
		//AND SET NEW ROLE
		user.editProfile(userProfile, user.getRole());
		
		//SET ENABLED OF USER TO ACTIVE
		user.setEnabled(ACTIVE_ENABLED);	
		return userService.updateUserProfile(user);
	}
}