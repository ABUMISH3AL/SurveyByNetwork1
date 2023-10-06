import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.net.*;
import java.io.*;

public class SecondScreen extends JFrame implements ActionListener
{


    private Socket socket = null;
    private DataInputStream console = null;
    private DataOutputStream streamOut = null;
    private SecondScreenThread client2 = null;
    private String serverName = "localhost";
    private int serverPort = 4444;


    SpringLayout myLayout = new SpringLayout();
    JLabel lblTitle, lblTopic, lblQN, lblA, lblB, lblC, lblD, lblE, lblAnswer, lblMessage;
    JTextField txtTopic, txtQnNumber, txtA, txtB, txtC, txtD, txtE, txtAnswer;
    JTextArea txtQn;
    JButton btnSubmit, btnExit, btnConnect;


    public static void main(String[] args)
    {
        JFrame myFrame = new SecondScreen();
        myFrame.setSize(350,400);
        myFrame.setLocation(1,1);
        myFrame.setResizable(false);
        myFrame.setVisible(true);
    }

    public SecondScreen()
    {
        setTitle("Survey Questions");
        setLayout(myLayout);
        CreatingAJLabel();
        CreatingAJTextField();
        CreatingAJButton();
        setVisible(true);
    }

    public void CreatingAJLabel()
    {
        lblTitle = ComponentBuilder.CreateALabel("Enter Your Answer And Click Submit", 60,20,myLayout,this);
        lblTopic = ComponentBuilder.CreateALabel("Topic:", 40, 70, myLayout, this);
        lblQN = ComponentBuilder.CreateALabel("Qn:", 45, 125, myLayout, this);
        lblA = ComponentBuilder.CreateALabel("1:", 50, 155, myLayout, this);
        lblB = ComponentBuilder.CreateALabel("2:", 50, 180, myLayout, this);
        lblC = ComponentBuilder.CreateALabel("3:", 50, 205, myLayout, this);
        lblD = ComponentBuilder.CreateALabel("4:", 50, 230, myLayout, this);
        lblE = ComponentBuilder.CreateALabel("5:", 50, 255, myLayout, this);
        lblAnswer = ComponentBuilder.CreateALabel("Your Answer:",40, 290, myLayout,this);
        lblMessage = ComponentBuilder.CreateALabel("lblMessage:",10, 345, myLayout,this);

    }


    public void CreatingAJTextField()
    {
        txtTopic = ComponentBuilder.CreateATextFieldUnEditable(189, 22, 105, 70, myLayout, this);
        txtQn = ComponentBuilder.CreateATextAreaUnEditable(189, 60, 105, 94, myLayout, this);
        txtQnNumber = ComponentBuilder.CreateATextFieldUnEditable(20, 22, 45, 98, myLayout, this);
        txtA = ComponentBuilder.CreateATextFieldUnEditable(189, 22, 105, 156, myLayout, this);
        txtB = ComponentBuilder.CreateATextFieldUnEditable(189, 22, 105, 180, myLayout, this);
        txtC = ComponentBuilder.CreateATextFieldUnEditable(189, 22, 105, 204, myLayout, this);
        txtD = ComponentBuilder.CreateATextFieldUnEditable(189, 22, 105, 228, myLayout, this);
        txtE = ComponentBuilder.CreateATextFieldUnEditable(189, 22, 105, 252, myLayout, this);
        txtAnswer = ComponentBuilder.CreateATextFieldEditable(50, 19, 130, 290, myLayout, this);

    }

    public void CreatingAJButton()
    {
        btnSubmit = ComponentBuilder.CreateAButton("Submit", 100,22,10,320,this,myLayout,this );
        btnExit = ComponentBuilder.CreateAButton("Exit", 100,22,225,320,this,myLayout,this );
        btnConnect = ComponentBuilder.CreateAButton("Connect", 100,22,117,320,this,myLayout,this );
    }

    public void actionPerformed(ActionEvent e)
    {
        if (e.getSource() == btnSubmit)
        {
            send();
            txtAnswer.requestFocus();
        }

        if (e.getSource() == btnExit)
        {
            System.exit(0);
        }

        if (e.getSource() == btnConnect)
        {
            connect(serverName, serverPort);
        }
    }

    public void connect(String serverName, int serverPort)
    {
        println("Establishing connection. Please wait ...");
        try
        {
            socket = new Socket(serverName, serverPort);
            println("Connected: " + socket);
            open();
        }
        catch (UnknownHostException uhe)
        {
            println("Host unknown: " + uhe.getMessage());
        }
        catch (IOException ioe)
        {
            println("Unexpected exception: " + ioe.getMessage());
        }
    }

    private void send() {
        try {
            // Send both the question number and the answer, separated by a delimiter (e.g., ";")
            String messageToSend = txtQnNumber.getText() + ";" + txtAnswer.getText();
            streamOut.writeUTF(messageToSend);
            streamOut.flush();
            txtAnswer.setText("");
        } catch (IOException ioe) {
            println("Sending error: " + ioe.getMessage());
            close();
        }
    }

    public void handle(String msg)
    {
        if (msg.equals(".bye"))
        {
            println("Good bye. Press EXIT button to exit ...");
            close();
        }
        else
        {
            String[] data = msg.split(";");

            if (data.length >= 8) {
                txtTopic.setText(data[0]);
                txtQn.setText(data[1]);
                txtQnNumber.setText(data[2]);
                txtA.setText(data[3]);
                txtB.setText(data[4]);
                txtC.setText(data[5]);
                txtD.setText(data[6]);
                txtE.setText(data[7]);
            } else {
                println("Answer Sent");
            }
        }
    }

    public void open()
    {
        try
        {
            streamOut = new DataOutputStream(socket.getOutputStream());
            client2 = new SecondScreenThread(this, socket);
        }
        catch (IOException ioe)
        {
            println("Error opening output stream: " + ioe);
        }
    }

    public void close()
    {
        try
        {
            if (streamOut != null)
            {
                streamOut.close();
            }
            if (socket != null)
            {
                socket.close();
            }
        }
        catch (IOException ioe)
        {
            println("Error closing ...");
        }
        client2.close();
        client2.stop();
    }

    void println(String msg)
    {
        //display.appendText(msg + "\n");
        lblMessage.setText(msg);
    }

    public void getParameters()
    {
        serverName = "localhost";
        serverPort = 4444;
    }
}
