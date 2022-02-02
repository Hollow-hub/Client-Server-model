import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class MessagingServer {
    public static void main(String[] args) {
        try {
            int port = Integer.parseInt(args[0]);
            RemoteCommunication stub = new RemoteCommunication();
            Registry rmiRegistry = LocateRegistry.createRegistry(port);
            rmiRegistry.rebind("communication", stub);
            System.out.println("Server start");
        } catch (Exception e) {
            System.out.println(e);
        }
    }

}