import ocsf.client.AbstractClient;

public class hatClient extends AbstractClient {

    public ChatClient() {
        super("localhost", 8300);
    }

    @Override
    public void handleMessageFromServer(Object arg0) {
        System.out.println("Server Message sent to Client " + arg0);
    }

    public void connectionException(Throwable exception) {
        System.out.println("Server connection exception: " + exception);
    }

    public void connectionEstablished() {
        System.out.println("Connection Established");
    }


}
