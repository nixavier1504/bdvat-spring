package com.dvenci.postgres.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dvenci.postgres.model.Profile;
import com.dvenci.postgres.repo.ProfileRepo;

@Service
public class ProfileService {
	
	@Autowired
	private ProfileRepo repo;
	
	public List<Profile> getProfiles(String username) {
		List<Profile> profiles = repo.getProfilesByUsername(username);
		return profiles;
	}
	
	public Profile saveProfile(Profile P) {
		return repo.save(P);
	}
}
