package com.sap.iot.cd.roche.shipment.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sap.iot.cd.roche.shipment.jco.JCoFunction_ZGET_SSCC_LINK;
import com.sap.iot.cd.roche.shipment.jco.JCoFunction_ZSET_SSCC_LINK;
import com.sap.iot.cd.roche.shipment.jco.SAPConnectionException;
import com.sap.iot.cd.roche.shipment.model.Link;

@Service
public class LinkService {
	@Autowired
	private JCoFunction_ZSET_SSCC_LINK setLink;
	
	@Autowired
	private JCoFunction_ZGET_SSCC_LINK getLink;
	
	public void createLink(Link link) throws SAPConnectionException{
		setLink.execute(link);
	}
	
	public List<Link> queryLinks(String containerId) throws SAPConnectionException{
		return getLink.execute(containerId);
	}
}
