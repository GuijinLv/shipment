package com.sap.iot.cd.roche.shipment.controller;

import java.net.URI;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import javax.print.attribute.standard.PrinterLocation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.sap.iot.cd.roche.shipment.jco.SAPConnectionException;
import com.sap.iot.cd.roche.shipment.model.Link;
import com.sap.iot.cd.roche.shipment.service.LinkService;

@RestController
@RequestMapping(value="/api")
public class ShipmentController {
	@Autowired
	private LinkService service;
	
	@RequestMapping(value="/links", method=RequestMethod.POST)
	public ResponseEntity<Void> createLinks(@RequestBody Link link) throws SAPConnectionException {
		service.createLink(link);
		// Set the Location parameter so new object can be located
		final URI location = ServletUriComponentsBuilder.fromCurrentServletMapping().path("/api/links").query("containerId={id}").build()
				.expand(link.getContainerId()).toUri();

		final HttpHeaders headers = new HttpHeaders();
		headers.setLocation(location);

		return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
	}
	
	@RequestMapping(value="hdfs",method=RequestMethod.POST)
	public ResponseEntity<Void> postToHdfs(){
		try {
			Class.forName("org.apache.hive.jdbc.HiveDriver");
			String jdbcUrl = "jdbc:hive2://localhost:10000/i072179";
			try {
				Connection connection = DriverManager.getConnection(jdbcUrl, "i335519", "Macklin1");
				StringBuilder names = new StringBuilder();
				StringBuilder deStrings = new StringBuilder();
				try {
					Statement statement = connection.createStatement();
					boolean flag = statement.execute("insert into i072179.employee values (7, 'cloudtest', '111', '09262017')");
					ResultSet resultSet = statement.executeQuery("select eid,name,salary,destination from i072179.employee");
					while (resultSet.next()) {
						names.append(resultSet.getString(2)).append(" ");
						deStrings.append(resultSet.getString(4)).append(" ");
					}
					
					System.out.println(flag);
					System.out.println(names);
					System.out.println(deStrings);
				} catch (Exception e) {
					e.printStackTrace();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		

		return new ResponseEntity<Void>(new HttpHeaders(), HttpStatus.OK);
		
	}
	
	
	private String driverName = "org.apache.hive.jdbc.HiveDriver";
	@RequestMapping(value="hdfs",method=RequestMethod.GET)
	public void print() throws SQLException{
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
	
	
	@RequestMapping(value="/links", method=RequestMethod.GET)
	public ResponseEntity<List<Link>> queryLinks(@RequestParam("containerId") String containerId) throws SAPConnectionException{
		return new ResponseEntity<List<Link>>(service.queryLinks(containerId), HttpStatus.OK);
	}

	@ExceptionHandler(SAPConnectionException.class)
	@ResponseBody
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	public String handleSAPConnectionException(Exception ex) {
		return ex.getMessage();
	}
}
