package com.sap.iot.cd.roche.shipment.jco;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.sap.conn.jco.JCoDestination;
import com.sap.conn.jco.JCoDestinationManager;
import com.sap.conn.jco.JCoException;
import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.JCoRepository;
import com.sap.iot.cd.roche.shipment.model.Link;

@Component
public class JCoFunction_ZSET_SSCC_LINK {
	private final String FUNCTION_NAME = "ZSET_SSCC_LINK";
	private final String IV_SENSOR_ID = "IV_SENSOR_ID";
	private final String IV_OBJCODE = "IV_OBJCODE";
	
	@Autowired
	Environment env;
	
	public JCoFunction_ZSET_SSCC_LINK(){
		super();
	}
	
	public void execute(Link link) throws SAPConnectionException {
		try {
			// Get a Reference to the function
			JCoDestination destination = JCoDestinationManager.getDestination("TC3RFC");
			JCoRepository repo = destination.getRepository();
			JCoFunction function = repo.getFunction(FUNCTION_NAME);
			
			// Set the input criteria
			function.getImportParameterList().setValue(IV_SENSOR_ID, link.getLoggerId());
			function.getImportParameterList().setValue(IV_OBJCODE, link.getContainerId());
			
			// Execute and return
			function.execute(destination);
			
		} catch (JCoException e) {
			throw new SAPConnectionException(e.getMessageText());
		}
	}
}
