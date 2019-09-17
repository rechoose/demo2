package cn.gw.demo2.service.impl;

import cn.gw.demo2.service.CacheIn;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

public abstract class MongoInImpl<Q, R> implements CacheIn<Q, R> {

    protected MongoTemplate mongoTemplate;

    protected String collectionName;//集合名称

    public MongoInImpl(MongoTemplate mongoTemplate, String collectionName) {
        this.mongoTemplate = mongoTemplate;
        this.collectionName = collectionName;
        init();
    }

    @Transactional
    protected void allDropAndInsert(Collection<R> insertObjects) {
        this.remove();
        this.insert(insertObjects);
    }

    @Transactional
    protected void removeAndinsert(Collection<R> insertObjects, Query query, Class<R> cz) {
        mongoTemplate.findAllAndRemove(query, cz, collectionName);
        this.insert(insertObjects);
    }

    private void init() {
        boolean b = mongoTemplate.collectionExists(collectionName);
        if (!b) {
            mongoTemplate.createCollection(collectionName);
        }
    }

    protected void remove() {
        mongoTemplate.createCollection(collectionName);
    }

    private void insert(Collection<R> insertObjects) {
        mongoTemplate.insert(insertObjects, collectionName);
    }


}
