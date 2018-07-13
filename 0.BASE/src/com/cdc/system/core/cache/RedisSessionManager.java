package com.cdc.system.core.cache;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.trustel.util.JavaSerializer;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Protocol;

import java.util.List;
import java.util.Map;

/**
 * Created by nike on 2014/7/10.
 */
public class RedisSessionManager {

    private final Log log = LogFactory.getLog("redis-oprea");

    //redis server host
    protected String host = "localhost";

    //redis server port
    protected int port = 6379;

    //redis server database
    protected int database = 0;

    protected String password;

    protected int timeOut = Protocol.DEFAULT_TIMEOUT;

    protected JedisPool connectionPool;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public int getDatabase() {
        return database;
    }

    public void setDatabase(int database) {
        this.database = database;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getTimeOut() {
        return timeOut;
    }

    public void setTimeOut(int timeOut) {
        this.timeOut = timeOut;
    }

    private void initializeDatabaseConnection(){
        try {
            JedisPoolConfig config = new JedisPoolConfig();
            //控制一个pool最多有多少个状态为idle(空闲的)的jedis实例。
            config.setMaxIdle(5);
            config.setMaxActive(100000);
            //在borrow一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的；
            config.setTestOnBorrow(true);
            connectionPool = new JedisPool(config, getHost(), getPort(), getTimeOut(), getPassword());
            log.info("初始化redis连接池成功");
        } catch (Exception e) {
            e.printStackTrace();
            log.error("初始化redis连接池失败",e);
        }

    }

    private void destoryPool(){
        try {
            if (connectionPool != null) {
                connectionPool.destroy();
                log.info("销毁redis连接池成功");
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("销毁redis连接池失败",e);
        }
    }

    private void returnResource(Jedis jedis){
        if (jedis != null)
            connectionPool.returnResource(jedis);
    }

    private Jedis getResource(){
        Jedis jedis = connectionPool.getResource();
        if (getDatabase() != 0) {
            jedis.select(getDatabase());
        }
        return jedis;
    }

    public List getList(String key){

        log.info(String.format("获取[%s]的值",key));
        List result = null;
        Jedis jedis = null;

        try {

            if (key != null) {
                jedis = getResource();
                byte[] resultData = jedis.get(key.getBytes());
                result = (List) JavaSerializer.deserializeInto(resultData);
            }
        } catch (Exception e) {
            if (jedis != null)
                connectionPool.returnBrokenResource(jedis);
        } finally {
            returnResource(jedis);
        }

        return result;
    }

    public String getString(String key) {
        log.info(String.format("获取[%s]的值",key));
        String value = null;

        Jedis jedis = null;
        try {
            if (key != null) {
                jedis = getResource();
                value = jedis.get(key);
            }
        } catch (Exception e) {
            //释放redis对象
            if (jedis != null)
                connectionPool.returnBrokenResource(jedis);
            e.printStackTrace();
        } finally {
            //返还到连接池
            returnResource(jedis);
        }

        return value;
    }

    public Map getMap(String key){
        log.info(String.format("获取[%s]的值",key));
        Map result = null;
        Jedis jedis = null;

        try {

            if (key != null) {
                jedis = getResource();
                byte[] resultData = jedis.get(key.getBytes());
                result = (Map) JavaSerializer.deserializeInto(resultData);
            }
        } catch (Exception e) {
            if (jedis != null)
                connectionPool.returnBrokenResource(jedis);
        } finally {
            returnResource(jedis);
        }

        return result;
    }

    public String putString(String key,String value) {
        log.info(String.format("存入[%s]的值",key));
        String returnCode = null;
        Jedis jedis = null;
        try {
            if (key != null) {
                jedis = getResource();
                returnCode = jedis.set(key,value);
            }
        } catch (Exception e) {
            //释放redis对象
            if (jedis != null)
                connectionPool.returnBrokenResource(jedis);
            e.printStackTrace();
        } finally {
            //返还到连接池
            returnResource(jedis);
        }

        return returnCode;
    }

    public String putList(String key ,List dataList) {
        log.info(String.format("存入[%s]的值",key));
        String returnCode = null;
        Jedis jedis = null;
        try {
            if (key != null) {
                jedis = getResource();
                returnCode = jedis.set(key.getBytes(),JavaSerializer.serializeFrom(dataList));
            }
        } catch (Exception e) {
            //释放redis对象
            if (jedis != null)
                connectionPool.returnBrokenResource(jedis);
            e.printStackTrace();
        } finally {
            //返还到连接池
            returnResource(jedis);
        }

        return returnCode;
    }

    public String putMap(String key ,Map dataMap) {
        log.info(String.format("存入[%s]的值",key));
        String returnCode = null;
        Jedis jedis = null;
        try {
            if (key != null) {
                jedis = getResource();
                returnCode = jedis.set(key.getBytes(),JavaSerializer.serializeFrom(dataMap));
            }
        } catch (Exception e) {
            //释放redis对象
            if (jedis != null)
                connectionPool.returnBrokenResource(jedis);
            e.printStackTrace();
        } finally {
            //返还到连接池
            returnResource(jedis);
        }

        return returnCode;
    }

}
