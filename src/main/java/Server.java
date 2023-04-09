import netscape.javascript.JSObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

import org.json.simple.JSONObject;

public class Server {
    public static void main(String[] args) {
        int port = 8181;
        try(ServerSocket serverSocket = new ServerSocket(port)) {
            Socket clientSocket = serverSocket.accept();
            try (PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                 BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            ) {
                int step = 0;
                String name = null;
                boolean child = false;
                JSONObject json = new JSONObject();
                while (step < 3) {
                    switch (step) {
                        case 0:
                            json.clear();
                            json.put("command", "input");
                            json.put("text", "Write your name");
                            out.println(json.toJSONString());
                            name = in.readLine();
                            break;
                        case 1:
                            json.clear();
                            json.put("command", "input");
                            json.put("text", "Are you child? (yes/no)");
                            out.println(json.toJSONString());
                            String resp = in.readLine();
                            child = resp.equals("yes");
                            break;
                        case 2:
                            json.clear();
                            json.put("command", "exit");
                            String text = null;
                            if (child) {
                                text = String.format("Welcome to the kids area, %s! Let's play!", name);
                            } else {
                                text = String.format("Welcome to the adult zone, %s! Have a good rest, or a good working day!", name);
                            }
                            json.put("text", text);
                            out.println(json.toJSONString());
                            break;
                    }
                    step++;
                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
