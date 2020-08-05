package com.dvenci.mongo.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Id;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Setter;

import lombok.Getter;

@Getter
@Setter
@Document
public class AnalysisHistory {
	
	@Id
	private String id;

	private String username;
	private Date timestamp;
	
	@DBRef
	private List<SchemaHistory> schema;
	
	@DBRef
	private List<DescStatsHistory> descriptiveStats;
	
	@DBRef
	private List<CorrelationHistory> correlation;
	
	@DBRef
	private List<PredictiveStatsHistory> predictiveStats;
	
	@DBRef
	private List<FeatureEngHistory> featureEngineering;
	
	@DBRef
	private List<TargetPredictHistory> targetPrediction;
	
	@DBRef
	private List<BinStatsHistory> binStats;
	
	@DBRef
	private List<NullCountHistory> nullCount;
	
	@DBRef
	private List<CompStatHistory> compStats;
	
}
