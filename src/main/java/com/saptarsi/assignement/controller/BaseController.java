/**
 * 
 */
package com.saptarsi.assignement.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.saptarsi.assignement.model.request.DoctorListRequest;
import com.saptarsi.assignement.model.request.LoginRequest;
import com.saptarsi.assignement.model.request.SignupRequest;
import com.saptarsi.assignement.model.request.UpdateMedicalRecordRequest;
import com.saptarsi.assignement.model.request.UpdatePrescriptionRequest;
import com.saptarsi.assignement.model.response.ModelAPIResponse;

/**
 * @author saptarsichaurashy
 *
 */
public interface BaseController {

	@RequestMapping(value = "/signup", produces = { "application/json" }, consumes = {
			"application/json" }, method = RequestMethod.POST)
	default ResponseEntity<ModelAPIResponse> signup(@RequestBody SignupRequest signupRequest) {
		return new ResponseEntity<ModelAPIResponse>(HttpStatus.NOT_IMPLEMENTED);
	}

	@RequestMapping(value = "/signup", produces = { "application/json" }, consumes = {
			"application/json" }, method = RequestMethod.POST)
	default ResponseEntity<ModelAPIResponse> login(@RequestBody LoginRequest loginRequest) {
		return new ResponseEntity<ModelAPIResponse>(HttpStatus.NOT_IMPLEMENTED);
	}

	@RequestMapping(value = "/getMedicalRecord", produces = { "application/json" }, method = RequestMethod.GET)
	default ResponseEntity<ModelAPIResponse> getMedicalRecord(@RequestHeader(required = true) String token,
			@RequestParam String userName, @RequestParam String pId) {
		return new ResponseEntity<ModelAPIResponse>(HttpStatus.NOT_IMPLEMENTED);
	}

	@RequestMapping(value = "/updateMedicalRecord", produces = { "application/json" }, consumes = {
			"application/json" }, method = RequestMethod.PUT)
	default ResponseEntity<ModelAPIResponse> updateMedicalRecord(@RequestHeader(required = true) String token,
			@RequestParam String userName, @RequestParam String pId,
			@RequestBody(required = true) UpdateMedicalRecordRequest updateMedicalRecordRequest) {
		return new ResponseEntity<ModelAPIResponse>(HttpStatus.NOT_IMPLEMENTED);

	}

	@RequestMapping(value = "/addAuth", produces = { "application/json" }, consumes = {
	"application/json" }, method = RequestMethod.POST)
	default ResponseEntity<ModelAPIResponse> addAuthorization(@RequestHeader(required = true) String token, @RequestParam(required = true) String reqRole, @RequestParam String pId, @RequestBody DoctorListRequest doctorListRequest){
		return new ResponseEntity<ModelAPIResponse>(HttpStatus.NOT_IMPLEMENTED);
	}
	
	@RequestMapping(value = "/removeAuth", produces = { "application/json" }, consumes = {
	"application/json" }, method = RequestMethod.POST)
	default ResponseEntity<ModelAPIResponse> removeAuthorization(@RequestHeader(required = true) String token, @RequestParam(required = true) String reqRole, @RequestParam String pId, @RequestBody DoctorListRequest doctorListRequest){
		return new ResponseEntity<ModelAPIResponse>(HttpStatus.NOT_IMPLEMENTED);
	}
	
	@RequestMapping(value = "/getPrescription", produces = { "application/json" }, method = RequestMethod.GET)
	default ResponseEntity<ModelAPIResponse> getPrescription(@RequestHeader(required = true) String token,
			@RequestParam String patinetName, @RequestParam String pId, @RequestParam(required = true) String docname) {
		return new ResponseEntity<ModelAPIResponse>(HttpStatus.NOT_IMPLEMENTED);
	}

	@RequestMapping(value = "/updatePrescription", produces = { "application/json" }, consumes = {
			"application/json" }, method = RequestMethod.PUT)
	default ResponseEntity<ModelAPIResponse> updatePrescription(@RequestHeader(required = true) String token,
			@RequestParam String userName, @RequestParam String pId,
			@RequestBody(required = true) UpdatePrescriptionRequest updatePrescriptionRequest) {
		return new ResponseEntity<ModelAPIResponse>(HttpStatus.NOT_IMPLEMENTED);

	}
	
	@RequestMapping(value = "/getRegisterdList", produces = { "application/json" }, method = RequestMethod.GET)
	default ResponseEntity<ModelAPIResponse> getRegisterdList(@RequestHeader(required = true) String token,
			@RequestParam(required = true) String pageNo, @RequestParam(required =true) String role) {
		return new ResponseEntity<ModelAPIResponse>(HttpStatus.NOT_IMPLEMENTED);
	}
	@RequestMapping(value = "/getDoctorRegisterdList", produces = { "application/json" }, method = RequestMethod.GET)
	default ResponseEntity<ModelAPIResponse> getRegisterdDoctorList(@RequestHeader(required = true) String token,
			@RequestParam(required = true) String docId) {
		return new ResponseEntity<ModelAPIResponse>(HttpStatus.NOT_IMPLEMENTED);
	}
	
	@RequestMapping(value = "/getAuthForDoctor", produces = { "application/json" },  method = RequestMethod.GET)
	default ResponseEntity<ModelAPIResponse> getAuthForDoctor(@RequestHeader(required = true) String token, @RequestParam Long docId, @RequestParam String docName, @RequestParam(required = true) String patientName){
		return new ResponseEntity<ModelAPIResponse>(HttpStatus.NOT_IMPLEMENTED);
	}
	
	@RequestMapping(value = "/getAuthForDoctor", produces = { "application/json" },  method = RequestMethod.GET)
	default ResponseEntity<ModelAPIResponse> getAuthForPharmacist(@RequestHeader(required = true) String token, @RequestParam(required = true) String patientName){
		return new ResponseEntity<ModelAPIResponse>(HttpStatus.NOT_IMPLEMENTED);
	}
}
