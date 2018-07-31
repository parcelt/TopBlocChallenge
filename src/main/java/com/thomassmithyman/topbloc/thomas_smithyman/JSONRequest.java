package com.thomassmithyman.topbloc.thomas_smithyman;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONObject;

public class JSONRequest {

	public void createJSONRequest(int[] data1NumberSet1, int[] data1NumberSet2,
			String[] data1WordSet) {
		
		String json = new JSONObject()
	        .put("id", "trsmithyman@gmail.com")
	        .put("numberSetOne", new JSONArray(data1NumberSet1))
	        .put("numberSetTwo", new JSONArray(data1NumberSet2))
	        .put("wordSetOne", new JSONArray(data1WordSet)).toString();

		try {

			// Create and send request
			URL url = new URL("http://34.239.125.159:5000/challenge");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setConnectTimeout(5000);
			conn.setRequestProperty("Content-Type",
					"application/json; charset=UTF-8");
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setRequestMethod("POST");
			OutputStream os = conn.getOutputStream();
			os.write(json.getBytes("UTF-8"));
			os.close();

			//Verified data was received
			InputStream in = new BufferedInputStream(conn.getInputStream());
			String result = IOUtils.toString(in, "UTF-8");
			System.out.println(result);
			in.close();
			
			conn.disconnect();

		} catch (Exception e) {
			System.out.println(e);
		}
	}

}
