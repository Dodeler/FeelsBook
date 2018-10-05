package cdodic.feelsbook;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class FeelingList implements Parcelable {
    private List<Feeling> feelings;
    private String[] feeling_type_strings = new String[]{"Love", "Joy", "Fear", "Anger", "Hope", "Sad"};

    public FeelingList(){
        feelings = new ArrayList<Feeling>();
    }

    public FeelingList(Parcel p){
        feelings = p.readArrayList(Feeling.class.getClassLoader());
    }
    //List implementations
    // Note: chose not to subclass list to prevent some operations
    public void add(Feeling f){
        feelings.add(f);
    }

    public int size(){
        return feelings.size();
    }

    public void set(int i, Feeling f){
        feelings.set(i, f);
    }

    public void remove(int i){
        feelings.remove(i);
    }

    public Feeling get(int i){
        return feelings.get(i);
    }

    public void sort(){
        Collections.sort(feelings);
    }

    public List<Feeling> getFeelings(){
        return feelings;
    }

    //collected number of feelings in the list matching the input feeling
    public Integer getFeelingCount(String feeling){
        Integer feeling_count = 0;
        for(int i=0; i<feelings.size(); i++){
            if(feelings.get(i).getFeelingType().equals(feeling)){
                feeling_count += 1;
            }
        }
        return feeling_count;
    }

    //Implementation for parcelable
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

    // information how to read/write to file gathered from
    // https://stackoverflow.com/questions/4118751/how-do-i-serialize-an-object-and-save-it-to-a-file-in-android
    // as well as knowledge from lab

    //Collect feeling list from file (serialized)
    public static FeelingList readFeelings(String filepath){
        FeelingList fl = new FeelingList();
        File file = new File(filepath);
        if(!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        FileInputStream fis = null;
        try {
            fis = new FileInputStream(filepath);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        if (fis == null){

        }
        else {
            try {
                if (fis.available() <= 0){
                }
                else{
                    ObjectInputStream is = null;
                    try {
                        is = new ObjectInputStream(fis);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    try {
                        while(true){
                            try{
                                Feeling f = (Feeling)is.readObject();
                                fl.add(f);
                            }
                            catch (EOFException e){
                                break;
                            }
                        }
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    try {
                        is.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    try {
                        fis.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return fl;
    }

    //Write feelingslist to file (serialized)
    public static void writeFeelings(FeelingList fl, String filepath){
        PrintWriter pw = null;
        try {
            pw = new PrintWriter(filepath);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        pw.close();
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(filepath);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        ObjectOutputStream os = null;
        try {
            os = new ObjectOutputStream(fos);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            for(Feeling f: fl.feelings){
                os.writeObject(f);
                os.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Creates hashmap of feeling types to their count in history (key: feeling_type, value: count)
    public HashMap<String, Integer> getAllFeelingCounts(){
        HashMap<String, Integer> feeling_counts = new HashMap<>();
        for(String s: feeling_type_strings){
            feeling_counts.put(s, getFeelingCount(s));
        }
        return feeling_counts;
    }
}
