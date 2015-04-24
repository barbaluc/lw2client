import java.util.*;
import java.io.*;
import java.net.*;

public class HttpClient
{
    protected URL url;
    protected HttpURLConnection server;

    /**
     * @param szUrl: String object for the URL
     */
    public HttpClient(String szUrl) throws Exception
    {
        try
        {
            url = new URL(szUrl);
        }
        catch (Exception e)
        {
            throw new Exception("Invalid URL");
        }
    }

    /**
     * @param method: String object for client method (POST, GET,...)
     */
    public void connect(String method) throws Exception
    {
        try
        {
            server = (HttpURLConnection)url.openConnection();
            server.setDoInput(true);
            server.setDoOutput(true);
            server.setRequestMethod(method);
            server.setRequestProperty("Content-type",
                    "application/xml");
            server.connect();
        }
        catch (Exception e)
        {
            throw new Exception("Connection failed");
        }
    }

    public void disconnect()
    {
        server.disconnect();
    }

    public void displayResponse() throws Exception
    {
        String line;

        try
        {
            BufferedReader s = new BufferedReader(new InputStreamReader(server.getInputStream()));
            line = s.readLine();
            while (line != null)
            {
                System.out.println(line);
                line = s.readLine();
            }
            s.close();
        }
        catch(Exception e)
        {
            throw new Exception("Unable to read input stream");
        }
    }

    public void post(String s) throws Exception
    {
        try
        {
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(server.getOutputStream()));
            bw.write(s, 0, s.length());
            bw.flush();
            bw.close();
        }
        catch(Exception e)
        {
            throw new Exception("Unable to write to output stream");
        }
    }

    public static void main(String argv[])
    {
        if (argv.length == 0)
        {
            System.out.println("Usage: java HttpClient url\r\n");
            System.exit(0);
        }

        try
        {
            HttpClient c = new HttpClient(argv[0]);
            c.connect("GET");
            c.displayResponse();
            c.disconnect();

            System.out.println("--------------------------------");

            c.connect("POST");
            c.post("<cv_entry> <id>3</id> <firstname>George</firstname> <name>Leyeti</name> </cv_entry>");
            c.displayResponse();
            c.disconnect();

        }
        catch (Exception e)
        {
            e.printStackTrace();
            System.out.println("LOL");
        }
    }
}