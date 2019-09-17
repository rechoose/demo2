package cn.gw.demo2.service;

import java.util.Collection;

/**
 * 全量数据缓存
 * Q:查询条件
 * R:结果
 */
public interface CacheIn<Q, R> {

    Collection<R> cacheAll(Q q) ;
}
