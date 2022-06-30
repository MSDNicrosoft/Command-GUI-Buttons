package cn.msdnicrosoft.commandbuttons;

import com.cedarsoftware.util.io.JsonWriter;
import com.google.gson.Gson;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class ConfigFile {

    private static final JSONParser parser = new JSONParser();
    private static FileWriter fileWriter;

    // Read commands.json, convert it to an array, and append A JSON OBJECT
    public static void appendToFile(JSONObject jsonObject) {
        try {
            Object obj = parser.parse(new FileReader("commands.json"));
            JSONArray array = (JSONArray) obj;
            array.add(jsonObject);
            writeToFile(array);
        } catch (IOException e) {
            System.out.println("Commands.json doesn't exist. Creating one...!");
            JSONArray jsonArray = new JSONArray();
            jsonArray.add(jsonObject);
            writeToFile(jsonArray);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public static void removeFromFile(JSONObject jsonObject) {
        try {
            Object obj = parser.parse(new FileReader("commands.json"));
            JSONArray array = (JSONArray) obj;
            array.remove(jsonObject);
            writeToFile(array);
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    // overwrites current commands.json w/ updated jsonArray
    private static void writeToFile(JSONArray jsonArray) {
        try {
            fileWriter = new FileWriter("commands.json");
            String jArrayToString = new Gson().toJson(jsonArray);
            String formattedJson = JsonWriter.formatJson(jArrayToString);
            fileWriter.write(formattedJson);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                fileWriter.flush();
                fileWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // parse commands.json into array, loop through array and add JSONObjects. returns array
    public static ArrayList<JSONObject> getArrayFromJsonFile() {
        ArrayList<JSONObject> commandObjects = new ArrayList<>();
        try {
            // assign array to JSONArray using our commands.json as an object
            Object obj = parser.parse(new FileReader("commands.json"));
            JSONArray array = (JSONArray) obj;
            // so "array" is now a JSONArray full of JSONObjects
            // now we will iterate through the array and add COs to our local CO array
            for (int i = 0; i < array.size(); i++) {
                JSONObject childObject = (JSONObject) array.get(i);
                commandObjects.add(childObject);
                if (i >= 19) break;
            }
            return commandObjects;
        } catch (IOException | ParseException e) {
            System.out.println("commands.json not yet initialized!");
        }
        return null;
    }

    public static void addObjectToCommList(JSONObject jsonObject) {
        ArrayList<JSONObject> commListCopy = CommandButtons.getMasterCommList();
        commListCopy.add(jsonObject);
        CommandButtons.setMasterCommList(commListCopy);
    }

    public static void removeObject(JSONObject objToRemove) {
        // get masterCommList and remove object from list
        ArrayList<JSONObject> commListCopy = CommandButtons.getMasterCommList();
        commListCopy.remove(objToRemove);

        // get commands.json
        // remove corresponding button from json
        removeFromFile(objToRemove);

    }


}
