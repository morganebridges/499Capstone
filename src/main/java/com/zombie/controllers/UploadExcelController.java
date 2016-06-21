package com.zombie.controllers;

import com.zombie.services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
@RestController
public class UploadExcelController {
    @Autowired
    private AdminService adminService;
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public String uploadFile(
            @RequestParam("uploadedFile") MultipartFile uploadedFileRef) {
	    try {
		    int users = adminService.importUserData(uploadedFileRef);
		    return users + " users were added.";
	    } catch (IllegalArgumentException e) {
		    return e.getMessage();
	    }
    }

}