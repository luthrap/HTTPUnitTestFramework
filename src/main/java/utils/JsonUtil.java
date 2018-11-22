package utils;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import static utils.FileUtil.*;

public class JsonUtil {

    public static Map<Integer,Map<String,String>> getJsonDataFromFile(String fileName)
    {
        Map<Integer,Map<String,String>> jsonData = new HashMap<Integer, Map<String, String>>();

        File file = new File(fileName);
        String content;
        try{
            content = FileUtils.readFileToString(file, "utf-8");
        }catch (FileNotFoundException fileNotFound){
            fileNotFound.printStackTrace();
            return null;
        }catch(IOException ioException){
            ioException.printStackTrace();
            return null;
        }
        JSONArray jsonArray = new JSONArray(content);

        for(int i = 0; i < jsonArray.length(); i++)
        {
            Map<String,String> jsonTemp = new HashMap<String, String>();
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            String key;
            Iterator<String> keyIterator = jsonObject.keys();
            while(keyIterator.hasNext()) {
                key = keyIterator.next();
                String value = String.valueOf(jsonObject.get(key));
                jsonTemp.put(key, value);
            }
            jsonData.put(i+1,jsonTemp);
        }
    return jsonData;
    }

    public static Map<String,Map<Integer,Map<String,String>>> getJsonDataFromDirectory(String directory)
    {
        Map<String,Map<Integer,Map<String,String>>>  jsonData = new HashMap<String, Map<Integer,Map<String,String>>> ();
        Set<File> fileList = listFileTree(directory);
        Map<Integer,Map<String,String>> fileJsonData = null;

        for(File file : fileList) {
            fileJsonData = null;
            fileJsonData = getJsonDataFromFile(file.getAbsolutePath());
            if (fileJsonData != null && fileJsonData.size() > 0) {
                jsonData.put(FilenameUtils.getBaseName(file.getName()), fileJsonData);
            }
        }
        return jsonData;
    }

    public static void main(String[] args) throws Exception{
        Map<Integer,Map<String,String>> abc;
        abc = getJsonDataFromFile("/Users/paras.luthra/Downloads/ServiceTestAutomation/src/test/resources/StatusCode.json");
        System.out.println(abc);

        Map<String,Map<Integer,Map<String,String>>> def;
        def = getJsonDataFromDirectory("/Users/paras.luthra/Downloads/ServiceTestAutomation/src/test/resources");
        System.out.println(def);

    }
}
