package com.sap.iot.cd.roche.shipment.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class test {
	private static String driverName = "org.apache.hive.jdbc.HiveDriver";

    public static void main(String[] args) throws SQLException {
        try {
            Class.forName(driverName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.exit(1);
        }

        Connection con = DriverManager.getConnection(
                "jdbc:hive2://mo-7ca891ad1.mo.sap.corp:10000/default", "hadoop", "123456");
        Statement stmt = con.createStatement();

        String sql = "select * from t_hive";
        ResultSet res = stmt.executeQuery(sql);
        while (res.next()) {
            System.out.println(String.valueOf(res.getInt(1)) + "\t"
                    + res.getString(2)+ "\t"
                    + res.getString(3));
        }
        res.close();
        stmt.close();
        con.close();
    }

}	
