import java.util.ArrayList;
import java.util.List;

public class Account {
    private String username;
    private int authToken;
    List<Message> messageBox;
    public Account(String username, int authToken) {
        this.username = username;
        this.authToken = authToken;
        this.messageBox = new ArrayList<>();
    }
    public String getUsername() {
        return username;
    }
    public int getAuthToken() {
        return authToken;
    }
    public ArrayList<Message> getMessageBox() {
        return (ArrayList<Message>) messageBox;
    }
    public void addMessage(Message message) {
        messageBox.add(message);
    }
}