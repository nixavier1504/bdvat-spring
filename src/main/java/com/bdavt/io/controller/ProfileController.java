package com.bdavt.io.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bdavt.io.postgres.model.Profile;
import com.bdavt.io.service.ProfileService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/profile")
public class ProfileController {
	
	@Autowired
	private ProfileService profile;
	
	@GetMapping("/{username}")
	public List<Profile> getProfiles(@PathVariable("username") String username) {
		return profile.getProfiles(username);
	}
	
	@PostMapping("")
	public Profile createProfile(@RequestBody Profile P) {
		return profile.saveProfile(P);
	}
}
