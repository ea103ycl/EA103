package tools;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

//20201030 By YCL, 參考小吳老師JDBC套件idv.david.websocketchat.jedis內容
public class JedisPoolUtil {
	
	//(L)只需要一個連線池，所以宣告為satic
	private static JedisPool pool = null;

	private JedisPoolUtil() {
	}

	public static JedisPool getJedisPool() {
		if (pool == null) {
			synchronized (JedisPoolUtil.class) {
				 //(L)沒有連線池時才建立
				if (pool == null) {
					JedisPoolConfig config = new JedisPoolConfig();
					config.setMaxTotal(8);
					config.setMaxIdle(8);
					config.setMaxWaitMillis(10000); //(L)連線滿時等待10秒
					pool = new JedisPool(config, "localhost", 6379); //(L)sid=localhost、port=6379需對照redis內的設定
				}
			}
		}
		return pool;
	}

	// 可在ServletContextListener的contextDestroyed裡呼叫此方法註銷Redis連線池
	public static void shutdownJedisPool() {
		if (pool != null)
			pool.destroy();
	}
}
