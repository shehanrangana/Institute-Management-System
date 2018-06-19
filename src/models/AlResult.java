package models;

import javafx.beans.property.SimpleStringProperty;

public class AlResult {
    private SimpleStringProperty stream;
    private SimpleStringProperty subject1;
    private SimpleStringProperty result1;
    private SimpleStringProperty subject2;
    private SimpleStringProperty result2;
    private SimpleStringProperty subject3;
    private SimpleStringProperty result3;
    private int rank;
    private double zScore;
    
    public AlResult(String stream, String subject1, String result1, String subject2, String result2, String subject3, String result3, int rank, double zScore){
        this.stream = new SimpleStringProperty(stream);
        this.subject1 = new SimpleStringProperty(subject1);
        this.result1 = new SimpleStringProperty(result1);
        this.subject2 = new SimpleStringProperty(subject2);
        this.result2 = new SimpleStringProperty(result2);
        this.subject3 = new SimpleStringProperty(subject3);
        this.result3 = new SimpleStringProperty(result3);
        this.rank = rank;
        this.zScore = zScore;
    }

    public String getStream() {
        return stream.get();
    }

    public String getSubject1() {
        return subject1.get();
    }

    public String getResult1() {
        return result1.get();
    }

    public String getSubject2() {
        return subject2.get();
    }

    public String getResult2() {
        return result2.get();
    }

    public String getSubject3() {
        return subject3.get();
    }

    public String getResult3() {
        return result3.get();
    }

    public int getRank() {
        return rank;
    }

    public double getzScore() {
        return zScore;
    }
    
    
}
