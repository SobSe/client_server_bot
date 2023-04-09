import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Client {
    public static void main(String[] args) {
        String host = "netology.homework";
        int port = 8181;
        try (Socket clientSocket = new Socket(host, port);
             PrintWriter out = new
                     PrintWriter(clientSocket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new
                     InputStreamReader(clientSocket.getInputStream()));
             Scanner scanner = new Scanner(System.in)) {

            JSONParser parser = new JSONParser();
            while (true) {
                String resp = in.readLine();
                JSONObject jsonResp = null;
                try {
                    jsonResp = (JSONObject) parser.parse(resp);
                } catch (ParseException e) {
                    System.out.println(e.getMessage());
                }

                if(jsonResp.get("command").equals("input")) {
                    System.out.println(jsonResp.get("text"));
                    out.println(scanner.nextLine());
                } else if((jsonResp.get("command").equals("exit"))) {
                    System.out.println(jsonResp.get("text"));
                    break;
                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
