package cn.gw.demo2.redissub;

import com.google.common.collect.Sets;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisClusterConnection;
import org.springframework.data.redis.connection.RedisClusterNode;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;
import redis.clients.util.JedisClusterCRC16;

import javax.annotation.PostConstruct;
import java.util.*;

/**
 * redis集群消息订阅
 */
@Component
public class RedisClusterMessageSubscriber implements RedisMessageSubscriber {

    private static final Logger logger = LoggerFactory.getLogger(RedisClusterMessageSubscriber.class);

    protected final String KEY_EVENT_EXPIRATION_PATTERN = "__keyevent@*__:expired";

    @Autowired
    private RedisTemplate redisTemplate;

    private TreeMap<Long, String> slotHostMap = new TreeMap<>();

    private Set<RedisListener> hasBeListener = Sets.newConcurrentHashSet();

    @PostConstruct
    private void init() {
       /* //获得集群各节点地址和端口
        Set<HostAndPort> hostAndPorts = new HashSet<>();
        RedisConnectionFactory connectionFactory = redisTemplate.getConnectionFactory();
        RedisClusterConnection clusterConnection = connectionFactory.getClusterConnection();
        Iterable<RedisClusterNode> redisClusterNodes = clusterConnection.clusterGetNodes();
        for (RedisClusterNode redisClusterNode : redisClusterNodes) {
            String host = redisClusterNode.getHost();
            Integer port = redisClusterNode.getPort();
            hostAndPorts.add(new HostAndPort(host, port));
        }
        //获取solt和地址端口的关系
        JedisCluster jedisCluster = new JedisCluster(hostAndPorts);
        Map<String, JedisPool> nodeMap = jedisCluster.getClusterNodes();
        for (String hostAndPort : nodeMap.keySet()) {
            slotHostMap.putAll(getSlotHostMap(hostAndPort));
        }*/
    }

    private TreeMap<Long, String> getSlotHostMap(String anyHostAndPortStr) {
        TreeMap<Long, String> tree = new TreeMap<Long, String>();
        String parts[] = anyHostAndPortStr.split(":");
        HostAndPort anyHostAndPort = new HostAndPort(parts[0], Integer.parseInt(parts[1]));
        try {
            Jedis jedis = new Jedis(anyHostAndPort.getHost(), anyHostAndPort.getPort());
            List<Object> list = jedis.clusterSlots();
            for (Object object : list) {
                List<Object> list1 = (List<Object>) object;
                List<Object> master = (List<Object>) list1.get(2);
                String hostAndPort = new String((byte[]) master.get(0)) + ":" + master.get(1);
                tree.put((Long) list1.get(0), hostAndPort);
                tree.put((Long) list1.get(1), hostAndPort);
            }
            jedis.close();
        } catch (Exception e) {
        }
        return tree;
    }

    private HostAndPort getHostAndPort(String slotName) {//获取槽所在的节点
        // 监听key所属slot的节点
        int slot = JedisClusterCRC16.getSlot(slotName);
        //map的key实际存储的是各节点的最小slot和最大slot，需要判断实际存值的slot在哪个节点的区间内
        long lastSlot = slotHostMap.ceilingKey(Long.valueOf(slot));
        String hostAndPort = slotHostMap.get(lastSlot);
        if (StringUtils.isEmpty(hostAndPort)) {
            logger.error("未获取到solt对应的redis节点");
            return null;
        }
        String[] hostPort = hostAndPort.split(":");
        if (2 > hostPort.length || StringUtils.isEmpty(hostPort[0]) || StringUtils.isEmpty(hostPort[1])) {
            logger.error("未获取到solt对应的redis节点");
            return null;
        }
        String host = hostPort[0];
        int port = Integer.valueOf(hostPort[1]);
        return new HostAndPort(host, port);
    }

    private String[] patterns() {
        return new String[]{KEY_EVENT_EXPIRATION_PATTERN};
    }

    @Override
    public void subscribe(RedisListener listener) {
        if (listener == null || hasBeListener.contains(listener)) {
            logger.info("this node has been listen");
            return;
        }
        hasBeListener.add(listener);
        HostAndPort hostAndPort = getHostAndPort(listener.getKeySlot());
        if (hostAndPort == null) {
            return;
        }
        String host = hostAndPort.getHost();
        int port = hostAndPort.getPort();
        new Thread(() -> {
            Jedis jedis = new Jedis(host, port);
            try {
                jedis.configSet("notify-keyspace-events", "Ex");
                jedis.psubscribe(listener, patterns());
            } catch (Exception ex) {
                logger.error(String.format("jedis订阅异常,host:%s,port:%s", host, port));
                ex.printStackTrace();
            }
        }, String.format("subscribe Thread--%s:%s", host, port)).start();
    }
}
