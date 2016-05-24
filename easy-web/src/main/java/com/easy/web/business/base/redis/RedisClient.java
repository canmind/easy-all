package com.easy.web.business.base.redis;


import org.springframework.stereotype.Component;

import com.easy.common.core.log.Logger;
import com.easy.common.core.log.LoggerFactory;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.ArrayList;
import java.util.TreeMap;

/**
 * Created with IntelliJ IDEA.
 * User:bowen.ma
 * Date:14/10/29
 *
 * @Description To change this template use File | Settings | File Templates.
 */
@Component
public class RedisClient {


    private static Logger logger = LoggerFactory.getLogger(RedisClient.class);

    private ArrayList<JedisPool> jedisPools = new ArrayList<JedisPool>();

    private static int VIRTUAL_NUM = 4;

    //树形节点
    private TreeMap<Long, JedisPool> nodes;


    public RedisClient(JedisPoolConfig jedisPoolConfig, String addresses, String redisPorts) {
        if (jedisPoolConfig == null || addresses == null || redisPorts == null) {
            logger.error("redis 客户端初始化失败");
            throw new RuntimeException("redis 客户端初始化失败");
        }

        String[] ips = addresses.split(",");
        String[] ports = redisPorts.split(",");
        for (int i = 0; i < ips.length; i++) {
            JedisPool jedisPool = new JedisPool(jedisPoolConfig, ips[i], Integer.valueOf(ports[i]), 10000);
            jedisPools.add(jedisPool);
        }

        nodes = new TreeMap<Long, JedisPool>();

        for (int i = 0; i < jedisPools.size(); i++) {
            JedisPool jedisPool = jedisPools.get(i);
            for (int j = 0; j < VIRTUAL_NUM; j++) {
                nodes.put(ConsistencyHash.hash(ConsistencyHash.computeMd5("SHARD-" + i + "-NODE-" + j), j), jedisPool);
            }
        }
        ConsistencyHash.printMap(nodes);
        logger.info("redis 客户端初始化成功");
    }


    /**
     * 获取redis节点实例
     *
     * @param key
     * @return
     */
    public int getRealRedis(String key) {
        JedisPool jedisPool = ConsistencyHash.calHash(key, nodes);
        if (jedisPool == jedisPools.get(0)) {
            return RedisRole.REDIS_0;
        } else {
            return RedisRole.REDIS_1;
        }
    }


    /**
     * 获取hash后的jedis pool
     *
     * @param key
     * @return
     */
    public JedisPool getConsistencyHashJedisPool(String key) {
        JedisPool jedisPool = ConsistencyHash.calHash(key, nodes);
        return jedisPool;
    }

}
