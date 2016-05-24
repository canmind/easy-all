package com.easy.web.business.base.redis;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.SortedMap;
import java.util.TreeMap;

import redis.clients.jedis.JedisPool;

import com.easy.common.core.log.Logger;
import com.easy.common.core.log.LoggerFactory;

/**
 * IDEA
 * 一致性哈希算法
 *
 * @Description Created by bowen.ma on 14-8-14.
 */
public class ConsistencyHash {
    private static Logger logger = LoggerFactory.getLogger(ConsistencyHash.class);


    //算法值
    private static final int hash = 1;

    /**
     * 根据key的hash值取得服务器节点信息
     *
     * @param hash
     * @return
     */
    public static JedisPool getShardInfo(long hash, TreeMap<Long, JedisPool> nodes) {
        Long key = hash;
        SortedMap<Long, JedisPool> tailMap = nodes.tailMap(key);
        if (tailMap.isEmpty()) {
            key = nodes.firstKey();
        } else {
            key = tailMap.firstKey();
        }
        logger.error("分配对应的哈希值为:" + key);
//        System.out.println("分配对应的哈希值为:" + key);
        return nodes.get(key);
    }

    /**
     * 打印圆环节点数据
     */
    public static void printMap(TreeMap<Long, JedisPool> nodes) {
        logger.info("nodes : {} "+ nodes);
    }

    /**
     * 根据2^32把节点分布到圆环上面。
     *
     * @param digest
     * @param nTime
     * @return
     */
    public static long hash(byte[] digest, int nTime) {
        long rv = ((long) (digest[3 + nTime * 4] & 0xFF) << 24)
                | ((long) (digest[2 + nTime * 4] & 0xFF) << 16)
                | ((long) (digest[1 + nTime * 4] & 0xFF) << 8)
                | (digest[0 + nTime * 4] & 0xFF);

        return rv & 0xffffffffL; /* Truncate to 32-bits */
    }

    /**
     * Get the md5 of the given key.
     * 计算MD5值
     */
    public static byte[] computeMd5(String k) {
        MessageDigest md5;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("MD5 not supported", e);
        }
        md5.reset();
        byte[] keyBytes;
        try {
            keyBytes = k.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("Unknown string :" + k, e);
        }

        md5.update(keyBytes);
        return md5.digest();
    }


    /**
     * 调用一致性哈希方法
     *
     * @param key  传入key值 进行计算哈希
     * @param nodes
     * @return
     */
    public static JedisPool calHash(String key, TreeMap<Long, JedisPool> nodes) {
        return getShardInfo(hash(computeMd5(key), hash), nodes);
    }

}