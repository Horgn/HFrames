package com.hframe.basic.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * Redis数据库工具类
 * 
 * @author Horgn黄小锤
 * @date 2019年1月11日 上午9:38:15
 * @version V1.0
 */
public class RedisUtils {

	private static JedisPool jedisPool = null;
	private static String redisConfigFile = "redis.properties";
	// 把redis连接对象放到本地线程中
	private static ThreadLocal<Jedis> local = new ThreadLocal<Jedis>();

	// 不允许通过new创建该类的实例
	private RedisUtils() {
	}

	/**
	 * 初始化链接池
	 * 
	 * @author Horgn黄小锤
	 * @date 2019年1月11日 上午9:40:09
	 */
	public static void initialPool() {
		
		try {
			Properties props = new Properties();
			// 加载连接池配置文件
			props.load(RedisUtils.class.getClassLoader().getResourceAsStream(redisConfigFile));
			// 创建jedis池配置实例
			JedisPoolConfig config = new JedisPoolConfig();
			
			// 设置池配置项值
			config.setMaxTotal(Integer.valueOf(props.getProperty("jedis.pool.maxActive")));
			config.setMaxIdle(Integer.valueOf(props.getProperty("jedis.pool.maxIdle")));
			config.setMaxWaitMillis(Long.valueOf(props.getProperty("jedis.pool.maxWait")));
			
			config.setTestOnBorrow(Boolean.valueOf(props.getProperty("jedis.pool.testOnBorrow")));
			config.setTestOnReturn(Boolean.valueOf(props.getProperty("jedis.pool.testOnReturn")));
			
			// 根据配置实例化jedis池
			jedisPool = new JedisPool(config, props.getProperty("redis.ip"),
					Integer.valueOf(props.getProperty("redis.port")),
					Integer.valueOf(props.getProperty("redis.timeout")), props.getProperty("redis.passWord"));
			
			System.out.println("线程池被成功初始化");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static Jedis getConn() {
		
		// Redis对象
		Jedis jedis = local.get();
		if (jedis == null) {
			if (jedisPool == null) {
				initialPool();
			}
			
			jedis = jedisPool.getResource();
			local.set(jedis);
		}
		return jedis;
	}

	/**
	 * 新版本用close归还连接
	 * 
	 * @author Horgn黄小锤
	 * @date 2019年1月11日 上午9:42:30
	 */
	public static void closeConn() {
		
		// 从本地线程中获取
		Jedis jedis = local.get();
		if (jedis != null) {
			jedis.close();
		}
		
		local.set(null);
	}

	/**
	 * 关闭池
	 * 
	 * @author Horgn黄小锤
	 * @date 2019年1月11日 上午9:42:49
	 */
	public static void closePool() {
		
		if (jedisPool != null) {
			jedisPool.close();
		}
	}

	public static void main(String[] args) {
		
		// 初始化连接池
		RedisUtils.initialPool();
		// 启动1000个线程
		
		for (int i = 0; i < 1000; i++) {
			ClientThread t = new ClientThread(i);
			t.start();
		}
	}

	
}

class ClientThread extends Thread {
	int i = 0;

	public ClientThread(int i) {
		this.i = i;
	}

	public void run() {
		
		Jedis jedis = RedisUtils.getConn();
		Date date = new Date();
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
		String time = sdf.format(date);
		jedis.set("key" + i, time);
		
		try {
			// 每次睡眠一个随机时间
			Thread.sleep((int) (Math.random() * 5000));
			String foo = jedis.get("key" + i);
			System.out.println("【输出>>>>】key:" + foo + " 第:" + i + "个线程");
			
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			RedisUtils.closeConn();
		}
	}
}
