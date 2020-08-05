package com.dvenci.mongo.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.dvenci.http.model.request.BinStats;
import com.dvenci.http.model.request.CompStats;
import com.dvenci.http.model.request.Correlation;
import com.dvenci.http.model.request.DescriptiveAnalysis;
import com.dvenci.http.model.request.FeatureEngineering;
import com.dvenci.http.model.request.NullCount;
import com.dvenci.http.model.request.PredictTarget;
import com.dvenci.http.model.request.PredictiveAnalysis;
import com.dvenci.http.model.request.Schema;
import com.dvenci.mongo.model.BinStatsHistory;
import com.dvenci.mongo.model.CompStatHistory;
import com.dvenci.mongo.model.CorrelationHistory;
import com.dvenci.mongo.model.DescStatsHistory;
import com.dvenci.mongo.model.FeatureEngHistory;
import com.dvenci.mongo.model.AnalysisHistory;
import com.dvenci.mongo.model.NullCountHistory;
import com.dvenci.mongo.model.PredictiveStatsHistory;
import com.dvenci.mongo.model.SchemaHistory;
import com.dvenci.mongo.model.TargetPredictHistory;
import com.dvenci.mongo.repo.BinStatsHistoryRepo;
import com.dvenci.mongo.repo.CompStatHistoryRepo;
import com.dvenci.mongo.repo.CorrelationHistoryRepo;
import com.dvenci.mongo.repo.DescStatsHistoryRepo;
import com.dvenci.mongo.repo.FeatureEngHistoryRepo;
import com.dvenci.mongo.repo.HistoryRepo;
import com.dvenci.mongo.repo.NullCountHistoryRepo;
import com.dvenci.mongo.repo.PredictiveStatsHistoryRepo;
import com.dvenci.mongo.repo.SchemaHistoryRepo;
import com.dvenci.mongo.repo.TargetPredictHistoryRepo;

@Service
public class MongoServiceImpl implements MongoService {
	
	@Autowired
	private HistoryRepo mongo;
	
	@Autowired
	private MongoTemplate template;
	
	@Autowired
	private SchemaHistoryRepo schemaRepo;
	
	@Autowired
	private DescStatsHistoryRepo descRepo;
	
	@Autowired
	private CorrelationHistoryRepo correlationRepo;
	
	@Autowired
	private PredictiveStatsHistoryRepo predStatRepo;
	
	@Autowired
	private FeatureEngHistoryRepo featureEngRepo;
	
	@Autowired
	private TargetPredictHistoryRepo targetPredictRepo;
	
	@Autowired
	private BinStatsHistoryRepo binStatsRepo;

	@Autowired
	private NullCountHistoryRepo nullCountRepo;
	
	@Autowired
	private CompStatHistoryRepo compStatsRepo;
	
	@Override
	public List<AnalysisHistory> getTimeStampsByUsername(String username) {
		return mongo.getTimeStampsByUsername(username);
	}

	@Override
	public AnalysisHistory createEntry(String username) {
		AnalysisHistory entry = new AnalysisHistory();
		entry.setTimestamp(new Date());
		entry.setUsername(username);
		return mongo.save(entry);
	}

	@Override
	public AnalysisHistory getEntry(String id) {
		Optional<AnalysisHistory> E = mongo.findById(id);
		if(E.isPresent()) {
			return E.get();
		}
		return null;
	}

	@Override
	public void saveSchema(Schema payload, JSONArray data) {
		SchemaHistory entry = new SchemaHistory();
		entry.setData(data);
		entry.setDataset(payload.getDataset());
		entry.setExecutionId(payload.getExecutionId());
		entry.setProfile(payload.getProfile());
		entry.setUsername(payload.getUsername());
		entry = schemaRepo.save(entry);
		Update update = new Update();
		update.addToSet("schema", entry);
		Criteria criteria = Criteria.where("id").is(payload.getExecutionId());
		template.updateFirst(Query.query(criteria), update, AnalysisHistory.class);
	}

	@Override
	public void saveDescStats(DescriptiveAnalysis payload, JSONArray data) {
		DescStatsHistory entry = new DescStatsHistory();
		entry.setData(data);
		entry.setDataset(payload.getDataset());
		entry.setColumns(payload.getColumns());
		entry.setExecutionId(payload.getExecutionId());
		entry.setProfile(payload.getProfile());
		entry.setUsername(payload.getUsername());
		entry = descRepo.save(entry);
		Update update = new Update();
		update.addToSet("descriptiveStats", entry);
		Criteria criteria = Criteria.where("id").is(payload.getExecutionId());
		template.updateFirst(Query.query(criteria), update, AnalysisHistory.class);
		
	}

	@Override
	public void saveCorrelation(Correlation payload, JSONObject data) {
		CorrelationHistory entry = new CorrelationHistory();
		entry.setData(data);
		entry.setDataset(payload.getDataset());
		entry.setColumns(payload.getColumns());
		entry.setExecutionId(payload.getExecutionId());
		entry.setProfile(payload.getProfile());
		entry.setUsername(payload.getUsername());
		entry = correlationRepo.save(entry);
		Update update = new Update();
		update.addToSet("correlation", entry);
		Criteria criteria = Criteria.where("id").is(payload.getExecutionId());
		template.updateFirst(Query.query(criteria), update, AnalysisHistory.class);
		
	}

	@Override
	public void savePredictiveStats(PredictiveAnalysis payload, JSONArray data) {
		PredictiveStatsHistory entry = new PredictiveStatsHistory();
		entry.setData(data);
		entry.setDataset(payload.getDataset());
		entry.setColumns(payload.getColumns());
		entry.setExecutionId(payload.getExecutionId());
		entry.setProfile(payload.getProfile());
		entry.setUsername(payload.getUsername());
		entry.setThresholdPercentage(payload.getThresholdPercentage());
		entry.setValidationPercentage(payload.getValidationPercentage());
		entry = predStatRepo.save(entry);
		Update update = new Update();
		update.addToSet("predictiveStats", entry);
		Criteria criteria = Criteria.where("id").is(payload.getExecutionId());
		template.updateFirst(Query.query(criteria), update, AnalysisHistory.class);
	}

	@Override
	public void saveFeatureEngineering(FeatureEngineering payload, JSONObject data) {
		FeatureEngHistory entry = new FeatureEngHistory();
		entry.setData(data);
		entry.setDataset(payload.getDataset());
		entry.setColumns(payload.getColumns());
		entry.setExecutionId(payload.getExecutionId());
		entry.setProfile(payload.getProfile());
		entry.setUsername(payload.getUsername());
		entry.setThresholdPercentage(payload.getThresholdPercentage());
		entry.setValidationPercentage(payload.getValidationPercentage());
		entry.setTargetColumn(payload.getTargetColumn());
		entry = featureEngRepo.save(entry);
		Update update = new Update();
		update.addToSet("featureEngineering", entry);
		Criteria criteria = Criteria.where("id").is(payload.getExecutionId());
		template.updateFirst(Query.query(criteria), update, AnalysisHistory.class);
	}

	@Override
	public void saveTargetPrediction(PredictTarget payload, JSONObject data) {
		TargetPredictHistory entry = new TargetPredictHistory();
		entry.setData(data);
		entry.setDataset(payload.getDataset());
		entry.setColumns(payload.getColumns());
		entry.setExecutionId(payload.getExecutionId());
		entry.setProfile(payload.getProfile());
		entry.setUsername(payload.getUsername());
		entry.setThresholdPercentage(payload.getThresholdPercentage());
		entry.setValidationPercentage(payload.getValidationPercentage());
		entry.setTargetColumn(payload.getTargetColumn());
		entry = targetPredictRepo.save(entry);
		Update update = new Update();
		update.addToSet("targetPrediction", entry);
		Criteria criteria = Criteria.where("id").is(payload.getExecutionId());
		template.updateFirst(Query.query(criteria), update, AnalysisHistory.class);
	}

	@Override
	public void saveBinStats(BinStats payload, JSONObject data) {
		BinStatsHistory entry = new BinStatsHistory();
		entry.setData(data);
		entry.setDataset(payload.getDataset());
		entry.setColumns(payload.getColumns());
		entry.setExecutionId(payload.getExecutionId());
		entry.setProfile(payload.getProfile());
		entry.setUsername(payload.getUsername());
		entry = binStatsRepo.save(entry);
		Update update = new Update();
		update.addToSet("binStats", entry);
		Criteria criteria = Criteria.where("id").is(payload.getExecutionId());
		template.updateFirst(Query.query(criteria), update, AnalysisHistory.class);
	}

	@Override
	public void saveNullCount(NullCount payload, JSONObject data) {
		NullCountHistory entry = new NullCountHistory();
		entry.setData(data);
		entry.setDataset(payload.getDataset());
		entry.setColumn(payload.getColumn());
		entry.setExecutionId(payload.getExecutionId());
		entry.setProfile(payload.getProfile());
		entry.setUsername(payload.getUsername());
		entry = nullCountRepo.save(entry);
		Update update = new Update();
		update.addToSet("nullCount", entry);
		Criteria criteria = Criteria.where("id").is(payload.getExecutionId());
		template.updateFirst(Query.query(criteria), update, AnalysisHistory.class);	
	}

	@Override
	public void saveCompStats(CompStats payload, JSONArray data) {
		CompStatHistory entry = new CompStatHistory();
		entry.setData(data);
		entry.setDataset1(payload.getDataset1());
		entry.setDataset2(payload.getDataset2());
		entry.setColumn(payload.getColumn());
		entry.setExecutionId(payload.getExecutionId());
		entry.setUsername(payload.getUsername());
		entry = compStatsRepo.save(entry);
		Update update = new Update();
		update.addToSet("compStats", entry);
		Criteria criteria = Criteria.where("id").is(payload.getExecutionId());
		template.updateFirst(Query.query(criteria), update, AnalysisHistory.class);
	}
	
}
