package accountabilibuddy.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Serialization {

    public static final String FILE_PATH = new File("").getAbsolutePath();

    /**
     * Modularized serialize function
     * @param object - Object to be serialized
     * @param newFileLocation - File location where the serialized object will be contained
     * 							Note: File will be extension .ser
     */
    public static void serialize(Object object, String newFileLocation){
        try{
            FileOutputStream fileout = new FileOutputStream(FILE_PATH + newFileLocation);
            ObjectOutputStream out = new ObjectOutputStream(fileout);
            out.writeObject(object);
            out.close();
            fileout.close();
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    /**
     * Modularized deseralize function
     * @param URLFileLocation - Location of .ser file
     * @return
     */
    public static Object deserialize(String URLFileLocation){
        Object object = null;
        try {
            FileInputStream fileIn = new FileInputStream(FILE_PATH + URLFileLocation);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            object = in.readObject();
            in.close();
            fileIn.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return object;
    }

}
