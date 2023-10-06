public class SurveyByNetworkRecords
{
    Integer qnNumber;
    String topic;
    String question;
    String a;
    String b;
    String c;
    String d;
    String e;

    @Override
    public String toString() {
        return "SurveyByNetworkRecords{" +
                ", QuestionNumber='" + qnNumber  + '\'' +
                ", Topic='" + topic + '\'' +
                ", Question='" + question + '\'' +
                ", A=" + a +
                ", B=" + b +
                ", C=" + c +
                ", D=" + d +
                ", E=" + e +
                '}';
    }

    public SurveyByNetworkRecords(Integer qnNumber, String topic, String question, String a, String b, String c, String d, String e)
    {
        this.qnNumber = qnNumber;
        this.topic = topic;
        this.question = question;
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
        this.e = e;
    }

    // Necessary for sorting testing
    public SurveyByNetworkRecords(Integer qnNumber)
    {
        this.qnNumber = qnNumber;
    }

    public Integer getQnNumber() {
        return qnNumber;
    }

    public void setQnNumber(Integer qnNumber) {
        this.qnNumber = qnNumber;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getA() {
        return a;
    }

    public void setA(String a) {
        this.a = a;
    }

    public String getB() {
        return b;
    }

    public void setB(String b) {
        this.b = b;
    }

    public String getC() {
        return c;
    }

    public void setC(String c) {
        this.c = c;
    }

    public String getD() {
        return d;
    }

    public void setD(String d) {
        this.d = d;
    }

    public String getE() {
        return e;
    }

    public void setE(String e) {
        this.e = e;
    }


}
