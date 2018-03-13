package com.sap.iot.cd.roche.shipment.jco;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.sap.conn.jco.JCoDestination;
import com.sap.conn.jco.JCoDestinationManager;
import com.sap.conn.jco.JCoException;
import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.JCoParameterList;
import com.sap.conn.jco.JCoRecordField;
import com.sap.conn.jco.JCoRecordFieldIterator;
import com.sap.conn.jco.JCoRepository;
import com.sap.conn.jco.JCoTable;
import com.sap.iot.cd.roche.shipment.model.Link;

@Component
public class JCoFunction_ZGET_SSCC_LINK {
	private final String FUNCTION_NAME = "ZGET_SSCC_LINK";
	private final String ET_SENSOR_IDS = "ET_SENSOR_IDS";
	private final String IV_OBJCODE = "IV_OBJCODE";
	
	@Autowired
	Environment env;
	
	public JCoFunction_ZGET_SSCC_LINK(){
		super();
	}
	
	public ArrayList<Link> execute(String containerId) throws SAPConnectionException {
		try {
			// Get a Reference to the function
			JCoDestination destination = JCoDestinationManager.getDestination("TC3RFC");
			JCoRepository repo = destination.getRepository();
			JCoFunction function = repo.getFunction(FUNCTION_NAME);
			
			// Set the input criteria
			function.getImportParameterList().setValue(IV_OBJCODE, containerId);
			
			// Execute and return
			function.execute(destination);
			return this.extractOutput(containerId, function.getExportParameterList().getTable(ET_SENSOR_IDS));
			
		} catch (JCoException e) {
			throw new SAPConnectionException(e.getMessageText());
		}
	}
	
	private ArrayList<Link> extractOutput(String containerId, JCoTable table){
		ArrayList<Link> list = new ArrayList<Link>();
		
		if (table != null && table.getNumRows() > 0) {
			table.firstRow();
			JCoRecordFieldIterator iterator = table.getRecordFieldIterator();

			while (iterator != null) {
				Link link = new Link();
				link.setContainerId(containerId);
				while (iterator.hasNextField()) {
					JCoRecordField field = iterator.nextRecordField();
					switch (field.getName()) {
						case "SENSOR_ID":
							link.setLoggerId(field.getString());
							break;
					}
				}
				list.add(link);
				iterator = null;
				if (table.nextRow()) {
					iterator = table.getRecordFieldIterator();
				}
			}
		}
		return list;			
	}
}
