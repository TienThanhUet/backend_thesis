package com.satdio.dataIntegration;

import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.node.NodeBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.net.InetAddress;

@SpringBootApplication(scanBasePackages = {"com.stadio"})
@EnableTransactionManagement
@ComponentScan(basePackages = {"com.stadio"})
@EnableMongoRepositories(basePackages = {"com.stadio.model.repository"})
@EnableElasticsearchRepositories(basePackages = {"com.stadio.model.esRepository"})
public class DataIntegrationApplication {

	public static void main(String[] args) {
		SpringApplication.run(DataIntegrationApplication.class, args);
	}

	@Bean
	public NodeBuilder nodeBuilder() {
		return new NodeBuilder();
	}

	@Value("${elasticsearch.host}")
	private String EsHost;

	@Value("${elasticsearch.port}")
	private int EsPort;

	@Value("${elasticsearch.clustername}")
	private String EsClusterName;

	@Bean
	public Client client() throws Exception {

		Settings esSettings = Settings.settingsBuilder()
				.put("cluster.name", EsClusterName)
				.build();

		return TransportClient.builder()
				.settings(esSettings)
				.build()
				.addTransportAddress(
						new InetSocketTransportAddress(InetAddress.getByName(EsHost), EsPort));
	}

	@Bean
	public ElasticsearchOperations elasticsearchTemplate() throws Exception {
		return new ElasticsearchTemplate(client());
	}
}
