//Source:
//  Creating a simple Chat Client1/Server Solution 
//  http://pirate.shu.edu/~wachsmut/Teaching/CSAS2214/Virtual/Lectures/chat-client-server.html

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

public class FirstScreenThread extends Thread
{
    private Socket socket = null;
    private FirstScreen firstScreen = null;
    private DataInputStream streamIn = null;

    public FirstScreenThread(FirstScreen _firstScreen, Socket _socket) {
        firstScreen = _firstScreen;
        socket = _socket;
        open();
        start();
    }

    public void open() {
        try
        {
            streamIn = new DataInputStream(socket.getInputStream());
        }
        catch (IOException ioe)
        {
            System.out.println("Error getting input stream: " + ioe);
            //client1.stop();
            firstScreen.close();
        }
    }

    public void close() {
        try
        {
            if (streamIn != null)
            {
                streamIn.close();
            }
        }
        catch (IOException ioe)
        {
            System.out.println("Error closing input stream: " + ioe);
        }
    }

    public void run() {
        while (true)
        {
            try
            {
                firstScreen.handle(streamIn.readUTF());
            }
            catch (IOException ioe)
            {
                System.out.println("Listening error: " + ioe.getMessage());
                //client1.stop();
                firstScreen.close();
            }
        }
    }
}
