package com.bdavt.io.postgres.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.bdavt.io.postgres.model.Profile;

@Repository
public interface ProfileRepo extends JpaRepository<Profile, Long>{

	@Query("SELECT P FROM Profile P WHERE P.username = :username")
	public List<Profile> getProfilesByUsername(@Param("username") String username);
}
