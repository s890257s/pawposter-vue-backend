package tw.pers.allen.pawposter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

@SpringBootApplication
public class PawposterVueBackendApplication {

	public static void main(String[] args) throws Exception {

		createDatabase();

		SpringApplication.run(PawposterVueBackendApplication.class, args);
	}

	private static void createDatabase() throws Exception {
		// 從 properties 取得連線資訊
		Properties properties = PropertiesLoaderUtils.loadProperties(new ClassPathResource("application.properties"));

		String jdbcUrl = properties.getProperty("spring.datasource.url");
		String username = properties.getProperty("spring.datasource.username");
		String password = properties.getProperty("spring.datasource.password");

		// 使用正則表達式取得 database 名稱
		Matcher matcher = Pattern.compile("database=([^;]+)").matcher(jdbcUrl);

		// 找不到就不建立了
		if (!matcher.find()) {
			throw new Exception("找不到 database 設定，請檢查 application.properties");
		}

		String databaseName = matcher.group(1);
		String jdbcUrlWithoutDatabase = jdbcUrl.replaceAll(";database=" + databaseName, "");

		Connection conn = DriverManager.getConnection(jdbcUrlWithoutDatabase, username, password);

		// 預先建立 database
		Statement statement = conn.createStatement();
		statement.execute("IF DB_ID('%s') IS NULL CREATE DATABASE %s".formatted(databaseName, databaseName));
		conn.close();
	}

}
