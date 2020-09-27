package com.dxxt.im.websocket;

import com.dxxt.im.config.AppConfig;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.utils.ZKPaths;
import org.apache.zookeeper.CreateMode;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Slf4j
@Component
public class Zookeeper{

    private CuratorFramework zkClient;

    @PostConstruct
    public void started() throws Exception {
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(3000, Integer.MAX_VALUE);
        zkClient = CuratorFrameworkFactory.newClient(AppConfig.zookeeperUrl, retryPolicy);
        zkClient.start();

        String ipPort = AppConfig.imServerIp + ":" + AppConfig.imServerPort;
        String zkPath = ZKPaths.makePath(AppConfig.zookeeperPath, "im");

        zkClient.create().creatingParentsIfNeeded()
                .withMode(CreateMode.EPHEMERAL_SEQUENTIAL)
                .forPath(zkPath, ipPort.getBytes());

        log.info("zookeeper client write:{} in path: {} ", ipPort, zkPath);
    }

    @PreDestroy
    public void stop() {
        zkClient.close();
        log.info("zookeeper client stopped");
    }
}
