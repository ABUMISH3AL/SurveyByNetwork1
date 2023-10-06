//Source:
//  Creating a simple Chat Client/Server Solution 
//  http://pirate.shu.edu/~wachsmut/Teaching/CSAS2214/Virtual/Lectures/chat-client-server.html

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

public class SecondScreenThread extends Thread
{
    private Socket socket = null;
    private SecondScreen secondScreen = null;
    private DataInputStream streamIn = null;

    public SecondScreenThread(SecondScreen _secondScreen, Socket _socket) {
        secondScreen = _secondScreen;
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
            //client2.stop();
            secondScreen.close();
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
                secondScreen.handle(streamIn.readUTF());
            }
            catch (IOException ioe)
            {
                System.out.println("Listening error: " + ioe.getMessage());
                //client2.stop();
                secondScreen.close();
            }
        }
    }
}
