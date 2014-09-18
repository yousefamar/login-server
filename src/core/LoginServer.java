package core;

import java.io.IOException;
import java.net.ServerSocket;

public class LoginServer {
	
	private void listenOnPort(int port) {
		try {
			ServerSocket serverSoc = new ServerSocket(port);
			int uid = 0;
			while (true) {
				new Thread(new ClientHandler(serverSoc.accept()), "Client"+(uid++)+" Thread").start();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		//TODO: This is the launcher, yo!
		int port = 8000;
		if (args.length > 0)
			port = Integer.parseInt(args[0]);

		new LoginServer().listenOnPort(port);
	}
}