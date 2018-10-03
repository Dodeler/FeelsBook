package cdodic.feelsbook;

import java.util.Date;

public class Love extends Feeling {
    protected void setFeelingType(){
        super.feeling_type = "Love";
    }
    public Love(Date d){
        super(d);
    }
    public Love(Date d, String c){
        super(d,c);
    }
}
