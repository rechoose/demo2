package cn.gw.demo2.service;

import java.util.Collection;

/**
 * 按条件从缓存中获取数据
 * Q:查询条件
 * R:结果
 */
public interface CacheOut<Q, R> {

    Collection<R> list(Q q);

    Collection<R> getResult();
}
