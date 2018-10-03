package cdodic.feelsbook;

import java.util.Date;

public class Joy extends Feeling {
    protected void setFeelingType(){
        super.feeling_type = "Joy";
    }
    public Joy(Date d){
        super(d);
    }
    public Joy(Date d, String c){
        super(d,c);
    }
}
