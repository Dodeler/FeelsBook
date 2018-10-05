package cdodic.feelsbook;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.Date;

public class Feeling implements Parcelable, Serializable, Comparable<Feeling> {
    // Feeling class
    // holds information about a recorded feelings:
    // date (timestamp)
    // comment
    // feeling (feeling_type)
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

    protected void setFeelingType(String feeling){
        feeling_type = feeling;
    }

    public String getFeeling_type(){
        return feeling_type;
    }

    protected void setTime(int h, int m){
        timestamp.setHours(h);
        timestamp.setMinutes(m);
    }

    protected void setDate(int year, int month, int day){
        timestamp.setYear(year);
        timestamp.setMonth(month);
        timestamp.setDate(day);
    }

    public Feeling(Date d, String f){
        timestamp = d;
        setFeelingType(f);
        comment = "[No Comment]";
    }

    public Feeling(Date d, String f, String c){
        timestamp = d;
        setFeelingType(f);
        comment = c;

    }


    // Implementation for parcelable and serializable
    public Feeling(Parcel p){
        timestamp = new Date(p.readString());
        feeling_type = p.readString();
        comment = p.readString();
    }

    @Override
    public int describeContents(){
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags){
        dest.writeString(timestamp.toString());
        dest.writeString(feeling_type);
        dest.writeString(comment);
    }

    public static Creator<Feeling> CREATOR = new Creator<Feeling>(){
        @Override
        public Feeling createFromParcel(Parcel source){
            return new Feeling(source);
        }

        @Override
        public Feeling[] newArray(int size){
            return new Feeling[size];
        }
    };

    //Implementing comparable
    public int compareTo(Feeling compare_feeling){
        return this.getTimestamp().compareTo(((Feeling)compare_feeling).getTimestamp());
    }
}
