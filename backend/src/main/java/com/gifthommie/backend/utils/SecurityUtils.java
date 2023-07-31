package com.gifthommie.backend.utils;

import org.springframework.security.core.context.SecurityContextHolder;

import com.gifthommie.backend.config.security.MyUserDetails;

public class SecurityUtils {
	
	//GET LOGIN USER TYPE MyUserDetail
	public static MyUserDetails getPrincipal() {
		return (MyUserDetails)(SecurityContextHolder.getContext())
				.getAuthentication().getPrincipal();
	}
	
}
