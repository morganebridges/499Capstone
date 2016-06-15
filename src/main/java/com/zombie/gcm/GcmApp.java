package com.zombie.gcm;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zombie.gcm.MsgContent;

public class GcmApp {
	public static void main(String[] args) {
		System.out.println("Sending POST to GCM");

		String apiKey = "AIzaSyBnyB-lod8OtE-Kr5tx5TOOYy0A4lxJKkU";
		MsgContent content = createMsgContent();

		POST2GCM.post(apiKey, content);
	}

	public static MsgContent createMsgContent() {

		MsgContent c = new MsgContent();

		c.addRegId(
				"APA91bFqnQzp0z5IpXWdth1lagGQZw1PTbdBAD13c-UQ0T76BBYVsFrY96MA4SFduBW9RzDguLaad-7l4QWluQcP6zSoX1HSUaAzQYSmI93....");
		c.createData("Test Title", "Test Message");

		return c;
	}
}