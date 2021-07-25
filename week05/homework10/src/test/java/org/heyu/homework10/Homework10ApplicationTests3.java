package org.heyu.homework10;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.zaxxer.hikari.HikariDataSource;

import lombok.extern.slf4j.Slf4j;

/**
 * @author heyu
 * @date 2021/7/25
 */
@Slf4j
@SpringBootTest
public class Homework10ApplicationTests3 {

	@Autowired
	private HikariDataSource hikariDataSource;

	@Test
	void contextLoads() throws SQLException {
		Connection con = hikariDataSource.getConnection();

		con.setAutoCommit(false);
		// 新增
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = con.prepareStatement("INSERT INTO `homework10` VALUES ('1', '1')");
			int a = ps.executeUpdate();
			log.info(String.valueOf(a));

			// 删除
			ps = con.prepareStatement("DELETE FROM homework10 WHERE id = 1");
			int b = ps.executeUpdate();
			log.info(String.valueOf(b));

			// 修改
			ps = con.prepareStatement("UPDATE homework10 SET name = '20' WHERE id = 2");
			int c = ps.executeUpdate();
			log.info(String.valueOf(c));

			// 查询
			ps = con.prepareStatement("select * from homework10");
			rs = ps.executeQuery();
			while (rs.next()) {
				// 遍历该行数据
				int id = rs.getInt("id");
				String name = rs.getString("name");
				log.info("id:" + id + "\t name:" + name);
			}
			con.commit();
		} catch (Exception e) {
			e.printStackTrace();
			con.rollback();
		} finally {
			// 6、关闭资源
			if (rs != null){
				rs.close();
			}
			if (ps != null){
				ps.close();
			}
		}
		con.close();
	}

}
