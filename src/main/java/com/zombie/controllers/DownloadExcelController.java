package com.zombie.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/downloadservice")
public class DownloadExcelController {

	@RequestMapping(value = "/download", method = RequestMethod.GET)
	public String downloadFile() {
		return "File downloaded successfully!";
	}

}