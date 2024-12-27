package gateway.route;

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.util.List;
import java.util.Random;

@Service
public final class SubscriptionPuller {
    private final URI NO_INSTANCE_EXIT = URI.create("http://localhost:8080/sorry");
    private final DiscoveryClient discoveryClient;
    private final Random random = new Random();

    /**
     * 注入Nacos的DiscoverClient
     *
     * @param discoveryClient -提供发现服务
     *                        //@param loadBalancerClient -提供负载均衡
     */
    public SubscriptionPuller(DiscoveryClient discoveryClient) {
        this.discoveryClient = discoveryClient;
        //this.loadBalancerClient = loadBalancerClient;
    }

    /**
     * 获得存活服务的uri
     *
     * @param serviceName
     * @return
     */
    public URI getUrl(String serviceName) {
        List<ServiceInstance> services = discoveryClient.getInstances(serviceName);
        services.forEach(serviceInstance -> System.out.println(serviceInstance.getHost() + serviceInstance.getPort()));
        //loadBalancerClient.choose(serviceName)
        return !services.isEmpty() ?
                services.get(random.nextInt(services.size())).getUri() : NO_INSTANCE_EXIT;
    }


}
