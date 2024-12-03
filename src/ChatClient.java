import ocsf.client.AbstractClient;
import java.util.ArrayList;

public class ChatClient extends AbstractClient {
    private LoginControl loginControl;
    private CreateAccountControl createAccountControl;
    private ArrayList<Player> players = new ArrayList<>();
    private ClientGUI clientGUI; // Reference to ClientGUI

    public ChatClient()
    {
        super("localhost", 8300);
    }

    // Setter for ClientGUI
    public void setClientGUI(ClientGUI clientGUI) {
        this.clientGUI = clientGUI;
    }


    public void setLoginControl(LoginControl loginControl) {
        this.loginControl = loginControl;
    }

    public void setCreateAccountControl(CreateAccountControl createAccountControl) {
        this.createAccountControl = createAccountControl;
    }

    @Override
    public void handleMessageFromServer(Object msg)
    {
        if (msg instanceof String)
        {
            if (msg.equals("LoginSuccessful") || ((String) msg).contains("Player joined")) {
                loginControl.handleLoginResponse(msg);
            } else if (msg.equals("CreateAccountSuccessful"))
            {
                createAccountControl.handleServerResponse(msg);
            }
            else if (((String) msg).contains("Error"))
            {
                System.out.println((String) msg);
            }
        }
        else if (msg instanceof Error) {
            Error error = (Error) msg;
            String errorMessage = error.getMessage();
            if (errorMessage.contains("IncorrectInfo")) {
                loginControl.displayError(errorMessage);
            } else if (errorMessage.contains("already in use")) {
                createAccountControl.handleServerResponse(error);
            }
        }
    }

    public void connectionException(Throwable exception) {
        System.out.println("Server connection exception: " + exception.getMessage());
        exception.printStackTrace();
    }

    public void connectionEstablished() {
        System.out.println("Connection Established");
    }
}
