import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;

public class SurveyByNetworkTableModel extends AbstractTableModel
{
    final private static String[] columnNames = new String[] {"#","Topic","Question"};
    ArrayList<SurveyByNetworkRecords> records;

    public SurveyByNetworkTableModel(ArrayList<SurveyByNetworkRecords> records)
    {
        this.records = records;
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    @Override
    public int getRowCount() {
        return records.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        SurveyByNetworkRecords record = this.records.get(rowIndex);

        switch (columnIndex) {
            case 0: return record.getQnNumber();
            case 1: return record.getTopic();
            case 2: return record.getQuestion();
            case 3: return record.getA();
            case 4: return record.getB();
            case 5: return record.getC();
            case 6: return record.getD();
            case 7: return record.getE();
            default: return null;
        }
    }
    public String getTopicByQnNumber(int qnNumber) {
        for (SurveyByNetworkRecords surveyRecord : records) {
            if (surveyRecord.getQnNumber().equals(qnNumber)) {
                return surveyRecord.getTopic();
            }
        }
        return null;
    }


}
