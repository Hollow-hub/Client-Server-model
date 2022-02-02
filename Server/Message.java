public class Message implements java.io.Serializable {
    private boolean isRead;
    private String sender;
    private String receiver;
    private String body;
    private int messageID;

    public Message(String sender,String receiver,String body){
        this.sender = sender;
        this.receiver = receiver;
        this.body = body;
        isRead = false;
    }

    public int getMessageID(){
        return messageID;
    }

    public void setMessageID(int messageID){
        this.messageID = messageID;
    }

    public boolean isRead() {
        return isRead;
    }

    public void setIsRead(boolean isRead){
        this.isRead = isRead;
    }

    public String getSender() {
        return sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public String getBody() {
        return body;
    }
}
