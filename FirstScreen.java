import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.PatternSyntaxException;
import java.net.*;
import java.io.*;


public class FirstScreen extends JFrame implements ActionListener, MouseListener {


    private Socket socket = null;
    private DataInputStream console = null;
    private DataOutputStream streamOut = null;
    private FirstScreenThread firstScreenThread = null;
    private String serverName = "localhost";
    private int serverPort = 4444;

    static int numberOfAssociatedWords = 50;
    static int currentAssocWord = 0;
    //  static AssocData wordList[] = new AssocData[numberOfAssociatedWords];

    private int totalResponses = 0;
    private int numberOfResponses = 0;

    private HashMap<String, ArrayList<Integer>> surveyAnswers = new HashMap<>();
    HashMap<Integer, ResponseData> responseMap = new HashMap<>();

    SpringLayout myLayout = new SpringLayout();
    String[][] DataArray = new String[100][5];
    DoublyLinkedList DList = new DoublyLinkedList();
    //  int currentEntry = 0;
    FileManager file = new FileManager();
    // DefaultTableModel model;

    MyModel model;
    private SurveyByNetworkTableModel tableModel;

    ArrayList<Object[]> records;
    SurveyByNetworkRecords focusRecord;
    SurveyByNetworkRecords recordPanelRecord;
    Integer row;
    boolean newRecord = false;


    JLabel lblSearchQN, lblSurveyQN, lblSortBy, lblLinkedList, lblBinaryTree, lblPre_O, lblIn_O, lblPost_O, lblMessage, lblTopic, lblQN, lblA, lblB, lblC, lblD, lblE;
    JButton btnQn, btnTopic, btnQuestion, btnExit, btnConnect, btnSend, btnDisplayPre_O, btnDisplayIn_O, btnDisplayPost_O, btnDisplay, btnSave, btnSearch;
    JTextField txtSearchQN, txtTopic, txtQnNumber, txtA, txtB, txtC, txtD, txtE;
    JTextArea txtLinkedList, txtBinaryTree, txtQn;
    JTable tblSurveyQNS, tblStaffScreen;


    public static void main(String[] args) {
        JFrame myFrame = new FirstScreen();
        myFrame.setSize(600, 560);
        myFrame.setLocation(1000, 250);
        myFrame.setResizable(false);
        myFrame.setVisible(true);
    }

    public FirstScreen() {
        setLayout(myLayout);
        CreatingAJLabel();
        CreatingAJTextField();
        CreatingAJButton();
        CreatingATextArea();
        CreatingAJTable();
        setVisible(true);
        getParameters();

    }

    private void CreatingAJTable() {

        tblStaffScreen = ComponentBuilder.CreateATable(250, 220, 320, 10, myLayout, this);

        String[] column = {"#", "Topic", "Question"};
        FileDialog fd = new FileDialog(this, "Choose a file to load", FileDialog.LOAD);
        fd.setVisible(true);
        records = file.LoadRecordList(fd.getDirectory() + fd.getFile());
        tblSurveyQNS = new JTable(DataArray, column);
        tblSurveyQNS.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        tblSurveyQNS.getColumnModel().getColumn(0).setPreferredWidth(30);
        tblSurveyQNS.getColumnModel().getColumn(1).setPreferredWidth(100);
        tblSurveyQNS.getColumnModel().getColumn(2).setPreferredWidth(300);
        tblSurveyQNS.setColumnSelectionAllowed(true);
        tblSurveyQNS.setRowSelectionAllowed(true);

        // constructor of JTable model
        model = new MyModel(records, column);
        // Create a new table instance
        tblSurveyQNS = new JTable(model);
        tblSurveyQNS.addMouseListener(this);
        JScrollPane spSurveyQNS = new JScrollPane(tblSurveyQNS);
        spSurveyQNS.setPreferredSize(new Dimension(300, 160));
        spSurveyQNS.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        spSurveyQNS.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        myLayout.putConstraint(SpringLayout.WEST, spSurveyQNS, 10, SpringLayout.WEST, this);
        myLayout.putConstraint(SpringLayout.NORTH, spSurveyQNS, 50, SpringLayout.NORTH, this);
        add(spSurveyQNS);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        row = tblSurveyQNS.getSelectedRow();
        populateCreateRecordPanel(row);
        newRecord = false;
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    class MyModel extends AbstractTableModel {
        ArrayList<Object[]> al;

        // the headers
        String[] header;

        // to hold the column index for the Sent column
        int col;

        // constructor
        MyModel(ArrayList<Object[]> obj, String[] header) {
            // save the header
            this.header = header;
            // and the data
            al = obj;
            // get the column index for the Sent column
            col = this.findColumn("Sent");
        }

        // method that needs to be overload. The row count is the size of the ArrayList

        public int getRowCount() {
            return al.size();
        }

        // method that needs to be overload. The column count is the size of our header
        public int getColumnCount() {
            return header.length;
        }

        // method that needs to be overload. The object is in the arrayList at rowIndex
        public Object getValueAt(int rowIndex, int columnIndex) {
            return al.get(rowIndex)[columnIndex];
        }

        // a method to return the column name
        public String getColumnName(int index) {
            return header[index];
        }

        public Class getColumnClass(int columnIndex) {
            if (columnIndex == col) {
                return Boolean.class; // For every cell in column 7, set its class to Boolean.class
            }
            return super.getColumnClass(columnIndex); // Otherwise, set it to the default class
        }

        // a method to add a new line to the table
        void add(String word1, String word2, boolean sent) {
            // make it an array[3] as this is the way it is stored in the ArrayList
            // (not best design but we want simplicity)
            Object[] item = new Object[3];
            item[0] = word1;
            item[1] = word2;
            item[2] = sent;
            al.add(item);
            // inform the GUI that I have change
            fireTableDataChanged();
        }
    }

    private void CreatingATextArea() {
        txtLinkedList = ComponentBuilder.CreateATextAreaEditable(560, 50, 10, 290, myLayout, this);
        txtBinaryTree = ComponentBuilder.CreateATextAreaEditable(560, 50, 10, 380, myLayout, this);
        txtSearchQN = ComponentBuilder.CreateATextFieldEditable(118, 18, 115, 10, myLayout, this);
    }

    private void CreatingAJButton() {
        btnSearch = ComponentBuilder.CreateAButton("Search", 75, 17, 235, 10, this, myLayout, this);
        btnQn = ComponentBuilder.CreateAButton("Qn #", 60, 20, 80, 210, this, myLayout, this);
        btnTopic = ComponentBuilder.CreateAButton("Topic", 70, 20, 145, 210, this, myLayout, this);
        btnQuestion = ComponentBuilder.CreateAButton("Question", 90, 20, 220, 210, this, myLayout, this);
        btnExit = ComponentBuilder.CreateAButton("Exit", 170, 20, 10, 240, this, myLayout, this);
        btnConnect = ComponentBuilder.CreateAButton("Connect", 130, 20, 230, 240, this, myLayout, this);
        btnSend = ComponentBuilder.CreateAButton("Send", 170, 20, 400, 240, this, myLayout, this);
        btnDisplay = ComponentBuilder.CreateAButton("Display", 100, 20, 470, 440, this, myLayout, this);
        btnDisplayPre_O = ComponentBuilder.CreateAButton("Display", 100, 20, 20, 465, this, myLayout, this);
        btnDisplayIn_O = ComponentBuilder.CreateAButton("Display", 100, 20, 168, 465, this, myLayout, this);
        btnDisplayPost_O = ComponentBuilder.CreateAButton("Display", 100, 20, 320, 465, this, myLayout, this);
        btnSave = ComponentBuilder.CreateAButton("Save", 100, 20, 470, 465, this, myLayout, this);
    }

    private void CreatingAJTextField() {
        txtTopic = ComponentBuilder.CreateATextFieldUnEditable(189, 22, 375, 15, myLayout, this);
        txtQn = ComponentBuilder.CreateATextAreaUnEditable(189, 60, 375, 39, myLayout, this);
        txtQnNumber = ComponentBuilder.CreateATextFieldUnEditable(20, 22, 335, 68, myLayout, this);
        txtA = ComponentBuilder.CreateATextFieldUnEditable(189, 22, 375, 101, myLayout, this);
        txtB = ComponentBuilder.CreateATextFieldUnEditable(189, 22, 375, 125, myLayout, this);
        txtC = ComponentBuilder.CreateATextFieldUnEditable(189, 22, 375, 149, myLayout, this);
        txtD = ComponentBuilder.CreateATextFieldUnEditable(189, 22, 375, 173, myLayout, this);
        txtE = ComponentBuilder.CreateATextFieldUnEditable(189, 22, 375, 197, myLayout, this);
    }

    private void CreatingAJLabel() {
        lblSearchQN = ComponentBuilder.CreateALabel("Search Question:", 10, 10, myLayout, this);
        lblSurveyQN = ComponentBuilder.CreateALabel("Survey Questions", 100, 30, myLayout, this);
        lblSortBy = ComponentBuilder.CreateALabel("Sort By:", 20, 210, myLayout, this);
        lblLinkedList = ComponentBuilder.CreateALabel("Linked List:", 10, 270, myLayout, this);
        lblBinaryTree = ComponentBuilder.CreateALabel("Binary Tree:", 10, 360, myLayout, this);
        lblPre_O = ComponentBuilder.CreateALabel("Pre-Order", 40, 440, myLayout, this);
        lblIn_O = ComponentBuilder.CreateALabel("In-Order", 195, 440, myLayout, this);
        lblPost_O = ComponentBuilder.CreateALabel("Post-Order", 340, 440, myLayout, this);
        lblMessage = ComponentBuilder.CreateALabel("lblMessage", 10, 500, myLayout, this);

        lblTopic = ComponentBuilder.CreateALabel("Topic:", 330, 15, myLayout, this);
        lblQN = ComponentBuilder.CreateALabel("Qn:", 335, 40, myLayout, this);
        lblA = ComponentBuilder.CreateALabel("1:", 340, 100, myLayout, this);
        lblB = ComponentBuilder.CreateALabel("2:", 340, 125, myLayout, this);
        lblC = ComponentBuilder.CreateALabel("3:", 340, 150, myLayout, this);
        lblD = ComponentBuilder.CreateALabel("4:", 340, 175, myLayout, this);
        lblE = ComponentBuilder.CreateALabel("5:", 340, 200, myLayout, this);
    }


    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == btnSearch) {
            String text = txtSearchQN.getText();
            TableRowSorter<SurveyByNetworkTableModel> sorter = (TableRowSorter<SurveyByNetworkTableModel>) tblSurveyQNS.getRowSorter();
            if (text.length() == 0) {
                sorter.setRowFilter(null);
            } else {
                try {
                    sorter.setRowFilter(RowFilter.regexFilter(text));
                } catch (PatternSyntaxException pse) {
                    System.out.println("Bad regex pattern");
                }
            }
        }

        if (ae.getSource() == btnQn) {
            InsertionSort.sort(records);
            model.fireTableDataChanged();
        }

        if (ae.getSource() == btnTopic) {
            BubbleSort.sort(records);
            model.fireTableDataChanged();
        }

        if (ae.getSource() == btnQuestion) {
            SelectionSort.sort(records);
            model.fireTableDataChanged();
        }

        if (ae.getSource() == btnExit) {
            System.exit(0);
        }

        if (ae.getSource() == btnConnect) {
            connect(serverName, serverPort);
        }

        if (ae.getSource() == btnSend) {
            send();
            txtTopic.requestFocus();
        }

        if (ae.getSource() == btnDisplay) {

        }

        if (ae.getSource() == btnDisplayPre_O) {

        }

        if (ae.getSource() == btnDisplayIn_O) {

        }

        if (ae.getSource() == btnDisplayPost_O) {

        }

        if (ae.getSource() == btnSave) {

        }
    }

    public class BubbleSort {
        public static void sort(ArrayList<Object[]> records)
        {
            // Convert the records list to a list of SurveyByNetworkRecords
            ArrayList<SurveyByNetworkRecords> surveyRecords = RecordConverter.toSurveyRecords(records);

            // Actual BubbleSort logic to sort surveyRecords by Topic
            int n = surveyRecords.size();
            for (int i = 0; i < n - 1; i++) {
                for (int j = 0; j < n - i - 1; j++) {
                    if (surveyRecords.get(j).getTopic().compareTo(surveyRecords.get(j + 1).getTopic()) > 0) {
                        SurveyByNetworkRecords temp = surveyRecords.get(j);
                        surveyRecords.set(j, surveyRecords.get(j + 1));
                        surveyRecords.set(j + 1, temp);
                    }
                }
            }

            // Convert the sorted surveyRecords list back to records list
            records.clear();
            records.addAll(RecordConverter.toRecordsList(surveyRecords));
        }
    }

    public class InsertionSort {
        public static void sort(ArrayList<Object[]> records) {
            // Convert the records list to a list of SurveyByNetworkRecords
            ArrayList<SurveyByNetworkRecords> surveyRecords = RecordConverter.toSurveyRecords(records);

            // Actual Insertion Sort logic
            for (int i = 1; i < surveyRecords.size(); i++) {
                SurveyByNetworkRecords key = surveyRecords.get(i);
                int j = i - 1;

                // Move elements of surveyRecords[0..i-1] that are greater than key
                // to one position ahead of their current position
                while (j >= 0 && (surveyRecords.get(j).getQnNumber() == null ||
                        (key.getQnNumber() != null && surveyRecords.get(j).getQnNumber() > key.getQnNumber()))) {
                    surveyRecords.set(j + 1, surveyRecords.get(j));
                    j = j - 1;
                }
                surveyRecords.set(j + 1, key);
            }

            // Convert the sorted surveyRecords list back to records list
            records.clear();
            records.addAll(RecordConverter.toRecordsList(surveyRecords));
        }
    }

    public class SelectionSort {
        public static void sort(ArrayList<Object[]> records) {
            // Convert the records list to a list of SurveyByNetworkRecords
            ArrayList<SurveyByNetworkRecords> surveyRecords = RecordConverter.toSurveyRecords(records);

            // Actual Selection Sort logic
            for (int i = 0; i < surveyRecords.size() - 1; i++) {
                int min_idx = i;
                for (int j = i + 1; j < surveyRecords.size(); j++) {
                    if (surveyRecords.get(j).getQuestion().compareTo(surveyRecords.get(min_idx).getQuestion()) < 0) {
                        min_idx = j;
                    }
                }

                SurveyByNetworkRecords temp = surveyRecords.get(min_idx);
                surveyRecords.set(min_idx, surveyRecords.get(i));
                surveyRecords.set(i, temp);
            }

            // Convert the sorted surveyRecords list back to records list
            records.clear();
            records.addAll(RecordConverter.toRecordsList(surveyRecords));
        }
    }

    public class RecordConverter {
        public static ArrayList<SurveyByNetworkRecords> toSurveyRecords(ArrayList<Object[]> records) {
            ArrayList<SurveyByNetworkRecords> surveyRecords = new ArrayList<>();
            for (Object[] record : records) {
                Integer qnNumber = null;
                if (record[0] != null) {
                    try {
                        qnNumber = Integer.parseInt(record[0].toString());
                    } catch (NumberFormatException ex) {
                        qnNumber = null;
                    }
                }
                String topic = (String) record[1];
                String question = (String) record[2];
                String a = record.length > 3 ? (String) record[3] : null;
                String b = record.length > 4 ? (String) record[4] : null;
                String c = record.length > 5 ? (String) record[5] : null;
                String d = record.length > 6 ? (String) record[6] : null;
                String e = record.length > 7 ? (String) record[7] : null;

                surveyRecords.add(new SurveyByNetworkRecords(qnNumber, topic, question, a, b, c, d, e));
            }
            return surveyRecords;
        }

        public static ArrayList<Object[]> toRecordsList(ArrayList<SurveyByNetworkRecords> surveyRecords) {
            ArrayList<Object[]> records = new ArrayList<>();
            for (SurveyByNetworkRecords record : surveyRecords) {
                String qnNumberStr = record.getQnNumber() != null ? record.getQnNumber().toString() : null;
                records.add(new Object[]{
                        qnNumberStr,
                        record.getTopic(),
                        record.getQuestion(),
                        record.getA(),
                        record.getB(),
                        record.getC(),
                        record.getD(),
                        record.getE()
                });
            }
            return records;
        }
    }

    private void updateLinkedList(String topic, int qnNumber, double average) {
        // Add the new node to your doubly linked list
        DList.add(topic, qnNumber, average);

        // Update the txtLinkedList with the new content of the doubly linked list
        txtLinkedList.setText(DList.toString());
    }

    private void populateCreateRecordPanel(int row) {
         Object[] record = records.get(row);

         txtQnNumber.setText(record.length > 0 ? record[0].toString() : "");
         txtTopic.setText(record.length > 1 ? record[1].toString() : "");
         txtQn.setText(record.length > 2 ? record[2].toString() : "");
         txtA.setText(record.length > 3 ? record[3].toString() : "");
         txtB.setText(record.length > 4 ? record[4].toString() : "");
         txtC.setText(record.length > 5 ? record[5].toString() : "");
         txtD.setText(record.length > 6 ? record[6].toString() : "");
         txtE.setText(record.length > 7 ? record[7].toString() : "");
    }

    public void connect(String serverName, int serverPort) {
        println("Establishing connection. Please wait ...");
        try {
            socket = new Socket(serverName, serverPort);
            println("Connected: " + socket);
            open();
        } catch (UnknownHostException uhe) {
            println("Host unknown: " + uhe.getMessage());
        } catch (IOException ioe) {
            println("Unexpected exception: " + ioe.getMessage());
        }
    }

    private void send() {
        try {
            String dataToSend = String.format("%s;%s;%s;%s;%s;%s;%s;%s",
                    txtTopic.getText(),
                    txtQn.getText(),
                    txtQnNumber.getText(),
                    txtA.getText(),
                    txtB.getText(),
                    txtC.getText(),
                    txtD.getText(),
                    txtE.getText());
            streamOut.writeUTF(dataToSend);
            streamOut.flush();
        } catch (IOException ioe) {
            println("Sending error: " + ioe.getMessage());
            close();
        }
    }

    public static boolean isNumeric(String str) {
        String[] parts = str.split(";");
        if (parts.length != 2) {
            return false;
        }
        try {
            Integer.parseInt(parts[0]);
            Integer.parseInt(parts[1]);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    class ResponseData {
        int totalScore = 0;
        int responseCount = 0;

        public void addResponse(int score) {
            totalScore += score;
            responseCount++;
        }

        public double getAverage() {
            return (double) totalScore / responseCount;
        }
    }

    class QuestionResponse {
        int totalScore = 0;
        int responseCount = 0;

        public void addResponse(int score) {
            totalScore += score;
            responseCount++;
        }

        public double getAverage() {
            return (double) totalScore / responseCount;
        }
    }


    public void handleResponse(int qnNumber, int score) {
        // Update the responseMap
        responseMap.computeIfAbsent(qnNumber, k -> new ResponseData()).addResponse(score);


        // Calculate the new average
        double average = responseMap.get(qnNumber).getAverage();

        // Update the DLL
        // (Assuming you have a method in the DLL to update a node. If not, you'll need to implement it.)
        updateDLLWithAverage(qnNumber, average);
    }

    public void displayDLLContent() {
        String content = DList.toString();
        // Assuming you have a JTextArea named linkedListTextArea to display the content
        txtLinkedList.setText(content);
    }

    public void updateDLLWithAverage(int qnNumber, double average) {
        Node current = DList.head;
        while (current != null) {
            if (current.qnNumber == qnNumber) {
                current.average = average;
                return;
            }
            current = current.next;
        }
        // If we reach here, it means the node doesn't exist. So, we add a new node.
        String topic = getTopicByQnNumber(qnNumber);
        if (topic != null) {
            DList.add(topic, qnNumber, average);
        }
    }

    public String getTopicByQnNumber(int qnNumber) {for (int i = 0; i < Math.min(5, records.size()); i++) { // print for the first 5 elements
        Object[] recordArray = records.get(i);
        for (int j = 0; j < recordArray.length; j++) {
            System.out.println("Element at index " + j + " is of type: " + recordArray[j].getClass().getName());
        }
        System.out.println("-----");
    }

        for (Object[] record : records) {
            SurveyByNetworkRecords surveyRecord = (SurveyByNetworkRecords) record[0]; // Assuming the first element is SurveyByNetworkRecords
            if (surveyRecord.getQnNumber().equals(qnNumber)) {
                return surveyRecord.getTopic();
            }
        }
        return null;
    }

    public void handle(String msg) {
        if (msg.equals(".bye")) {
            println("Good bye. Press EXIT button to exit ...");
            close();
        } else {
            // Split the received message into question number and response
            String[] parts = msg.split(";");
            if (parts.length < 2) {
                println("Invalid message format received: " + msg);
                return;
            }

            try {
                int qnNumber = Integer.parseInt(parts[0]);
                int response = Integer.parseInt(parts[1]);

                // Handle the response
                handleResponse(qnNumber, response);

                // Update the display
                displayDLLContent();
            } catch (NumberFormatException e) {
                println("Received non-numeric input in message: " + msg);
            }
        }
    }




/*    public void handle(String msg) {
        if (isNumeric(msg)) {
            println("Handle method called with message: " + msg);

            if (msg.equals(".bye")) {
                println("Good bye. Press EXIT button to exit ...");
                close();
            } else {
                // Split the received message into question number and response
                String[] parts = msg.split(";");
                String qnNum = parts[0];
                int response = Integer.parseInt(parts[1]);

                // Fetch the corresponding question's data using the question number
                SurveyByNetworkRecords currentData = tableModel.getRecordByQnNum(Integer.parseInt(qnNum));

                // Ensure currentData is not null
                if (currentData == null) {
                    println("No data found for question number: " + qnNum);
                    return;
                }

                // Update the total responses and the number of responses
                totalResponses += response;
                numberOfResponses++;

                // Calculate the average value of the selected responses
                double average = (double) totalResponses / numberOfResponses;

                // Ensure DList is initialized
                if (DList == null) {
                    DList = new DoublyLinkedList();
                }

                // Update the doubly linked list
                DList.add(currentData.getTopic(), currentData.getQnNumber().intValue(), average);
                println("Added to DList: " + currentData.getTopic() + ", " + currentData.getQnNumber() + ", " + average);

                // Check if DList has data and update txtLinkedList
                if (DList.head == null) {
                    println("DList is empty.");
                } else {
                    txtLinkedList.setText(DList.toString());
                }
            }
        } else {
            println("Received non-numeric input: " + msg);
        }
    }



*/



    public void open() {
        try {
            streamOut = new DataOutputStream(socket.getOutputStream());
            firstScreenThread = new FirstScreenThread(this, socket);
        } catch (IOException ioe) {
            println("Error opening output stream: " + ioe);
        }
    }

    public void close() {
        try {
            if (streamOut != null) {
                streamOut.close();
            }
            if (socket != null) {
                socket.close();
            }
        } catch (IOException ioe) {
            println("Error closing ...");
        }
        firstScreenThread.close();
        //client.stop();
    }

    void println(String msg) {
        //display.appendText(msg + "\n");
        lblMessage.setText(msg);
    }

    public void getParameters() {
//        serverName = getParameter("host");
//        serverPort = Integer.parseInt(getParameter("port"));

        serverName = "localhost";
        serverPort = 4444;
    }
}