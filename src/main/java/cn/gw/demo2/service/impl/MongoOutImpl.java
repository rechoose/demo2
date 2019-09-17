package cn.gw.demo2.service.impl;

import cn.gw.demo2.service.CacheOut;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;

import java.util.Collection;
import java.util.List;

public abstract class MongoOutImpl<Q, R> implements CacheOut<Q, R> {

    protected MongoTemplate mongoTemplate;

    protected String collectionName;//集合名称

    protected Collection<R> result;//查询到的结果

    public MongoOutImpl(MongoTemplate mongoTemplate, String collectionName) {
        this.mongoTemplate = mongoTemplate;
        this.collectionName = collectionName;
    }

    protected Collection<R> list(Query query, Class<R> cz) {
        List<R> rs = mongoTemplate.find(query, cz, collectionName);
        result = rs;
        return rs;
    }

    @Override
    public Collection<R> getResult() {
        return result;
    }
}
