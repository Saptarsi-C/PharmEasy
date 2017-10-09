package com.saptarsi.assignement.notification;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * @author saptarsichaurashy
 *
 */
public class EmailTemplateContainer {

	private static EmailTemplateContainer instance = null;
	
	Map<String, String> container;
	
	private EmailTemplateContainer(){
		container = new HashMap<String, String>();
	}
	
	public static EmailTemplateContainer getInstance(){
	      if(instance == null) {
	          instance = new EmailTemplateContainer();
	       }
	       return instance;
	}
	
	public String getBody(String templateId){
		
		String body = container.get(templateId);
		
		if(body == null){
			// Read file			
			body = getFile(templateId);
			container.put(templateId, body);
		}		
		return body; 
	}
	
	private String getFile(String fileName) {

			StringBuilder result = new StringBuilder("");

			//Get file from resources folder
			ClassLoader classLoader = getClass().getClassLoader();
			File file = new File(classLoader.getResource(fileName).getFile());

			try (Scanner scanner = new Scanner(file)) {

				while (scanner.hasNextLine()) {
					String line = scanner.nextLine();
					result.append(line).append("\n");
				}

				scanner.close();

			} catch (IOException e) {
				e.printStackTrace();
			}

			return result.toString();

		  }	
	
}