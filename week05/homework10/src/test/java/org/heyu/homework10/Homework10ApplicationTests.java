package org.heyu.homework10;

import java.sql.*;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import lombok.extern.slf4j.Slf4j;

/**
 * @author heyu
 * @date 2021/7/25
 */
@Slf4j
@SpringBootTest
public class Homework10ApplicationTests {

	@Test
	void contextLoads() throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3307/week05?useSSL=false", "root",
				"mn814561293");

		// 新增
		PreparedStatement ps = con.prepareStatement("INSERT INTO `homework10` VALUES ('1', '1')");
		int a = ps.executeUpdate();
		log.info(String.valueOf(a));

		// 删除
		ps = con.prepareStatement("DELETE FROM homework10 WHERE id = 1");
		int b = ps.executeUpdate();
		log.info(String.valueOf(b));

		// 修改
		ps = con.prepareStatement("UPDATE homework10 SET name = '10' WHERE id = 2");
		int c = ps.executeUpdate();
		log.info(String.valueOf(c));

		// 查询
		ps = con.prepareStatement("select * from homework10");
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			// 遍历该行数据
			int id = rs.getInt("id");
			String name = rs.getString("name");
			log.info("id:" + id + "\t name:" + name);
		}

		// 6、关闭资源
		rs.close();
		ps.close();
		con.close();
	}

}
