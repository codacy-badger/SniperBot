package utils;

import java.util.ArrayList;
import java.util.Date;
import net.dv8tion.jda.core.OnlineStatus;
import net.dv8tion.jda.core.entities.Game;

public class STATICS {

    public static String TOKEN = "";

    //######### GENERAL BOT SETTINGS #########//


    public static String VERSION = "1.0.0";

    public static String PREFIX = "-";

    public static OnlineStatus STATUS = OnlineStatus.ONLINE;

    public static String CUSTOM_MESSAGE = "Systems Optimal, Connetected";

    public static Game GAME = Game.playing(CUSTOM_MESSAGE);

    //######### PERMISSION SETTINGS #########//

    public static String[] PERMS = {"Bot Owner", "Moderator", "Admin", "Owner"};
    public static String[] FULLPERMS = {"Admin", "Owner"};

    public static String guildJoinRole = "";

    //########### OTHER SETTINGS ###########//

    public static String voiceLogChannel = "voicelog";

    public static boolean commandConsoleOutout = true;

    public static String KICK_VOICE_CHANNEL = "";


    public static boolean autoUpdate = true;

    public static String input;

    public static String discordJoinMessage = "Welcome";

    public class CMDTYPE {
        public static final String administration = "Bot Administration";
        public static final String chatutils = "Chat Utilities";
        public static final String essentials = "Essentials";
        public static final String etc = "Etc";
        public static final String guildadmin = "Guild Administration";
        public static final String settings = "SettingsCore";
    }

    public static Date lastRestart;

    public static int reconnectCount = 0;

    public static ArrayList<ArrayList<String>> cmdLog = new ArrayList<>();

    public static long BOT_OWNER_ID = 0;

    public static String SQL_HOST = "";
    public static String SQL_PORT = "";
    public static String SQL_USER = "";
    public static String SQL_PASS = "";
    public static String SQL_DB = "";
}
