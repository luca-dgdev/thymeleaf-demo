package com.example.thymeleafdemo.utils;

import java.util.EnumSet;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;

import com.example.thymeleafdemo.constants.AppRole;

public class Utils {

	public static long calculateBinaryAuthorities(Set<AppRole> roles) {
		long binaryAuthorities = 0;
		for (GrantedAuthority r : roles) {
			binaryAuthorities |= 1 << ((AppRole) r).getBit();
		}
		return binaryAuthorities;
	}

	public static Set<AppRole> getAuthoritiesFromBinary(long binaryAuthorities) {
		Set<AppRole> roles = EnumSet.noneOf(AppRole.class);
		for (AppRole r : AppRole.values()) {
			if ((binaryAuthorities & (1 << r.getBit())) != 0) {
				roles.add(r);
			}
		}
		return roles;
	}
}
