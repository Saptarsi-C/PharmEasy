/**
 * 
 */
package com.saptarsi.assignement.errorcodes;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;

/**
 * @author saptarsichaurashy
 *
 */
public class ErrorToHttpCodeMapping {

	private static final Map<String, HttpStatus> map = new HashMap<String, HttpStatus>(){
		private static final long serialVersionUID = 1L;

	{
		put("UMSP_700", HttpStatus.OK);
		put("UMSP_701", HttpStatus.CREATED);
		put("UMSP_702", HttpStatus.NO_CONTENT);
		put("UMSP_703", HttpStatus.OK);
		put("UMSP_705", HttpStatus.OK);
		put("UMSP_710", HttpStatus.OK);
		put("UMSP_720", HttpStatus.OK);
		put("UMSP_600", HttpStatus.BAD_REQUEST);
		put("UMSP_601", HttpStatus.BAD_REQUEST);
		put("UMSP_602", HttpStatus.CONFLICT);
		put("UMSP_603", HttpStatus.FAILED_DEPENDENCY);
		put("UMSP_604", HttpStatus.CONFLICT);
		put("UMSP_605", HttpStatus.INTERNAL_SERVER_ERROR);
		put("UMSP_606", HttpStatus.UNAUTHORIZED);
		put("UMSP_610", HttpStatus.INTERNAL_SERVER_ERROR);
		put("UMSP_611", HttpStatus.INTERNAL_SERVER_ERROR);
		put("UMSP_615", HttpStatus.CONFLICT);
		put("UMSP_616", HttpStatus.NO_CONTENT);
		put("UMSP_617", HttpStatus.CONFLICT);
		put("UMSP_618", HttpStatus.UNAUTHORIZED);
		put("UMSP_619", HttpStatus.INTERNAL_SERVER_ERROR);
		put("UMSP_620", HttpStatus.INTERNAL_SERVER_ERROR);
		put("UMSP_621", HttpStatus.UNPROCESSABLE_ENTITY);
		put("UMSP_630", HttpStatus.UNPROCESSABLE_ENTITY);
		put("UMSP_631", HttpStatus.INTERNAL_SERVER_ERROR);
		put("UMSP_632", HttpStatus.BAD_REQUEST);
		put("UMSP_633", HttpStatus.INTERNAL_SERVER_ERROR);
		put("UMSP_634", HttpStatus.NO_CONTENT);
		put("UMSP_635", HttpStatus.OK);
		put("UMSP_636", HttpStatus.CREATED);
		put("UMSP_645", HttpStatus.BAD_REQUEST);
		put("UMSP_646", HttpStatus.INTERNAL_SERVER_ERROR);
		put("UMSP_647", HttpStatus.INTERNAL_SERVER_ERROR);
		put("UMSP_648", HttpStatus.NOT_IMPLEMENTED);
		put("UMSP_650", HttpStatus.INTERNAL_SERVER_ERROR);
		
		put("UMSP_651", HttpStatus.INTERNAL_SERVER_ERROR);
		put("UMSP_652", HttpStatus.INTERNAL_SERVER_ERROR);
		put("UMSP_653", HttpStatus.INTERNAL_SERVER_ERROR);
		put("UMSP_654", HttpStatus.INTERNAL_SERVER_ERROR);
		put("UMSP_655", HttpStatus.INTERNAL_SERVER_ERROR);
		put("UMSP_656", HttpStatus.INTERNAL_SERVER_ERROR);
		put("UMSP_730", HttpStatus.NO_CONTENT);
		put("UMSP_731", HttpStatus.CREATED);
		put("UMSP_732", HttpStatus.OK);
	}};

	public static HttpStatus getStatusFromAppCode(String appCode){
		return map.get(appCode);
	}
}
