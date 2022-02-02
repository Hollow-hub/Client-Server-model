import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface Communication extends Remote{

    int CreateAccount(String username) throws RemoteException;

    ArrayList<String> ShowAccounts(int token) throws RemoteException;

    String SendMessage(int token, String recipient, String message_body) throws RemoteException;

    ArrayList<Message> ShowInbox(int token) throws RemoteException;

    String ReadMessage(int token, int message_id)throws RemoteException;

    String DeleteMessage(int token, int message_id)throws RemoteException;

}
