package pe.com.nextel.provisioning.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import pe.com.nextel.provisioning.model.vo.MensajeVO;

public class XmlFile {

	public XmlFile(){}

	Document dom ;
	List 	myMens;

	private void parseXmlFile(){
		//get the factory
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

		try {

			//Using factory get an instance of document builder
			DocumentBuilder db = dbf.newDocumentBuilder();
			//parse using builder to get DOM representation of the XML file
			dom = db.parse(new File("D:\\karnish\\eMensaje.xml").toURI().toString());

		}catch(ParserConfigurationException pce) {
			pce.printStackTrace();
		}catch(SAXException se) {
			se.printStackTrace();
		}catch(IOException ioe) {
			ioe.printStackTrace();
		}
	}

	private void parseDocument(){
		//get the root element
		Element docEle = dom.getDocumentElement();
		myMens = new ArrayList() ;
		//get a nodelist of  elements
		NodeList nl = docEle.getElementsByTagName("ADD_USR");
		int talla  = nl.getLength() ;
		if(nl != null && talla > 0) {
			for(int i = 0 ; i < talla ;i++) {

				//get the employee element
				Element el = (Element)nl.item(i);
				//get the Employee object
			
				MensajeVO e = getMensaje(el);
				
				//add it to list
				myMens.add(e);
			}
		}
	}

	//private Employee getEmployee(Element empEl) {

	private MensajeVO getMensaje(Element menEl) {

		//for each <mensajeVO> element get text values of 

		java.lang.String uSERNAME   = getTextValue(menEl,"USERNAME") ;
		java.lang.String pASSWORD   = getTextValue(menEl,"PASSWORD");
		java.lang.String number     = getTextValue(menEl,"Number");
		java.lang.String numberType = getTextValue(menEl,"NumberType");
		java.lang.String iMSI = getTextValue(menEl,"IMSI") ;
		java.lang.String HLRAddress = getTextValue(menEl,"HLRAddress") ;
		java.lang.String newRoute = getTextValue(menEl,"NewRoute") ;
		java.lang.String operatorCode = getTextValue(menEl,"OperatorCode") ;
		java.lang.String locationCode = getTextValue(menEl,"LocationCode") ;
		java.lang.String interconnectCode = getTextValue(menEl,"InterconnectCode");
		java.lang.String cPcode = getTextValue(menEl,"CPcode") ;
		java.lang.String cNL = getTextValue(menEl,"CNL") ;
		java.lang.String recipientID = getTextValue(menEl,"RecipientID");
		java.lang.String donorID = getTextValue(menEl,"DonorID") ; 

		//Create a new MensajeVO with the value read from the xml nodes

		MensajeVO bean = new MensajeVO();

		bean.setCNL(cNL) ;
		bean.setCPcode(cPcode) ;
		bean.setDonorID(donorID)  ;
		bean.setHLRAddress(HLRAddress) ;
		bean.setIMSI(iMSI) ;
		bean.setInterconnectCode(interconnectCode) ;
		bean.setLocationCode(locationCode) ;
		bean.setNewRoute(newRoute) ;
		bean.setNumber(number) ;
		bean.setNumberType(numberType) ;
		bean.setOperatorCode(operatorCode) ;
		bean.setPASSWORD(pASSWORD) ;
		bean.setRecipientID(recipientID);
		bean.setUSERNAME(uSERNAME);


		return bean;
	}

	private String getTextValue(Element ele, String tagName) {
		String textVal = "";
		NodeList nl = ele.getElementsByTagName(tagName);
		int talla = nl.getLength() ;
		try {
			if( nl != null && talla > 0) {
				Element el = (Element)nl.item(0);
				 if ( el.getFirstChild().getNodeValue() != null )  textVal = el.getFirstChild().getNodeValue()  ;
			}
		}catch (Exception ee){ }
		
		return textVal ;
	}

	private int getIntValue(Element ele, String tagName) {
		//in production application you would catch the exception
		return Integer.parseInt(getTextValue(ele,tagName));
	}

	private void printData(){

		System.out.println("Nro of Mensajes '" + myMens.size() + "'.");
		Iterator it = myMens.iterator();
		while(it.hasNext()) {
			MensajeVO men =	(MensajeVO)it.next()  ;
			System.out.println("------> " + men.getUSERNAME() );
			System.out.println("------> " + men.getCNL() );
			System.out.println("------> " + men.getCPcode() );
		}
	}

	public static void main(String[] args){
		//create an instance
		XmlFile dpe = new XmlFile();

		//call run example
		dpe.runExample();
		
	
	}

	public void runExample() {

		//parse the xml file and get the dom object
		parseXmlFile();

		//get each employee element and create a Employee object
		parseDocument();

		//Iterate through the list and print the data
		printData();

	}




}
