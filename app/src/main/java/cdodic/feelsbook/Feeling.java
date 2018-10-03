package cdodic.feelsbook;

import java.util.Date;

public abstract class Feeling {
    private Date timestamp;
    private String comment;
    protected String feeling_type;

    public void setTimestamp(Date d){
        timestamp = d;
    }
    public Date getTimestamp(){
        return timestamp;
    }
    public void setComment(String c){
        comment = c;
    }
    public String getComment(){
        return comment;
    }
    protected abstract void setFeelingType();
    public String getFeeling_type(){
        return feeling_type;
    }
    public Feeling(Date d){
        timestamp = d;
        comment = "[No Comment]";
        setFeelingType();
    }
    public Feeling(Date d, String c){
        timestamp = d;
        comment = c;
        setFeelingType();
    }
}
