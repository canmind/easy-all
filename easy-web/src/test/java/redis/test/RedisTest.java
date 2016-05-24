package redis.test;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;





import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import com.easy.web.business.base.redis.RedisClient;
import com.easy.common.core.log.Logger;
import com.easy.common.core.log.LoggerFactory;

/**
 * Created with IntelliJ IDEA.
 * User:bowen.ma
 * Date:14/10/29
 *
 * @Description To change this template use File | Settings | File Templates.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/spring-test-business.xml"})
public class RedisTest {

    private static Logger logger = LoggerFactory.getLogger(RedisTest.class);

    @Autowired
    private RedisClient redisClient;


    @Test
    public void test() {
        JedisPool jedisPool = redisClient.getConsistencyHashJedisPool("mabowen");
        Jedis jedis = jedisPool.getResource();
        jedis.set("mabowen", "mmmmmmmm");
        jedisPool.returnResource(jedis);


        jedisPool = redisClient.getConsistencyHashJedisPool("mabowen");
        jedis = jedisPool.getResource();
        System.out.println(jedis.get("mabowen"));
        logger.error("test log4j mail {}14776405@qq.com");
    }

    @Test
    public void loadTokenTest() {
        ThreadPoolExecutor consumeExecutor = new ThreadPoolExecutor(40, 40 + 10, 5, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(40 + 10), new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                Thread myThread = new Thread(r);
                myThread.setName("TT");
                return myThread;
            }
        }, new ThreadPoolExecutor.CallerRunsPolicy());
        while (true) {

            consumeExecutor.execute(new Runnable() {

                @Override
                public void run() {

                    JedisPool jedisPool = redisClient.getConsistencyHashJedisPool("mabowen");
                    Jedis jedis = jedisPool.getResource();
                    jedis.set("mabowen", "mmmmmmmm");
                    jedisPool.returnResource(jedis);


                    jedisPool = redisClient.getConsistencyHashJedisPool("mabowen");
                    jedis = jedisPool.getResource();
                    System.out.println(jedis.get("mabowen"));
                    jedisPool.returnResource(jedis);

                }

            });
        }
    }


    @Test
    public void testLog(){
        logger.info("1111111111");
    }

}
