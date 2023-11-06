package edu.school21.sockets.app;

import edu.school21.sockets.server.Server;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    public static void main(String[] args) {
        int port = 8081;
        if(args.length == 1){
            try{
                String[] portsArg = args[0].split("=");
                if(portsArg.length != 2 || !"--server-port".equals(portsArg[0])){
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
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext("edu.school21.sockets");
        Server server = applicationContext.getBean(Server.class);
        server.serverStart(port);
    }
}
