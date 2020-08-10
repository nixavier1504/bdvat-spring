package com.dvenci.utils;

import java.util.List;

import org.json.simple.JSONArray;

import com.dvenci.postgres.model.Profile;

public class ProfileUtils {
	
	@SuppressWarnings("unchecked")
	public static List<Profile> processProfiles(List<Profile> profiles){
		for(Profile P: profiles) {
			JSONArray datasetArr = new JSONArray();
			datasetArr.add(P.getDataset());
			P.setDataset(datasetArr.toJSONString());
		}
		return profiles;
	}
}
