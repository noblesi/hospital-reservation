package com.hospital.common.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import kr.co.sist.chipher.DataDecryption;

public class GetKey {

	public static String getKey() {
		String key = "";
		String enKey = "";

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = DBConnection.getConnection();

			StringBuilder query = new StringBuilder();
			query//
					.append("	select 	secret_key	")//
					.append("	from	server_property	") //
					.append("	where	type = '1'	");

			pstmt = con.prepareStatement(query.toString());

			rs = pstmt.executeQuery();

			if (rs.next()) {
				enKey = rs.getString("secret_key");
			}

			if (enKey == null || "".equals(enKey)) {
				System.err.println("key 못 찾음");
				return key;
			}

			key = decrypt(enKey);

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBConnection.close(rs, pstmt, con);
		}

		return key;
	}

	private static String decrypt(String enKey) throws Exception {
		String key = "";
		String deKey = "";

		if (enKey == null || "".equals(enKey)) {
			System.err.println("DB에 저장된 키가 존재하지 않습니다.");
			return key;
		}

		File file = new File("C:/qoeryqoeryqoe.txt");

		if (!file.exists()) {
			System.err.println("복호화 키 파일이 존재하지 않습니다.");
			return null;
		}

		FileInputStream fis = null;
		InputStreamReader isr = null;
		BufferedReader br = null;

		try {
			fis = new FileInputStream(file);
			isr = new InputStreamReader(fis);
			br = new BufferedReader(isr);

			deKey = br.readLine();

			DataDecryption dd = new DataDecryption(deKey);

			key = dd.decrypt(enKey);

		} finally {
			if (br != null) {
				br.close();
			}
			if (isr != null) {
				isr.close();
			}
			if (fis != null) {
				fis.close();
			}
		}

		return key;
	}

}
