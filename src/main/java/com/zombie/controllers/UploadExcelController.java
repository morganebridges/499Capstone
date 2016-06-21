package com.zombie.controllers;

import com.zombie.models.User;
import com.zombie.services.AdminService;
import com.zombie.services.UserService;
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
	@Autowired
	private UserService userService;

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public String uploadFile(
            @RequestParam("uploadedFile") MultipartFile uploadedFileRef) {
	    try {
		    long oldUserCount = userService.getUserCount();
		    Iterable<User> oldUsers = userService.getAllUsers();
		    int newUserCount = adminService.importUserData(uploadedFileRef);
		    userService.deleteUsers(oldUsers);

		    return newUserCount + " users were added. " + oldUserCount + " users were removed.";
	    } catch (Exception e) {
		    e.printStackTrace();
		    return e.getMessage();
	    }
    }

}