import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Random;

public class RemoteCommunication extends UnicastRemoteObject implements Communication {
    private ArrayList<Account> Accounts;

    public RemoteCommunication() throws RemoteException {
        super();
        this.Accounts = new ArrayList<>();
    }

    @Override
    public int CreateAccount(String username) throws RemoteException {
        if(username.matches("[a-zA-Z0-9_]+")) {
            for (Account account : Accounts) {
                if (account.getUsername().equals(username)) {
                    return 1;
                }
            }
            Random random = new Random();
            int number=1;
            boolean flag = true;
            while (flag) {
                number = random.nextInt(10000);
                flag = false; // new addition
                for (Account account : Accounts){
                    if (number == account.getAuthToken()) {
                        flag = true;
                        break;
                    }
                }
            }
            Accounts.add(new Account(username, number));
            return number;
        }else{
            return 0;
        }
    }

    @Override
    public ArrayList<String> ShowAccounts(int token) throws RemoteException {
        boolean foundToken = false;
        for (Account account : Accounts) {
            if (account.getAuthToken() == token) {
                foundToken = true;
            }
        }
        if (!foundToken) {
            return null;
        }
        ArrayList<String> usernames = new ArrayList<>();
        for (Account account : Accounts) {
            usernames.add(account.getUsername());
        }
        return usernames;
    }

    @Override
    public String SendMessage(int token, String recipient, String message_body) throws RemoteException {
        String temp = "";
        for (Account account : Accounts) {
            if (account.getUsername().equals(recipient)) {
                temp = "User does not exist";
                break;
            }
        }
        String temp1 = "";
        int temp2 = -1;
        boolean flag = false;
        boolean foundSender = false;
        for (int i=0; i<Accounts.size(); i++){
//            if (Accounts.get(i).getUsername()==recipient){
            if (Accounts.get(i).getUsername().equals(recipient)) {
                flag = true;
                temp2 = i;
            }
            if (Accounts.get(i).getAuthToken() == token){
                temp1 = Accounts.get(i).getUsername();
                foundSender = true;
//                temp2 = i;
            }
        }
        Message message = new Message(temp1, recipient, message_body);
        if (flag) {
            Random random = new Random();
            int number=1;
            boolean flag1 = true;
            while (flag1) {
                number = random.nextInt(1000);
                flag1 = false;
                for (Message message1 : Accounts.get(temp2).getMessageBox())
                    if ((number == message1.getMessageID())) {
                        flag1 = true;
                        break;
                    }
            }
            if (!foundSender) {
                return "Invalid Auth Token";
            }
            message.setMessageID(number);
            Accounts.get(temp2).addMessage(message);
            temp = "OK";
        }else if (temp2==-1) {
            temp = "User does not exist";
        }
        if (!foundSender) {
            temp = "Invalid Auth Token";
        }
        return temp;
    }

    @Override
    public ArrayList<Message> ShowInbox(int token) throws RemoteException {
        for (Account account : Accounts) {
            if (account.getAuthToken() == token) {
                return account.getMessageBox();
            }
        }
        return null;
    }

    @Override
    public String ReadMessage(int token, int message_id) throws RemoteException {
        for (Account account : Accounts) {
            if (account.getAuthToken() == token) {
                for (Message message : account.getMessageBox()) {
                    if (message.getMessageID() == message_id) {
                        message.setIsRead(true);
                        return "(" + message.getSender() + ") " + message.getBody();
                    }
                }
                return "Message " + message_id + " does not exist";
            }
        }
        return "Invalid Auth Token";
    }

    @Override
    public String DeleteMessage(int token, int message_id) throws RemoteException {
        for (Account account : Accounts) {
            if (account.getAuthToken() == token) {
                for (Message message : account.getMessageBox()) {
                    if (message.getMessageID() == message_id) {
                        account.getMessageBox().remove(message);
                        return "OK";
                    }
                }
                return "Message does not exist";
            }
        }
        return "Invalid Auth Token";
    }
}
