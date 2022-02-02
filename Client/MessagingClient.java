import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;

public class MessagingClient {

    public static void main(String args[]) {
        try {
            String ip = args[0];
            int port = Integer.parseInt(args[1]);
            int function_id = Integer.parseInt(args[2]);
            Registry registry = LocateRegistry.getRegistry(ip, port);
            Communication stub = (Communication) registry.lookup("communication");
            String username;
            int authToken;
            String recipient;
            int message_id;
            String message_body;

            switch (function_id){
                case 1:
                    username = args[3];
                    int result = stub.CreateAccount(username);
                    if (result==1){
                        System.out.println("Sorry, the user already exists");
                    }else if(result==0){
                        System.out.println("Invalid Username");
                    }else {
                        System.out.println(result);
                    }
                    break;
                case 2:
                    authToken = Integer.parseInt(args[3]);
                    var usernames = stub.ShowAccounts(authToken);
                    if (usernames == null) {
                        System.out.println("Invalid Auth Token");
                        break;
                    }
                    for (int i = 0; i < usernames.size(); i++) {
                        System.out.println(i+1 + ". " + usernames.get(i));
                    }
                    break;
                case 3:
                    recipient = args[4];
                    message_body = args[5];
                    authToken = Integer.parseInt(args[3]);
                    System.out.println(stub.SendMessage(authToken,recipient,message_body));
                    break;
                case 4:
                    authToken = Integer.parseInt(args[3]);
                    ArrayList<Message> messages = stub.ShowInbox(authToken);
                    if (messages == null) {
                        System.out.println("Invalid Auth Token");
                        break;
                    }
                    for (Message message1 : messages){
                        if (message1.isRead()) {
                            System.out.println(message1.getMessageID() + ". from: " + message1.getSender());
                        }
                        else {
                            System.out.println(message1.getMessageID() + ". " + "from: " + message1.getSender() + "*");
                        }
                    }
                    break;
                case 5:
                    message_id = Integer.parseInt(args[4]);
                    authToken = Integer.parseInt(args[3]);
                    System.out.println(stub.ReadMessage(authToken,message_id));
                    break;
                case 6:
                    message_id = Integer.parseInt(args[4]);
                    authToken = Integer.parseInt(args[3]);
                    System.out.println(stub.DeleteMessage(authToken,message_id));
                    break;
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}