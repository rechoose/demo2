package cn.gw.demo2.mongo;

import cn.gw.demo2.lock.SingleTaskLock;
import cn.gw.demo2.utils.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.IndexOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.index.Index;
import org.springframework.data.mongodb.core.index.IndexField;
import org.springframework.data.mongodb.core.index.IndexInfo;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.util.*;

@Component
public class MongoAbout {

    public static final String SENSING_DETAIL = "sensing_detail";//感知设备
    public static final String CAMERA_DETAIL = "camera_detail";//摄像头
    public static final String POLICE_FACILITY_SIMPLE = "police_facility_simple";//all警用物资简单模型
    public static final String LOCATION = "location";//最新的点位

    private Logger logger = LoggerFactory.getLogger(MongoAbout.class);
    @Autowired
    private MongoTemplate mongoTemplate;

    @Transactional
    public void insertToMongo(Collection<? extends Object> batchToSave, String collectionName) {
        mongoTemplate.remove(new Query(Criteria.where("_id").exists(true)), collectionName);
        mongoTemplate.insert(batchToSave, collectionName);
    }

    @Transactional
    public void removeAndInsertToMongo(String removeKey, Collection<? extends Object> batchToSave, String collectionName) {
        Query query = new Query();
        Criteria criteria = Criteria.where("code").is(removeKey);
        query.addCriteria(criteria);
        mongoTemplate.findAllAndRemove(query, collectionName);
        mongoTemplate.insert(batchToSave, collectionName);
    }

    //    @PostConstruct
    public void init() {
        try {
            initColl();
        } catch (Exception e) {
            logger.error("初始化mongo异常:{}", e.getMessage());
        }
    }

    //初始化mongo
    private void initColl() throws Exception {//启动的时候初始化一次
        logger.info("初始化mongo集合,索引");

        //#######################################//
        boolean b = mongoTemplate.collectionExists(SENSING_DETAIL);
        if (!b) {
            logger.info("新建mongo集合" + SENSING_DETAIL);
            mongoTemplate.createCollection(SENSING_DETAIL);
        }
        ensureIndex(SENSING_DETAIL, "stationId", "deviceType");

        //#######################################//
        boolean f = mongoTemplate.collectionExists(POLICE_FACILITY_SIMPLE);
        if (!f) {
            logger.info("新建mongo集合" + POLICE_FACILITY_SIMPLE);
            mongoTemplate.createCollection(POLICE_FACILITY_SIMPLE);
        }
        ensureIndex(POLICE_FACILITY_SIMPLE, "stationId", "facilityId");

        //#######################################//
        boolean g = mongoTemplate.collectionExists(LOCATION);
        if (!g) {
            logger.info("新建mongo集合" + LOCATION);
            mongoTemplate.createCollection(LOCATION);
        }
        ensureIndex(LOCATION, "gpsId");

    }

    private void ensureIndex(String collectionName, String... keys) throws Exception {
        IndexOperations ops = mongoTemplate.indexOps(collectionName);
        Set<String> haskeys = new HashSet<>();
        List<IndexInfo> indexInfo = ops.getIndexInfo();
        for (IndexInfo info : indexInfo) {
            List<IndexField> indexFields = info.getIndexFields();
            for (IndexField indexField : indexFields) {
                haskeys.add(indexField.getKey());
            }
        }
        for (String key : keys) {
            if (!haskeys.contains(key)) {
                Index index = new Index(key, Sort.Direction.ASC);
                ops.ensureIndex(index);
            }
        }
    }


    @Scheduled(cron = "0 2 2 * * ?")
    @SingleTaskLock
    public void clearMongo() {
        LocalDateTime now = LocalDateTime.now();
        Query query = new Query();
        Criteria time = Criteria.where("time").lte(now);
        query.addCriteria(time);
        List<Object> allAndRemove = mongoTemplate.findAllAndRemove(query, MongoAbout.LOCATION);
        if (!CollectionUtils.isEmpty(allAndRemove)) {
            logger.info("清理缓存location:{}个", allAndRemove.size());
        }
    }


}

