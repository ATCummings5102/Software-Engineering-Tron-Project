import ocsf.client.AbstractClient;

public class ChatClient extends AbstractClient
{
    // Private data fields for storing the GUI controllers.
    private LoginControl loginControl;
    private CreateAccountControl createAccountControl;

    // Constructor for initializing the client with default settings.
    public ChatClient()
    {
        super("localhost", 8300);
    }

    // Setters for the GUI controllers.
    public void setLoginControl(LoginControl loginControl)
    {
        this.loginControl = loginControl;
    }

    public void setCreateAccountControl(CreateAccountControl createAccountControl)
    {
        this.createAccountControl = createAccountControl;
    }

    @Override
    public void handleMessageFromServer(Object msg) {
        // Check if the message is related to login success or error
        if (msg instanceof String) {
            if (msg.equals("LoginSuccessful")) {
                // Handle login success
                loginControl.handleLoginResponse(msg);

            } else if (msg.equals("CreateAccountSuccessful")) {
                // Handle account creation success
                createAccountControl.handleServerResponse(msg);
            }
        } else if (msg instanceof Error) {
            // If the message is an error, handle the error
            Error error = (Error) msg;
            String errorMessage = error.getMessage();

            // Determine whether the error relates to log-in or account creation
            if (errorMessage.contains("IncorrectInfo")) {
                // Invalid login
                loginControl.displayError(errorMessage);
            } else if (errorMessage.contains("already in use")) {
                // Duplicate username for account creation
                createAccountControl.handleServerResponse(error);
            }
        }
    }

    public void connectionException(Throwable exception) {
        System.out.println("Server connection exception: " + exception);
    }

    public void connectionEstablished() {
        System.out.println("Connection Established");
    }

}
