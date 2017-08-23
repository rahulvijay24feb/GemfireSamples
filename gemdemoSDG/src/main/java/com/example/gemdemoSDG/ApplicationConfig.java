package com.example.gemdemoSDG;

import java.net.URI;
import java.util.Properties;

import org.apache.geode.cache.client.ClientCache;
import org.apache.geode.cache.client.ClientCacheFactory;
import org.apache.geode.cache.client.ClientRegionShortcut;
import org.apache.geode.pdx.ReflectionBasedAutoSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.Cloud;
import org.springframework.cloud.CloudFactory;
import org.springframework.cloud.config.java.AbstractCloudConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.gemfire.client.ClientRegionFactoryBean;
import org.springframework.data.gemfire.repository.config.EnableGemfireRepositories;



@Configuration
@EnableGemfireRepositories
public class ApplicationConfig extends AbstractCloudConfig {

    private static final String SECURITY_CLIENT = "security-client-auth-init";
    private static final String SECURITY_USERNAME = "security-username";
    private static final String SECURITY_PASSWORD = "security-password";

    @Bean
    public ClientCache gemfireCache() {
        Cloud cloud = new CloudFactory().getCloud();
        ServiceInfo serviceInfo = (ServiceInfo) cloud.getServiceInfos().get(0);

        ClientCacheFactory factory = new ClientCacheFactory();
        for (URI locator : serviceInfo.getLocators()) {
            factory.addPoolLocator(locator.getHost(), locator.getPort());
        }

        factory.set(SECURITY_CLIENT, "io.pivotal.UserAuthInitialize.create");
        factory.set(SECURITY_USERNAME, serviceInfo.getUsername());
        factory.set(SECURITY_PASSWORD, serviceInfo.getPassword());
        factory.setPdxSerializer(new ReflectionBasedAutoSerializer("io.pivotal.Pizza"));

        return factory.create();
    }

    @Bean(name = "Greeting")
    public ClientRegionFactoryBean<String, Greeting> greetingRegion(@Autowired ClientCache gemfireCache) {
        ClientRegionFactoryBean<String, Greeting> greetingRegion = new ClientRegionFactoryBean<>();

        greetingRegion.setCache(gemfireCache);
        greetingRegion.setClose(false);
        greetingRegion.setShortcut(ClientRegionShortcut.PROXY);
        greetingRegion.setLookupEnabled(true);
        return greetingRegion;
    }
}