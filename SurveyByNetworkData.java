public class SurveyByNetworkData
{
        private String Topic;
        private String QnNum ;
        private String Question;
        private String A;
        private String B;
        private String C;
        private String D;
        private String E;


        public SurveyByNetworkData(String topic, String qnNum, String question, String a, String b, String c, String d, String e)
        {
            Topic = topic;
            QnNum = qnNum;
            Question = question;
            A = a;
            B = b;
            C = c;
            D = d;
            E = e;
        }

    public String getTopic() { return Topic; }

    public void setTopic(String topic) { Topic = topic; }

    public String getQnNum() { return QnNum; }

    public void setQnNum(String qnNum) { QnNum = qnNum; }

    public String getQuestion() { return Question; }

    public void setQuestion(String question) { Question = question; }

    public String getA() {
        return A;
    }

    public void setA(String a) {
        A = a;
    }

    public String getB() {
        return B;
    }

    public void setB(String b) {
        B = b;
    }

    public String getC() {
        return C;
    }

    public void setC(String c) {
        C = c;
    }

    public String getD() {
        return D;
    }

    public void setD(String d) {
        D = d;
    }

    public String getE() {
        return E;
    }

    public void setE(String e) {
        E = e;
    }


}
