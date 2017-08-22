package com.example.gemdemo;

import org.apache.geode.cache.Region;
import org.apache.geode.cache.client.ClientCache;
import org.apache.geode.cache.client.ClientRegionFactory;
import org.apache.geode.cache.client.ClientRegionShortcut;
import org.apache.geode.pdx.ReflectionBasedAutoSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.Cloud;
import org.springframework.cloud.CloudFactory;
import org.springframework.cloud.service.ServiceConnectorConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import io.pivotal.spring.cloud.service.gemfire.GemfireServiceConnectorConfig;

@Configuration
@Profile("cloud")
public class PCCClientCloudConfig {
	public ServiceConnectorConfig createGemfireConnectorConfig() {

		GemfireServiceConnectorConfig gemfireConfig = new GemfireServiceConnectorConfig();
		gemfireConfig.setPoolSubscriptionEnabled(true);
		//ArrayList<String> patterns = new ArrayList<String>();
		//patterns.add("com.example.gemdemo.Greeting");
		ReflectionBasedAutoSerializer customPdx = new ReflectionBasedAutoSerializer(".*");
		//customPdx.setSerializableClasses(patterns);
		gemfireConfig.setPdxSerializer(customPdx);
		gemfireConfig.setPdxReadSerialized(false);

		return gemfireConfig;
	}

	public ClientCache getGemfireClientCache() throws Exception {

		Cloud cloud = new CloudFactory().getCloud();
		ClientCache clientCache = cloud.getSingletonServiceConnector(ClientCache.class, createGemfireConnectorConfig());

		return clientCache;
	}

	@Bean
	public Region<String, Greeting> greetRegion(@Autowired ClientCache clientCache) {

		ClientRegionFactory<String, Greeting> activeItemRegionFactory = clientCache
				.createClientRegionFactory(ClientRegionShortcut.PROXY);
		Region<String, Greeting> greetRegion = activeItemRegionFactory.create("Hello");

		return greetRegion;
	}
}
