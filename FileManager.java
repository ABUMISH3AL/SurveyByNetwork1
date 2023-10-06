import javax.swing.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;

public class FileManager extends JComponent
{
    static ArrayList<Object[]> records;
    String filename = "Data.txt";

    public void WriteDataToFile(SurveyByNetworkData[] data) {
        //The try/catch structure attempts to perform the functionality within the try
        //portion of the structure. If something fails and would cause an error or exception
        //The code in the catch portion is executed instead.
        try
        {
            //Creates the BufferedReader object which establishes the connection to the
            //desired file for writing. A file writer is also used in this process to
            // //connect to the file.
            BufferedWriter write = new BufferedWriter(new FileWriter(filename));

            //Iterates through the provided array and processes each element for writing
            //until the end or until it is stopped.
            for (SurveyByNetworkData datum : data) {
                //Checks each element to determine whether it contains data. If empty(null)
                //The if statement executes and triggers the break command which will stop the loop.
                if (datum == null) {
                    break;
                }

                //Writes the data from the current element to the text file in a string format.
                //The data string used contains each piece of contained info separated by a comma.
                write.write(datum.getTopic() + "," + datum.getQnNum() + "," + datum.getQuestion() +",");
                //Moves to a new line before the next entry is processed.
                write.newLine();
            }
            //Closes the connection to file to finalise writing process.
            write.close();
        }
        catch (Exception e)//Exception variable top store info if error occurred.
        {
        }
    }

    public String [][] ReadDataFromFile(String File) {

        //Creates new array to store data entries.
        SurveyByNetworkData[]  data = new SurveyByNetworkData[100];
        //Counter to track which iteration of the loop it is currently on
        int count = 0;
        String[][] tempAnswers = new String[8][8];
        try
        {
            //Creates the BufferedWriter object which establishes the connection to the
            //desired file for reading. A file reader is also used in this process to
            // //connect to the file.
            BufferedReader reader = new BufferedReader(new FileReader(File));
            //Variable to store each line for processing.
            String line;

            //The following while loop performs 2 processes per iteration. First it reads the next
            //line from the file and stores the value into the line variable. Then it checks
            //whether the line variable is null which will be the case on reading a blank line.
            //If not null the loop executes.
            while ((line = reader.readLine()) != null)
            {
                //Creates temporary string array and then splits the line variable
                //based upon the position of the indicated character(comma). The split components
                //Are then assigned in order to the array based upon how many splits occurred.
                //String[] temp = line.;
                //Sets the current count position of the array to be a new PersonData object
                //And passes it the values gained by the splitting of the line above. This should
                //add the data to the array in the same order it would have been written out in.
                for (int i = 0; i < 7; i++)
                {
                    tempAnswers[count][i] =line;
                    line = reader.readLine();

                }
                //Increments the counter for the next iteration.
                count++;
            }
            //Closes the reader
            reader.close();
            //Returns the finalised array to the caller in a completed state

        }
        catch (Exception e)
        {
            //Returns the finalised array to the caller in an incomplete or empty state
            //return data;
            System.out.println( e );
        }
        return tempAnswers;
    }

    public static ArrayList<Object[]> LoadRecordList(String filename) {
    records = new ArrayList<Object[]>();

    try
    {
        FileReader fr = new FileReader(filename);
        BufferedReader br = new BufferedReader(fr);
        String aLine = "";
        aLine = br.readLine();
        while (aLine  != null)
        {
            String line [] = new String[8];
            line [0] = aLine;
            line [1] = br.readLine();
            line [2] = br.readLine();
            line [3] = br.readLine();
            line [4] = br.readLine();
            line [5] = br.readLine();
            line [6] = br.readLine();
            line [7] = br.readLine();

            records.add(line);
            aLine  = br.readLine();
        }
        return records;
    }
    catch(Exception e)
    {
        System.err.println("Failed to load records: " + e.toString());
        return null;
    }

}






































}
