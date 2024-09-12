package tw.pers.allen.pawposter;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

@SpringBootApplication
public class PawposterVueBackendApplication {

	public static void main(String[] args) throws IOException, SQLException {
		// 從 application.properties 讀取參數
		Resource resource = new ClassPathResource("application.properties");
		Properties properties = PropertiesLoaderUtils.loadProperties(resource);

		String username = properties.getProperty("spring.datasource.username");
		String password = properties.getProperty("spring.datasource.password");

		Connection conn = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;trustServerCertificate=true",
				username, password);

		// 預先建立 database
		Statement statement = conn.createStatement();
		statement.execute("IF DB_ID('paw_poster') IS NULL CREATE DATABASE paw_poster");
		conn.close();
		
		// 開始執行整個 springboot 框架
		SpringApplication.run(PawposterVueBackendApplication.class, args);
	}

}
