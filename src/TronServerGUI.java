import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Objects;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class TronServerGUI extends JFrame
{
    private final String[] labels = {"Port #", "Timeout"};
    private final JTextField[] textFields = new JTextField[labels.length];
    private final JTextArea serverLog;
    private final JButton start;
    private final JButton close;
    private final JButton stop;
    private final JButton quit;
    private final TronServer server;

    public TronServerGUI()
    {
        //Defined some colors for usage throughout program
        Color MAIN_BACKGROUND = new Color(0, 0, 102);
        Color BOX_BACKGROUND = new Color(102, 153, 204);
        Color WHITE_TEXT = Color.WHITE;

        //Created event handler and made a counter for use
        EventHandler handler = new EventHandler();
        int i = 0;

        //Initialize the JPanels
        JPanel north = new JPanel();
        JPanel center = new JPanel(new BorderLayout());
        JPanel south = new JPanel();

        //Set up the Tron logo to display at top
        ImageIcon gameLogo = new ImageIcon(Objects.requireNonNull(getClass().getResource("tronLogo.png")));
        JLabel logoLabel = new JLabel(gameLogo);
        north.add(logoLabel, BorderLayout.NORTH);

        //Set the color of the panels to the dark blue
        north.setBackground(MAIN_BACKGROUND);
        center.setBackground(MAIN_BACKGROUND);
        south.setBackground(MAIN_BACKGROUND);

        //Set title, close operation, and color of GUI
        this.setTitle("Tron Server Panel");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.getContentPane().setBackground(MAIN_BACKGROUND);

        //Create the labels that show if the server has been started or not
        JLabel startedText = new JLabel("Started: ");
        startedText.setForeground(WHITE_TEXT);
        //Declare variables
        JLabel started = new JLabel("Not Started");
        started.setForeground(Color.RED);

        //Make a new panel for holding the status and set the color
        JPanel statusPanel = new JPanel();
        statusPanel.setBackground(MAIN_BACKGROUND);
        statusPanel.add(startedText);
        statusPanel.add(started);

        //Create a panel to hold the fields for the Port# and Timeout values
        JPanel textFieldsPanel = new JPanel(new GridLayout(labels.length, 2, 5, 5));
        //Set the color to the dark blue
        textFieldsPanel.setBackground(MAIN_BACKGROUND);
        //This loop goes through the two textfields, port and timeout, and adds them
        for(i = 0; i < textFields.length; i++)
        {
            //currentLabel is just the text field currently being added (first port).
            JLabel currentLabel = new JLabel(labels[i], JLabel.RIGHT);
            //Set the color of the label to white for readability
            currentLabel.setForeground(WHITE_TEXT);
            //Add the current label
            textFieldsPanel.add(currentLabel);
            //Create a text field for the current label with a width of 10
            textFields[i] = new JTextField(10);
            //Set the color of the text field to the light blue color
            textFields[i].setBackground(BOX_BACKGROUND);
            //Make sure text within the field is white
            textFields[i].setForeground(WHITE_TEXT);
            //Add the text field for the current label to the panel.
            textFieldsPanel.add(textFields[i]);
        }
        //Default values
        textFields[0].setText("8300");
        textFields[1].setText("500");

        //Create a main panel and buffer to format and contain the status panel and the text fields panel
        JPanel mainCentralPanel = new JPanel(new BorderLayout());
        mainCentralPanel.add(statusPanel, BorderLayout.NORTH);
        mainCentralPanel.add(textFieldsPanel, BorderLayout.CENTER);
        JPanel centralBuffer = new JPanel();
        centralBuffer.setBackground(MAIN_BACKGROUND);
        centralBuffer.add(mainCentralPanel);
        center.add(centralBuffer, BorderLayout.NORTH);

        //Create a new panel to contain the server log & corresponding label
        JPanel serverLogPanel = new JPanel(new BorderLayout());
        serverLogPanel.setBackground(MAIN_BACKGROUND);
        JLabel serverLabel = new JLabel("Server Log", JLabel.CENTER);
        serverLabel.setForeground(WHITE_TEXT);
        JPanel logBuffer = new JPanel();
        logBuffer.setBackground(MAIN_BACKGROUND);
        logBuffer.add(serverLabel);
        serverLogPanel.add(logBuffer, BorderLayout.NORTH);

        //Create the actual scroll pane area for the server log
        serverLog = new JTextArea(10, 35);
        serverLog.setEditable(false);
        serverLog.setBackground(BOX_BACKGROUND);
        serverLog.setForeground(WHITE_TEXT);
        JScrollPane serverLogPane = new JScrollPane(serverLog);

        //Add the scroll pane log to the GUI
        JPanel centerSouth = new JPanel();
        centerSouth.setBackground(MAIN_BACKGROUND);
        centerSouth.add(serverLogPane);
        center.add(centerSouth, BorderLayout.SOUTH);

        //Implement the start button
        start = new JButton("Start");
        start.setBackground(BOX_BACKGROUND);
        start.setForeground(WHITE_TEXT);
        start.addActionListener(handler);
        south.add(start);

        //Implement the close button
        close = new JButton("Close");
        close.setBackground(BOX_BACKGROUND);
        close.setForeground(WHITE_TEXT);
        close.addActionListener(handler);
        south.add(close);

        //Implement the stop button
        stop = new JButton("Stop");
        stop.setBackground(BOX_BACKGROUND);
        stop.setForeground(WHITE_TEXT);
        stop.addActionListener(handler);
        south.add(stop);

        //Implement the quit button
        quit = new JButton("Quit");
        quit.setBackground(BOX_BACKGROUND);
        quit.setForeground(WHITE_TEXT);
        quit.addActionListener(handler);
        south.add(quit);

        //Add the main panels to the GUI
        this.add(north, BorderLayout.NORTH);
        this.add(center, BorderLayout.CENTER);
        this.add(south, BorderLayout.SOUTH);

        //Display GUI
        this.setSize(700, 700);
        this.setVisible(true);

        server = new TronServer();
        server.setLog(serverLog);
        server.setStatus(started);
        Database database = new Database();
        server.setDatabase(database);  // Set the database on the ChatServer
    }

    public static void main(String[] args)
    {
        new TronServerGUI();
    }

    class EventHandler implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            Object buttonClicked = e.getSource();

            if(buttonClicked == start)
            {
                // Display an error if the port number or timeout was not entered.
                if (textFields[0].getText().isEmpty() || textFields[1].getText().isEmpty())
                {
                    serverLog.append("Port number or timeout not entered before pressing Listen\n");
                }

                // Otherwise, tell the server to start listening with the user's settings.
                else
                {
                    server.setPort(Integer.parseInt(textFields[0].getText()));
                    server.setTimeout(Integer.parseInt(textFields[1].getText()));
                    try
                    {
                        server.listen();
                    }
                    catch (IOException e1)
                    {
                        serverLog.append("An exception occurred: " + e1.getMessage() + "\n");
                    }
                }
            }

            // Handle the Close button.
            else if (buttonClicked == close)
            {
                // Display an error if the server has not been started.
                if (!server.isListening())
                {
                    serverLog.append("Server not currently started\n");
                }

                // Otherwise, close the server.
                else
                {
                    try
                    {
                        server.close();
                    }
                    catch (IOException e1)
                    {
                        serverLog.append("An exception occurred: " + e1.getMessage() + "\n");
                    }
                }
            }

            // Handle the Stop button.
            else if (buttonClicked == stop)
            {
                // Display an error if the server is not listening.
                if (!server.isListening())
                {
                    serverLog.append("Server not currently listening\n");
                }

                // Otherwise, stop listening.
                else
                {
                    server.stopListening();
                }
            }

            // For the Quit button, just stop this program.
            else if (buttonClicked == quit)
            {
                System.exit(0);
            }
        }
    }
}