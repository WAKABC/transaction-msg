package com.wak.transactionmsg;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@MapperScan(basePackages = "com.wak.transactionmsg.mapper")
@EnableScheduling
public class TransactionMsgApplication {
	public static void main(String[] args) {
		SpringApplication.run(TransactionMsgApplication.class, args);
	}
}
