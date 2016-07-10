package com.zombie.controllers;

import com.zombie.services.AdminService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayInputStream;


@RestController
@RequestMapping(value = "/downloadservice")
public class DownloadExcelController {

	@Autowired
	AdminService adminService;

	private final Logger log = LoggerFactory.getLogger(DownloadExcelController.class);

	@RequestMapping(value = "/download", method = RequestMethod.GET,
			produces = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
	public ResponseEntity<InputStreamResource> downloadFile() {
		try {
			//log.trace("User export being requested");
			byte[] out = adminService.exportUserData();
			ByteArrayInputStream in = new ByteArrayInputStream(out);

			HttpHeaders headers = new HttpHeaders();
			headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
			headers.add("Pragma", "no-cache");
			headers.add("Expires", "0");


			ResponseEntity<InputStreamResource> response = ResponseEntity.ok().headers(headers)
					.contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
					.body(new InputStreamResource(in));
			String statusCode = response.getStatusCode().name();
			//log.info("User export being sent statusCode={}", statusCode);
			return response;


		} catch (Exception e) {
			//log.error("Error exporting user data", e);
			return new ResponseEntity<>(HttpStatus.I_AM_A_TEAPOT);
		}
	}

}