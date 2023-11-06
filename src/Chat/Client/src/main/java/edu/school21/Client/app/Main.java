package edu.school21.Client.app;

import edu.school21.Client.models.Client;

public class Main {
    public static void main(String[] args) {
        int port = 8081;
        if(args.length == 1){
            try{
                String[] portsArg = args[0].split("=");
                if(portsArg.length != 2 || !"--port".equals(portsArg[0])){
                    throw new RuntimeException("Application arguments are invalid");
                }
                port = Integer.parseInt(portsArg[1]);
                if(port < 1024 || port > 65535){
                    throw new RuntimeException("Application arguments are invalid, port is out of range 1024 - 65535");
                }
            }catch(RuntimeException e){
                System.out.println(e.getMessage());
                port = 8081;
            }
        }
        Client client = new Client("localhost", port);
        client.execute();
    }
}
