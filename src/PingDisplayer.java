import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class PingDisplayer {
    String pingCommand;
    String pingResult;
    String pingLine;
    Runtime r;
    Process p;
    public PingDisplayer(String pingAddress){
        pingCommand = "ping "+pingAddress + " -t";
    }

    public void PingServer(){
        try {
            r = Runtime.getRuntime();
            p = r.exec(pingCommand);

            BufferedReader in = new BufferedReader(new
                    InputStreamReader(p.getInputStream()));
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                System.out.println(inputLine);
                if (inputLine.contains("time=")){
                    int start = inputLine.indexOf("time")+ 5 ;
                    int end = inputLine.indexOf("ms");
                    pingLine = inputLine.substring(start,end);
                    System.out.println(getPing()); //Delete if needed
                }
                else if(inputLine.contains("Request timed out.")){
                    pingLine = "Cannot connect to address";
                }
            }
            in.close();

        } catch (IOException e) {
            System.out.println(e);
        }
    }

    String getPing(){
        return pingLine;
    }

    public static void main(String[] args) {
        String testIPAddress = "www.google.com";
        PingDisplayer pd = new PingDisplayer(testIPAddress);
        pd.PingServer();
    }
}

