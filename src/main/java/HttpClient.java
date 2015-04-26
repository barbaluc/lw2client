import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpClient {
    protected URL url;
    protected HttpURLConnection server;


    public HttpClient(String szUrl) throws Exception {
        try {
            url = new URL(szUrl);
        } catch (Exception e) {
            throw new Exception("Invalid URL");
        }
    }


    public void connect(String method) throws Exception {
        try {
            server = (HttpURLConnection) url.openConnection();
            server.setDoInput(true);
            server.setDoOutput(true);
            server.setRequestMethod(method);
            server.setRequestProperty("Content-type", "application/xml");
            server.connect();
        } catch (Exception e) {
            throw new Exception("Connection failed");
        }
    }

    public void disconnect() {
        server.disconnect();
    }

    public String displayResponse() throws Exception {
        String line, result = "";

        try {
            BufferedReader s = new BufferedReader(new InputStreamReader(server.getInputStream()));
            line = s.readLine();
            while (line != null) {
                System.out.println(line);
                result = line;
                line = s.readLine();
            }
            s.close();
        } catch (Exception e) {
            throw new Exception("Unable to read input stream");
        }
        return result;
    }

    public void post(String s) throws Exception {
        try {
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(server.getOutputStream()));
            bw.write(s, 0, s.length());
            bw.flush();
            bw.close();
        } catch (Exception e) {
            throw new Exception("Unable to write to output stream");
        }
    }

    public static void main(String argv[]) {
        /*if (argv.length == 0)
        {
            System.out.println("Usage: java HttpClient url\r\n");
            System.exit(0);
        }*/

        try {
            //HttpClient c = new HttpClient(argv[0]);
            HttpClient c = new HttpClient("http://lw2-barbaluc.rhcloud.com/resume");
            c.connect("GET");
            System.out.println("Méthode GET :");
            c.displayResponse();
            c.disconnect();

            /*System.out.println("Méthode POST :");

            c.connect("POST");
            c.post("<cv_entry> <id>4</id> <firstname>George</firstname> <name>Leyeti</name> </cv_entry>");
            c.displayResponse();
            c.disconnect();*/

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}