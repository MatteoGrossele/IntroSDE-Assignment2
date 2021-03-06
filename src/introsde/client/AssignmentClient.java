package introsde.client;

import java.net.URI;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.Response;
import javax.ws.rs.client.Entity;

import java.io.*;


import org.glassfish.jersey.client.ClientConfig;

public class AssignmentClient {
	//URI of the web server
	static String URI = "https://matteogrosseleintrosde.herokuapp.com/introsde-assignment2/";

	//Names of the logs files
	static String XML_LOGS = "client_xml.txt";
	static String JSON_LOGS= "client_json.txt";


	public static void main(String[] args) throws IOException {
		//Connection to the web service
		ClientConfig clientConfig = new ClientConfig();
		Client client = ClientBuilder.newClient(clientConfig);
		WebTarget service = client.target(getBaseURI());
		//Variable needed to parse the response
		Response response;
		int status;
		String body = "";
		String result = "";
		String message = "";
		//Open logs file for XMl and Json to write response from server
        BufferedWriter writerXML = new BufferedWriter(new FileWriter(new File(XML_LOGS)));
        BufferedWriter writerJson = new BufferedWriter(new FileWriter(new File(JSON_LOGS)));
        //Path for the current request
        String requestPath = "";
        String putPerson = "";
        String postPerson = "";
        //Id of the first person in the aplpications
        int firstPersonId = 1;
		//Ids of the first and last person inside the people list
		int [] storedId = {1,4};
		//Id of the person created at step 4
		String createdPersonIdXML = "999";
		String createdPersonIdJSon = "998";
		//List of the measure types of the application, set at step 6
		String [] measureTypes;
		//measureType and person id of the step 7
		String measureType = "";
		int savedPersonId = 0;
		int savedMid = 0;

		//Print uri of the server
		System.out.println("SERVER  URI = " + URI);

		/*
		*											STEP 1
		*/

        writerXML.write("------------------------------ STEP 1 XML --------------------------\n");
        System.out.print("------------------------------ STEP 1 XML --------------------------\n");
        requestPath = "person";

		response = service.path(requestPath).request().accept(MediaType.APPLICATION_XML).get();
		status = response.getStatus();
		body = response.readEntity(String.class);

		//Condition for the request to be successfull
		if(countOccourences(body,"<person>") > 3)
			result = "OK";
		else
			result = "ERROR";

		//Get message to print out 
		message = getMessageInfo(1, "GET",URI + requestPath,"APPLICATION/XML", "APPLICATION/XML", result, status, body);

		//Print result from the request
		writerXML.write(message);
		System.out.print(message);

		writerJson.write("------------------------------ STEP 1 JSON --------------------------\n");
        System.out.print("------------------------------ STEP 1 JSON --------------------------\n");
        requestPath = "person";

		response = service.path(requestPath).request().accept(MediaType.APPLICATION_JSON).get();
		status = response.getStatus();
		body = response.readEntity(String.class);

		//Condition for the request to be successfull
		if(countOccourences(body,"\"firstname\":") > 3)
			result = "OK";
		else
			result = "ERROR";

		//Set id of the first and last person
		firstPersonId = 1;storedId[0] = 1;storedId[1] = 4;

		//Get message to print out 
		message = getMessageInfo(1, "GET",URI + requestPath,"APPLICATION/JSON", "APPLICATION/JSON", result, status, body);

		//Print result from the request
		writerJson.write(message);
		System.out.print(message);

		/*
		*											STEP 2
		*/

		writerXML.write("------------------------------ STEP 2 XML --------------------------\n");
        System.out.print("------------------------------ STEP 2 XML --------------------------\n");
        requestPath = "person/" + firstPersonId;

		response = service.path(requestPath).request().accept(MediaType.APPLICATION_XML).get();
		status = response.getStatus();
		body = response.readEntity(String.class);

		//Condition for the request to be successfull
		if(status == 202 || status == 200)
			result = "OK";
		else
			result = "ERROR";

		//Get message to print out 
		message = getMessageInfo(2, "GET",URI + requestPath,"APPLICATION/XML", "APPLICATION/XML", result, status, body);

		//Print result from the request
		writerXML.write(message);
		System.out.print(message);

		writerJson.write("------------------------------ STEP 2 JSON --------------------------\n");
        System.out.print("------------------------------ STEP 2 JSON --------------------------\n");
		requestPath = "person/" + firstPersonId;

		response = service.path(requestPath).request().accept(MediaType.APPLICATION_JSON).get();
		status = response.getStatus();
		body = response.readEntity(String.class);

		//Condition for the request to be successfull
		if(status == 202 || status == 200)
			result = "OK";
		else
			result = "ERROR";

		//Get message to print out 
		message = getMessageInfo(2, "GET",URI + requestPath,"APPLICATION/JSON", "APPLICATION/JSON", result, status, body);

		//Print result from the request
		writerJson.write(message);
		System.out.print(message);

		/*
		*											STEP 3
		*/

		writerXML.write("------------------------------ STEP 3 XML --------------------------\n");
        System.out.print("------------------------------ STEP 3 XML --------------------------\n");
        String xmlTag = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>";
        requestPath = "person/" + firstPersonId;
        putPerson =  "<person>"
						+"<birthdate>24/06/1993</birthdate>"
						+"<firstname>Matteo</firstname>"
						+"<idPerson>" + firstPersonId +"</idPerson>"
						+"<lastname>Grossele</lastname>"
					+"</person>";

		response = service.path(requestPath).request().accept(MediaType.APPLICATION_XML).put(Entity.xml(putPerson));
		status = response.getStatus();
		body = response.readEntity(String.class);

		//Condition for the request to be successfull
		if(status == 201 || status == 200)
			if(countOccourences(body,"<lastname>Grossele</lastname>") == 1)
				result = "OK";
			else
				result = "ERROR";
		else
			result = "ERROR";

		//Get message to print out 
		message = getMessageInfo(3, "PUT",URI + requestPath,"APPLICATION/XML", "APPLICATION/XML", result, status, body);

		//Print result from the request
		writerXML.write(message);
		System.out.print(message);

		writerJson.write("------------------------------ STEP 3 JSON --------------------------\n");
        System.out.print("------------------------------ STEP 3 JSON --------------------------\n");
		requestPath = "person/"+firstPersonId;
		putPerson =  "{"
					+"\"idPerson\": " + firstPersonId + ","
					+"\"lastname\": \"Grossele\","
					+"\"firstname\": \"Matteo\","
					+"\"birthdate\": \"24/06/1993\""
					+"}";

		response = service.path(requestPath).request().accept(MediaType.APPLICATION_JSON).put(Entity.json(putPerson));
		status = response.getStatus();
		body = response.readEntity(String.class);

		//Condition for the request to be successfull
		if(status == 201 || status == 200)
			if(countOccourences(body,"\"Grossele\"") == 1)
				result = "OK";
			else
				result = "ERROR";
		else
			result = "ERROR";

		//Get message to print out 
		message = getMessageInfo(3, "PUT",URI + requestPath,"APPLICATION/JSON", "APPLICATION/JSON", result, status, body);

		//Print result from the request
		writerJson.write(message);
		System.out.print(message);


		/*
		*											STEP 4
		*/

		writerXML.write("------------------------------ STEP 4 XML --------------------------\n");
        System.out.print("------------------------------ STEP 4 XML --------------------------\n");
        requestPath = "person";
        postPerson = "<person>"
						+"<birthdate>01/01/1945</birthdate>"
						+"<firstname>Chuck</firstname>"
						+"<idPerson>" + createdPersonIdXML +"</idPerson>"
						+"<lastname>Norris</lastname>"
						+"<measures>"
							+"<Measure>"
								+"<date>02/09/1978</date>"
								+"<idMeasure>88</idMeasure>"
								+"<idPerson>"+ createdPersonIdXML +"</idPerson>"
								+"<mid>1</mid>"
								+"<type>weight</type>"
								+"<value>78.9</value>"
							+"</Measure>"
							+"<Measure>"
								+"<date>02/09/1978</date>"
								+"<idMeasure>89</idMeasure>"
								+"<idPerson>"+ createdPersonIdXML +"</idPerson>"
								+"<mid>2</mid>"
								+"<type>height</type>"
								+"<value>172.0</value>"
							+"</Measure>"
						+"</measures>"
					+"</person>";

		response = service.path(requestPath).request().accept(MediaType.APPLICATION_XML).post(Entity.xml(postPerson));
		status = response.getStatus();
		body = response.readEntity(String.class);

		//Condition for the request to be successfull
		if(status == 202 || status == 200 || status == 201)
			result = "OK";
		else
			result = "ERROR";

		//Get message to print out 
		message = getMessageInfo(4, "POST",URI + requestPath,"APPLICATION/XML", "APPLICATION/XML", result, status, xmlTag + postPerson);

		//Print result from the request
		writerXML.write(message);
		System.out.print(message);

		writerJson.write("------------------------------ STEP 4 JSON --------------------------\n");
        System.out.print("------------------------------ STEP 4 JSON --------------------------\n");
		requestPath = "person";
		postPerson = "{"
						+"\"idPerson\" : "+createdPersonIdJSon+","
						+" \"firstname\"     : \"Chuck\","
						+"\"lastname\"      : \"Norris\","
						+"\"birthdate\"     : \"01/01/1945\","
						+"\"measures\" :" 
						+"["
							+"{"
								+"\"idMeasure\": 98,"
								+"\"idPerson\": "+createdPersonIdJSon+","
								+"\"mid\": 1,"
								+"\"type\": \"weight\","
								+"\"value\": \"78.9\","
								+"\"date\": \"02/09/1978\""
							+"},"
							+"{"
								+"\"idMeasure\": 99,"
								+"\"idPerson\": "+createdPersonIdJSon+","
								+"\"mid\": 1,"
								+"\"type\": \"height\","
								+"\"value\": \"172.0\","
								+"\"date\": \"02/09/1978\""
							+"}"
						+"]"
					+"}";


		response = service.path(requestPath).request().accept(MediaType.APPLICATION_JSON).post(Entity.json(postPerson));
		status = response.getStatus();
		body = response.readEntity(String.class);

		//Condition for the request to be successfull
		if(status == 202 || status == 200 || status == 201)
			result = "OK";
		else
			result = "ERROR";

		//Get message to print out 
		message = getMessageInfo(4, "POST",URI + requestPath,"APPLICATION/JSON", "APPLICATION/JSON", result, status, body);

		//Print result from the request
		writerJson.write(message);
		System.out.print(message);

		/*
		*											STEP 5
		*/

		writerXML.write("------------------------------ STEP 5 XML --------------------------\n");
        System.out.print("------------------------------ STEP 5 XML --------------------------\n");
        requestPath = "person/" + createdPersonIdXML;

        //First i delete the person
		response = service.path(requestPath).request().accept(MediaType.APPLICATION_XML).delete();
		status = response.getStatus();
		body = response.readEntity(String.class);

		//Condition for the request to be successfull
		if(status == 204 || status == 200)
			result = "OK";
		else
			result = "ERROR";

		//Get message to print out 
		message = getMessageInfo(5, "DELETE",URI + requestPath,"APPLICATION/XML", "APPLICATION/XML", result, status, body);

		//Print result from the request
		writerXML.write(message);
		System.out.print(message);
		//I request that person to verify it has been deleted
		response = service.path(requestPath).request().accept(MediaType.APPLICATION_XML).get();
		status = response.getStatus();
		body = response.readEntity(String.class);

		//Condition for the request to be successfull
		if(status == 404)
			result = "OK";
		else
			result = "ERROR";

		//Get message to print out 
		message = getMessageInfo(6, "GET",URI + requestPath,"APPLICATION/XML", "APPLICATION/XML", result, status, body);

		//Print result from the request
		writerXML.write(message);
		System.out.print(message);

		writerJson.write("------------------------------ STEP 5 JSON --------------------------\n");
        System.out.print("------------------------------ STEP 5 JSON --------------------------\n");
		requestPath = "person/" + createdPersonIdJSon;
		
        //First i delete the person
		response = service.path(requestPath).request().accept(MediaType.APPLICATION_JSON).delete();
		status = response.getStatus();
		body = response.readEntity(String.class);

		//Condition for the request to be successfull
		if(status == 204 || status == 200)
			result = "OK";
		else
			result = "ERROR";

		//Get message to print out 
		message = getMessageInfo(5, "DELETE",URI + requestPath,"APPLICATION/JSON", "APPLICATION/JSON", result, status, body);

		//Print result from the request
		writerJson.write(message);
		System.out.print(message);

		//I request that person to verify it has been deleted
		response = service.path(requestPath).request().accept(MediaType.APPLICATION_JSON).get();
		status = response.getStatus();
		body = response.readEntity(String.class);

		//Condition for the request to be successfull
		if(status == 404)
			result = "OK";
		else
			result = "ERROR";

		//Get message to print out 
		message = getMessageInfo(6, "GET",URI + requestPath,"APPLICATION/JSON", "APPLICATION/JSON", result, status, body);

		//Print result from the request
		writerJson.write(message);
		System.out.print(message);

		/*
		*											STEP 6
		*/

		writerXML.write("------------------------------ STEP 6 XML --------------------------\n");
        System.out.print("------------------------------ STEP 6 XML --------------------------\n");
        requestPath = "measureTypes";

		response = service.path(requestPath).request().accept(MediaType.APPLICATION_XML).get();
		status = response.getStatus();
		body = response.readEntity(String.class);

		//Condition for the request to be successfull
		if(countOccourences(body,"measureType") > 2)
			result = "OK";
		else
			result = "ERROR";

		//Get message to print out 
		message = getMessageInfo(9, "GET",URI + requestPath,"APPLICATION/XML", "APPLICATION/XML", result, status, body);

		//Print result from the request
		writerXML.write(message);
		System.out.print(message);

		writerJson.write("------------------------------ STEP 6 JSON --------------------------\n");
        System.out.print("------------------------------ STEP 6 JSON --------------------------\n");
		requestPath = "measureTypes";
		
		response = service.path(requestPath).request().accept(MediaType.APPLICATION_JSON).get();
		status = response.getStatus();
		body = response.readEntity(String.class);

		//Condition for the request to be successfull
		if(countOccourences(body,",") > 1)
			result = "OK";
		else
			result = "ERROR";

		//Ottengo le misure supportate dal sistema
		String measureTypesString = body.substring(body.indexOf('[') + 1, body.indexOf(']'));
		measureTypesString = measureTypesString.replace("\"","");
		measureTypes = measureTypesString.split(",");
		for(String s : measureTypes)
			s.trim();

		//Get message to print out 
		message = getMessageInfo(9, "GET",URI + requestPath,"APPLICATION/JSON", "APPLICATION/JSON", result, status, body);

		//Print result from the request
		writerJson.write(message);
		System.out.print(message);

		/*
		*											STEP 7
		*/

		writerXML.write("------------------------------ STEP 7 XML --------------------------\n");
        System.out.print("------------------------------ STEP 7 XML --------------------------\n");
        
        result = "ERROR";

        for(int id = 0; id < storedId.length && result == "ERROR";id++)
        	for(int idType = 0; idType < measureTypes.length && result == "ERROR";idType++)
        	{
        		requestPath = "person/" +storedId[id] + "/" + measureTypes[idType];
        		response = service.path(requestPath).request().accept(MediaType.APPLICATION_XML).get();
				status = response.getStatus();
				body = response.readEntity(String.class);
				//Condition for the request to be successfull
				if(status == 202 || status == 200 || status == 201)
					result = "OK";
        	}

		//Get message to print out 
		message = getMessageInfo(6, "GET",URI + requestPath,"APPLICATION/XML", "APPLICATION/XML", result, status, body);

		//Print result from the request
		writerXML.write(message);
		System.out.print(message);

		writerJson.write("------------------------------ STEP 7 JSON --------------------------\n");
        System.out.print("------------------------------ STEP 7 JSON --------------------------\n");

        result = "ERROR";

		for(int id = 0; id < storedId.length && result == "ERROR";id++)
        	for(int idType = 0; idType < measureTypes.length && result == "ERROR";idType++)
        	{
        		requestPath = "person/" +storedId[id] + "/" + measureTypes[idType];
        		response = service.path(requestPath).request().accept(MediaType.APPLICATION_JSON).get();
				status = response.getStatus();
				body = response.readEntity(String.class);
				//Condition for the request to be successfull
				if(status == 202 || status == 200 || status == 201) {
					result = "OK";
					savedPersonId = storedId[id];
					savedMid = 2;
				}
        	}
		
		//Get message to print out 
		message = getMessageInfo(6, "GET",URI + requestPath,"APPLICATION/JSON", "APPLICATION/JSON", result, status, body);

		//Print result from the request
		writerJson.write(message);
		System.out.print(message);

		/*
		*											STEP 8
		*/

		writerXML.write("------------------------------ STEP 8 XML --------------------------\n");
        System.out.print("------------------------------ STEP 8 XML --------------------------\n");
        
        measureType = "height";

		requestPath = "person/" + savedPersonId + "/" + measureType + "/" + savedMid;

		response = service.path(requestPath).request().accept(MediaType.APPLICATION_XML).get();
		status = response.getStatus();
		body = response.readEntity(String.class);
		//Condition for the request to be successfull
		if(status == 202 || status == 200 || status == 201)
			result = "OK";
		else
			result = "ERROR";

		//Get message to print out 
		message = getMessageInfo(7, "GET",URI + requestPath,"APPLICATION/XML", "APPLICATION/XML", result, status, body);

		//Print result from the request
		writerXML.write(message);
		System.out.print(message);

		writerJson.write("------------------------------ STEP 8 JSON --------------------------\n");
        System.out.print("------------------------------ STEP 8 JSON --------------------------\n");

		requestPath = "person/" + savedPersonId + "/" + measureType + "/" + savedMid;

		response = service.path(requestPath).request().accept(MediaType.APPLICATION_JSON).get();
		status = response.getStatus();
		body = response.readEntity(String.class);
		//Condition for the request to be successfull
		if(status == 202 || status == 200 || status == 201)
			result = "OK";
		else
			result = "ERROR";
		
		//Get message to print out 
		message = getMessageInfo(7, "GET",URI + requestPath,"APPLICATION/JSON", "APPLICATION/JSON", result, status, body);

		//Print result from the request
		writerJson.write(message);
		System.out.print(message);

		/*
		*											STEP 9
		*/

		writerXML.write("------------------------------ STEP 9 XML --------------------------\n");
        System.out.print("------------------------------ STEP 9 XML --------------------------\n");

        measureType = "height";
        
		requestPath = "person/" +firstPersonId + "/" + measureType;

		response = service.path(requestPath).request().accept(MediaType.APPLICATION_XML).get();
		status = response.getStatus();
		body = response.readEntity(String.class);
		//Condition for the request to be successfull
		if(status == 202 || status == 200 || status == 201)
			result = "OK";
		else
			result = "ERROR";

		//Get message to print out 
		message = getMessageInfo(6, "GET",URI + requestPath,"APPLICATION/XML", "APPLICATION/XML", result, status, body);

		//Print result from the request
		writerXML.write(message);
		System.out.print(message);

		int measureCount = countOccourences(body,"<Measure>");

		String postMeasure = "<Measure>"
								+"<date>09/12/2011</date>"
								+"<idMeasure>65</idMeasure>"
								+"<idPerson>1</idPerson>"
								+"<mid>8</mid>"
								+"<type>height</type>"
								+"<value>12.0</value>"
							+"</Measure>";

		response = service.path(requestPath).request().accept(MediaType.APPLICATION_XML).post(Entity.xml(postMeasure));
		status = response.getStatus();
		body = response.readEntity(String.class);
		//Condition for the request to be successfull
		if(status == 202 || status == 200 || status == 201)
			result = "OK";
		else
			result = "ERROR";

		//Get message to print out 
		message = getMessageInfo(8, "POST",URI + requestPath,"APPLICATION/XML", "APPLICATION/XML", result, status, body);

		//Print result from the request
		writerXML.write(message);
		System.out.print(message);

		response = service.path(requestPath).request().accept(MediaType.APPLICATION_XML).get();
		status = response.getStatus();
		body = response.readEntity(String.class);
		//Condition for the request to be successfull
		if((status == 202 || status == 200 || status == 201) && measureCount +1 == countOccourences(body,"<Measure>"))
			result = "OK";
		else
			result = "ERROR";

		//Get message to print out 
		message = getMessageInfo(6, "GET",URI + requestPath,"APPLICATION/XML", "APPLICATION/XML", result, status, body);

		//Print result from the request
		writerXML.write(message);
		System.out.print(message);


		writerJson.write("------------------------------ STEP 9 JSON --------------------------\n");
        System.out.print("------------------------------ STEP 9 JSON --------------------------\n");

		requestPath = "person/" +firstPersonId + "/" + measureType;

		response = service.path(requestPath).request().accept(MediaType.APPLICATION_JSON).get();
		status = response.getStatus();
		body = response.readEntity(String.class);
		//Condition for the request to be successfull
		if((status == 202 || status == 200 || status == 201) )
			result = "OK";
		else
			result = "ERROR";
		
		//Get message to print out 
		message = getMessageInfo(6, "GET",URI + requestPath,"APPLICATION/JSON", "APPLICATION/JSON", result, status, body);

		//Print result from the request
		writerJson.write(message);
		System.out.print(message);

		measureCount = countOccourences(body,"},");

		postMeasure ="{"
						+"\"date\": \"09/12/2011\","
						+"\"idMeasure\": 66,"
						+"\"idPerson\": "+firstPersonId +","
						+"\"mid\": 8,"
						+"\"type\": \""+measureType+"\","
						+"\"value\": \"12.0\""
					+"}";

		response = service.path(requestPath).request().accept(MediaType.APPLICATION_JSON).post(Entity.json(postMeasure));
		status = response.getStatus();
		body = response.readEntity(String.class);
		//Condition for the request to be successfull
		if((status == 202 || status == 200 || status == 201) )
			result = "OK";
		else
			result = "ERROR";
		
		//Get message to print out 
		message = getMessageInfo(8, "POST",URI + requestPath,"APPLICATION/JSON", "APPLICATION/JSON", result, status, body);

		//Print result from the request
		writerJson.write(message);
		System.out.print(message);

		response = service.path(requestPath).request().accept(MediaType.APPLICATION_JSON).get();
		status = response.getStatus();
		body = response.readEntity(String.class);
		//Condition for the request to be successfull
		if((status == 202 || status == 200 || status == 201)  && measureCount +1 == countOccourences(body,"},"))
			result = "OK";
		else
			result = "ERROR";
		
		//Get message to print out 
		message = getMessageInfo(6, "GET",URI + requestPath,"APPLICATION/JSON", "APPLICATION/JSON", result, status, body);

		//Print result from the request
		writerJson.write(message);
		System.out.print(message);

		

		/*
		*											STEP 10
		*/

		writerXML.write("------------------------------ STEP 10 XML --------------------------\n");
        System.out.print("------------------------------ STEP 10 XML --------------------------\n");
        
        measureType = "height";
        savedMid = 65;

		requestPath = "person/" +savedPersonId + "/" + measureType;

		String putMeasure = "<Measure>"
								+"<date>09/12/2011</date>"
								+"<idMeasure>"+savedMid+"</idMeasure>"
								+"<idPerson>"+firstPersonId +"</idPerson>"
								+"<mid>8</mid>"
								+"<type>height</type>"
								+"<value>90</value>"
							+"</Measure>";

		response = service.path(requestPath + "/"+savedMid).request().accept(MediaType.APPLICATION_XML).put(Entity.xml(putMeasure));
		status = response.getStatus();
		body = response.readEntity(String.class);
		//Condition for the request to be successfull
		if(status == 202 || status == 200 || status == 201)
			result = "OK";
		else
			result = "ERROR";

		//Get message to print out 
		message = getMessageInfo(10, "PUT",URI + requestPath+ "/"+savedMid,"APPLICATION/XML", "APPLICATION/XML", result, status, body);

		//Print result from the request
		writerXML.write(message);
		System.out.print(message);

		response = service.path(requestPath).request().accept(MediaType.APPLICATION_XML).get();
		status = response.getStatus();
		body = response.readEntity(String.class);
		//Condition for the request to be successfull
		if((status == 202 || status == 200 || status == 201) && 1 <= countOccourences(body,"<value>90</value>"))
			result = "OK";
		else
			result = "ERROR";

		//Get message to print out 
		message = getMessageInfo(6, "GET",URI + requestPath,"APPLICATION/XML", "APPLICATION/XML", result, status, body);

		//Print result from the request
		writerXML.write(message);
		System.out.print(message);

		service.path(requestPath + "/"+savedMid).request().accept(MediaType.APPLICATION_XML).delete();


		writerJson.write("------------------------------ STEP 10 JSON --------------------------\n");
        System.out.print("------------------------------ STEP 10 JSON --------------------------\n");

        savedMid = 66;
		requestPath = "person/" +savedPersonId + "/" + measureType ;

		putMeasure ="{"
						+"\"date\": \"09/12/2011\","
						+"\"idMeasure\": "+savedMid+","
						+"\"idPerson\": "+firstPersonId +","
						+"\"mid\": 8,"
						+"\"type\": \""+measureType+"\","
						+"\"value\": \"90\""
					+"}";

		response = service.path(requestPath + "/"+savedMid).request().accept(MediaType.APPLICATION_JSON).put(Entity.json(putMeasure));
		status = response.getStatus();
		body = response.readEntity(String.class);
		//Condition for the request to be successfull
		if(status == 202 || status == 200 || status == 201)
			result = "OK";
		else
			result = "ERROR";
		
		//Get message to print out 
		message = getMessageInfo(10, "PUT",URI + requestPath + "/"+savedMid,"APPLICATION/JSON", "APPLICATION/JSON", result, status, body);

		//Print result from the request
		writerJson.write(message);
		System.out.print(message);

		response = service.path(requestPath).request().accept(MediaType.APPLICATION_JSON).get();
		status = response.getStatus();
		body = response.readEntity(String.class);
		//Condition for the request to be successfull
		if((status == 202 || status == 200 || status == 201)  && 1 <= countOccourences(body,"\"value\":\"90\""))
			result = "OK";
		else
			result = "ERROR";
		
		//Get message to print out 
		message = getMessageInfo(6, "GET",URI + requestPath,"APPLICATION/JSON", "APPLICATION/JSON", result, status, body);

		//Print result from the request
		writerJson.write(message);
		System.out.print(message);

		service.path(requestPath + "/"+savedMid).request().accept(MediaType.APPLICATION_JSON).delete();

		/*
		*											STEP 11
		*/

		writerXML.write("------------------------------ STEP 11 XML --------------------------\n");
        System.out.print("------------------------------ STEP 11 XML --------------------------\n");
        
        measureType = "height";

		requestPath = "person/" + firstPersonId + "/" + measureType + "?before=02/09/1979&after=02/09/1977";

		response = service.path("person/" +savedPersonId + "/" + measureType).queryParam("before","02/09/1979").queryParam("after","02/09/1977").request().accept(MediaType.APPLICATION_XML).get();
		status = response.getStatus();
		body = response.readEntity(String.class);
		//Condition for the request to be successfull
		if((status == 202 || status == 200 || status == 201) && countOccourences(body,"<Measure>") >= 1)
			result = "OK";
		else
			result = "ERROR";

		//Get message to print out 
		message = getMessageInfo(11, "GET",URI + requestPath,"APPLICATION/XML", "APPLICATION/XML", result, status, body);

		//Print result from the request
		writerXML.write(message);
		System.out.print(message);

		writerJson.write("------------------------------ STEP 11 JSON --------------------------\n");
        System.out.print("------------------------------ STEP 11 JSON --------------------------\n");

		requestPath = "person/" +savedPersonId + "/" + measureType + "?before=02/09/1979&after=02/09/1977";

		response = service.path("person/" +savedPersonId + "/" + measureType).queryParam("before","02/09/1979").queryParam("after","02/09/1977").request().accept(MediaType.APPLICATION_JSON).get();
		status = response.getStatus();
		body = response.readEntity(String.class);
		//Condition for the request to be successfull
		if((status == 202 || status == 200 || status == 201) && countOccourences(body,"}") >= 1)
			result = "OK";
		else
			result = "ERROR";
		
		//Get message to print out 
		message = getMessageInfo(11, "GET",URI + requestPath,"APPLICATION/JSON", "APPLICATION/JSON", result, status, body);

		//Print result from the request
		writerJson.write(message);
		System.out.print(message);

		/*
		*											STEP 12
		*/

		writerXML.write("------------------------------ STEP 12 XML --------------------------\n");
        System.out.print("------------------------------ STEP 12 XML --------------------------\n");
        
		requestPath = "person?measureType=height&max=100&min=70";

		response = service.path("person").queryParam("measureType","height").queryParam("max","100").queryParam("min","70").request().accept(MediaType.APPLICATION_XML).get();
		status = response.getStatus();
		body = response.readEntity(String.class);
		//Condition for the request to be successfull
		if((status == 202 || status == 200 || status == 201) && countOccourences(body,"<person>") >= 1)
			result = "OK";
		else
			result = "ERROR";

		//Get message to print out 
		message = getMessageInfo(12, "GET",URI + requestPath,"APPLICATION/XML", "APPLICATION/XML", result, status, body);

		//Print result from the request
		writerXML.write(message);
		System.out.print(message);

		writerJson.write("------------------------------ STEP 12 JSON --------------------------\n");
        System.out.print("------------------------------ STEP 12 JSON --------------------------\n");

		requestPath = "person?measureType=height&max=100&min=70";

		response = service.path("person").queryParam("measureType","height").queryParam("max","100").queryParam("min","70").request().accept(MediaType.APPLICATION_JSON).get();
		status = response.getStatus();
		body = response.readEntity(String.class);
		//Condition for the request to be successfull
		if((status == 202 || status == 200 || status == 201) && countOccourences(body,"}") >= 1)
			result = "OK";
		else
			result = "ERROR";
		
		//Get message to print out 
		message = getMessageInfo(12, "GET",URI + requestPath,"APPLICATION/JSON", "APPLICATION/JSON", result, status, body);

		//Print result from the request
		writerJson.write(message);
		System.out.print(message);

		writerJson.flush();
		writerXML.flush();

	}

	private static int countOccourences(String stringa, String token)
	{
		int counter = 0;
		int lastIndex = 0;

		while(lastIndex != -1){

			lastIndex = stringa.indexOf(token,lastIndex);

			if(lastIndex != -1){
				counter ++;
				lastIndex += token.length();
			}
		}

		return counter;
	}

	private static String getMessageInfo(int numero, String httpMetodo, String url, String tipo, String contentType, String output, int status, String risposta)
	{
		return  "\n\nRequest #"+numero+": "+httpMetodo+" "+url+" Accept: "+tipo+" content-type: "+contentType +
				"\n=> Result: "+ output +
				"\n=> HTTP Status: "+ status +
				"\n\n" +
				risposta + "\n\n";
	}

	private static URI getBaseURI() {
		return UriBuilder.fromUri(AssignmentClient.URI).build();
	}
}