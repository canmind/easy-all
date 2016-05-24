package redis.test;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import junit.textui.TestRunner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import com.clarkware.junitperf.ConstantTimer;
import com.clarkware.junitperf.LoadTest;
import com.clarkware.junitperf.TimedTest;
import com.clarkware.junitperf.Timer;

public class JunitPerfTestCase extends TestCase { 
	private static Logger LOGGER = LoggerFactory.getLogger(JunitPerfTestCase.class);

    private static ApplicationContext context = null;
     
    public JunitPerfTestCase(String name) { 
        super(name); 
    }
    @Override  
    protected void setUp() throws Exception { 
    	LOGGER.debug("init {}",new Object[]{"setUp"});
        //context = new ClassPathXmlApplicationContext("applicationContext.xml");
    }
     
    @Override  
    protected void tearDown() throws Exception {
    }
 
    public void testOne() throws InterruptedException {  
        // UserServer us = (UserServce)context.getBean("");  
        Thread.sleep(3000);
        System.out.println("test running"); 
    }
         
    public static Test suite() {  
        Test testCase = new JunitPerfTestCase("testOne");  
        // 创建一个TestSuite  
        TestSuite suite = new TestSuite();  
        // 增加一个TimedTest
        // 默认有第三个参数：true，可以完整运行  
        suite.addTest(new TimedTest(testCase, 2000));
        // 第三个参数表示超过限定时间 不再继续执行，无法得到程序运行总时间  
        // suite.addTest( new TimedTest( testCase, 2000,false));  
        return suite; 
    }
     
    public static Test loadSuite() {  
    	LOGGER.info("start loadSuite");

        Test testCase = new JunitPerfTestCase("testOne");  
        TestSuite suite = new TestSuite();  
        int users = 10; // 执行用户数，模拟多线程并发  
        int iterations = 10;// 每用户数执行多少次  
        Timer timer = new ConstantTimer(1000);// 每次执行相差时间，即1秒执行一次  
        long maxElapsedTimeInMillis = 3000;// 总执行时间  
        // Test loadTest = new LoadTest(testCase, users, iterations,timer);  
        // Test timedTest = new TimedTest(loadTest,maxElapsedTimeInMillis);  
        suite.addTest(new TimedTest(new LoadTest(testCase, users, iterations, timer), maxElapsedTimeInMillis));  
        return suite; 
    }
 
    public static void main(String[] args) {  
    	LOGGER.debug("start {}",new Object[]{"main"});
        TestRunner.run(loadSuite()); 
    }
}
