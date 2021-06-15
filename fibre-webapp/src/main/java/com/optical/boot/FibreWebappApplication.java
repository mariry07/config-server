package com.optical.boot;

import com.optical.Service.impl.OpticalServiceImpl;
import com.optical.bean.SocketProperties;
import com.optical.component.NettyServer;
import com.optical.component.SocketListener;
import com.optical.component.SocketRunner;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Primary;

import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

@SpringBootApplication
//@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
@ComponentScan(basePackages = {
		"com.optical.controller",
		"com.optical.bean",
		"com.optical.common",
		"com.optical.component",
		"com.optical.Service",
		"com.optical.Service.impl",
})
@MapperScan(basePackages = "com.optical.mapper")
public class FibreWebappApplication {
	private static final Logger log = LoggerFactory.getLogger(FibreWebappApplication.class);

	public static void main(String[] args) {

		BlockingQueue<Integer> queue = new ArrayBlockingQueue<>(200);

		SpringApplication.run(FibreWebappApplication.class, args);


		//启动netty服务端(生产者)
		NettyServer nettyServer = new NettyServer(queue, 200);


		//需要内网地址
		//配置服务器 11002 端口
//        nettyServer.start(new InetSocketAddress("127.0.0.1", 11002));

		//配置服务器 11002 端口
		nettyServer.start(new InetSocketAddress("172.31.243.186", 11002));
//
        //业务模拟服务器 11003 端口
//		nettyServer.start(new InetSocketAddress("172.31.243.186", 11003));


//		ApplicationContext context = SpringApplication.run(FibreWebappApplication.class, args);
//		log.info("=======================here start FibreWebappApplication! ===========================");
//		context.getBean(SocketRunner.class).runrun();
	}



//
//	@Primary
//	@Bean
//	public OpticalServiceImpl createOpticalServiceImpl() {
//		OpticalServiceImpl opticalService = new OpticalServiceImpl();
//		return opticalService;
//	}
//
//	@Primary
//	@Bean
//	public SocketProperties createSocketProperties() {
//		SocketProperties socketProperties = new SocketProperties();
//		return socketProperties;
//	}
//
//	@Bean
//	public SocketRunner createSocketRunner() {
//		SocketRunner socketRunner = new SocketRunner();
//		return socketRunner;
//	}
//

}
