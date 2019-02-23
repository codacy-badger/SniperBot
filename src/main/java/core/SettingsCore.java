package core;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.stream.Collectors;
import org.json.JSONObject;
import utils.STATICS;

/**
 * Created by ROMVoid
 * © ROMVoid 2019
 */

public class SettingsCore {


    public static void initialize() {

        JSONObject main = new JSONObject();

        JSONObject mysql = new JSONObject()
                .put("host", "")
                .put("port", "")
                .put("username", "")
                .put("password", "")
                .put("database", "");

        main    .put("token", "")
                .put("prefix", "-")
                .put("ownerid", "")
                .put("updateinfo", "")
                .put("mysql", mysql);

        try {
            BufferedWriter br = new BufferedWriter(new FileWriter("SETTINGS.json"));
            br.write(main.toString());
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void loadSettings() throws IOException {

        if (!new File("SETTINGS.json").exists()) {
            initialize();
            System.out.println("Please open \"SETTINGS.json\" file and enter your discord token and owner ID there!");
            System.exit(-1);
        } else {

            BufferedReader br = new BufferedReader(new FileReader("SETTINGS.json"));
            String out = br.lines().collect(Collectors.joining("\n"));

            JSONObject obj = new JSONObject(out);

            STATICS.TOKEN           = obj.getString("token");
            STATICS.PREFIX          = obj.getString("prefix");
            STATICS.BOT_OWNER_ID    = Long.parseLong(obj.getString("ownerid"));
            STATICS.autoUpdate      = Boolean.parseBoolean(obj.getString("updateinfo"));

            JSONObject mysql = obj.getJSONObject("mysql");

            STATICS.SQL_HOST = mysql.getString("host");
            STATICS.SQL_PORT = mysql.getString("port");
            STATICS.SQL_USER = mysql.getString("username");
            STATICS.SQL_PASS = mysql.getString("password");
            STATICS.SQL_DB = mysql.getString("database");
            
            br.close();

        }

    }

}
