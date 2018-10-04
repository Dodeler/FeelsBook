package cdodic.feelsbook;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

//https://stackoverflow.com/questions/22446359/android-class-parcelable-with-arraylist
public class FeelingList implements Parcelable {
    private List<Feeling> feelings;
    public FeelingList(){
        feelings = new ArrayList<Feeling>();

    }
    public FeelingList(Parcel p){
        feelings = p.readArrayList(Feeling.class.getClassLoader());
    }
    public void add(Feeling f){
        feelings.add(f);
    }
    public List<Feeling> getFeelings(){
        return feelings;
    }
    public int size(){
        return feelings.size();
    }
    public Feeling get(int i){
        return feelings.get(i);
    }
    @Override
    public int describeContents(){
        return 0;
    }
    @Override
    public void writeToParcel(Parcel dest, int flags){
        dest.writeList(feelings);
    }
    public static Creator<FeelingList> CREATOR = new Creator<FeelingList>(){
        @Override
        public FeelingList createFromParcel(Parcel source){
            return new FeelingList(source);
        }
        @Override
        public FeelingList[] newArray(int size){
            return new FeelingList[size];
        }
    };
//    private void writeObject(ObjectOutputStream oos) throws IOException {
//        oos.defaultWriteObject();
//        oos.writeObject()
//    }

}
