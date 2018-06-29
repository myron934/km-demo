package com.netease.km;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableDiscoveryClient
@EnableTransactionManagement
@ComponentScan("com.netease.km")
public class AsEnterpriseApplication {

	public static void main(String[] args) {
		SpringApplication.run(AsEnterpriseApplication.class, args);
	}
}
