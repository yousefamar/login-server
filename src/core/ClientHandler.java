package core;

import java.io.*;
import java.net.*;

public class ClientHandler implements Runnable {

	private Socket clientSoc;
	
	public ClientHandler(Socket clientSoc) {
		this.clientSoc = clientSoc;
	}
	
	public String login(String username, String password) {
		//TODO: Add version parameter.
		String parameters = "username="+username+"&password="+password;
		HttpURLConnection connection = null;
		try {
			connection = (HttpURLConnection) new URL("http://www.4ytech.com/usr/javaLogin.php").openConnection();
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

			connection.setRequestProperty("Content-Length",	Integer.toString(parameters.getBytes().length));
			connection.setRequestProperty("Content-Language", "en-US");

			connection.setUseCaches(false);
			connection.setDoInput(true);
			connection.setDoOutput(true);

			DataOutputStream out = new DataOutputStream(connection.getOutputStream());
			out.writeBytes(parameters);
			out.flush();
			out.close();

			BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String response = in.readLine();
			in.close();
			return response;
		} catch (Exception e) {
			return "-1:Unable to connect to user database.";
		} finally {
			if (connection != null) {
				connection.disconnect();
			}
		}
	}

	public void run() {
		BufferedReader in = null;
		PrintWriter out = null;
		try {
			in = new BufferedReader(new InputStreamReader(clientSoc.getInputStream()));
			out = new PrintWriter(clientSoc.getOutputStream(), true);
			
			out.write(login(in.readLine(), in.readLine()));
		} catch (Exception e) {
			//TODO: Error.
			e.printStackTrace();
		} finally {
			try {
				in.close();
				out.close();
				clientSoc.close();
			} catch (IOException e) {
			}
		}
	}
	
	public static void main(String[] args) {
		System.out.println(new ClientHandler(null).login("Paraknight", "bashbash"));
	}
}