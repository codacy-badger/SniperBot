package core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import utils.STATICS;

/**
 * Created by ROMVoid
 * � ROMVoid 2019
 */

public class SSSS /* Stands for "SERVER SPECIFIC SETTINGS SYSTEM" :^) */ {

    public static void listSettings(MessageReceivedEvent event) {

        Guild g = event.getGuild();

        HashMap<String, String> sets = new HashMap<>();
        sets.put("PREFIX                ", getPREFIX(g));
        sets.put("SERVER_JOIN_MSG       ", getSERVERJOINMESSAGE(g));
        sets.put("SERVER_LEAVE_MSG      ", getSERVERJOINMESSAGE(g));
        sets.put("AUTOROLE              ", getAUTOROLE(g));
        sets.put("VKICK_CHANNEL         ", getVKICKCHANNEL(g));

        HashMap<String, String> setsMulti = new HashMap<>();
        setsMulti.put("PERMROLES_LVL1", String.join(", ", getPERMROLES_1(g)));
        setsMulti.put("PERMROLES_LVL2", String.join(", ", getPERMROLES_2(g)));
        setsMulti.put("BLACKLIST", String.join(", ", getBLACKLIST(g)));

        StringBuilder sb = new StringBuilder()
                .append("**GUILD SPECIFIC SETTINGS**\n\n")
                .append("```")
                .append("SETTINGS KEY            -  VALUE\n\n");

        sets.forEach((k, v) -> sb.append(
                String.format("%s  -  \"%s\"\n", k, v))
        );

        sb.append("\n- - - - -\n\n");
        setsMulti.forEach((k, v) -> sb.append(
                String.format("%s:\n\"%s\"\n\n", k, v))
        );

        event.getTextChannel().sendMessage(new EmbedBuilder().setDescription(sb.append("```").toString()).build()).queue();
    }

    public static String getPREFIX(Guild guild) {
        String out = Main.getMySql().getString("guilds", "prefix", "id", guild.getId());
        if (out == null)
            return STATICS.PREFIX;
        return out;
    }

    public static void setPREFIX(String entry, Guild guild) {
        Main.getMySql().setString("guilds", "prefix", entry, "id", guild.getId());
    }


    public static String getSERVERJOINMESSAGE(Guild guild) {

        String out = Main.getMySql().getString("guilds", "joinmsg", "id", guild.getId());
        if (out == null)
            return "OFF";
        return out;
    }

    public static void setSERVERJOINMESSAGE(String entry, Guild guild) {
        Main.getMySql().setString("guilds", "joinmsg", entry, "id", guild.getId());
    }


    public static String getSERVERLEAVEMESSAGE(Guild guild) {

        String out = Main.getMySql().getString("guilds", "leavemsg", "id", guild.getId());
        if (out == null)
            return "OFF";
        return out;
    }

    public static void setSERVERLEAVEMESSAGE(String entry, Guild guild) {
        Main.getMySql().setString("guilds", "leavemsg", entry, "id", guild.getId());
    }


    public static String[] getPERMROLES_1(Guild guild) {

        String out = Main.getMySql().getString("guilds", "perm1", "id", guild.getId());
        if (out == null)
            return STATICS.PERMS;
        return out.split(",");

    }

    public static void setPERMROLES_1(String entry, Guild guild) {
        Main.getMySql().setString("guilds", "perm1", entry.replace(", ", ","), "id", guild.getId());
    }


    public static String[] getPERMROLES_2(Guild guild) {

        String out = Main.getMySql().getString("guilds", "perm2", "id", guild.getId());
        if (out == null)
            return STATICS.FULLPERMS;
        return out.split(",");
    }

    public static void setPERMROLES_2(String entry, Guild guild) {
        Main.getMySql().setString("guilds", "perm2", entry.replace(", ", ","), "id", guild.getId());
    }


    public static String getAUTOROLE(Guild guild) {
        String out = Main.getMySql().getString("guilds", "autorole", "id", guild.getId());
        if (out == null)
            return "";
        return out;
    }

    public static void setAUTOROLE(String entry, Guild guild) {
        Main.getMySql().setString("guilds", "autorole", entry, "id", guild.getId());
    }


    public static String getVKICKCHANNEL(Guild guild) {
        String out = Main.getMySql().getString("guilds", "vkick", "id", guild.getId());
        if (out == null)
            return "";
        return out;
    }

    public static void setVKICKCHANNEL(String entry, Guild guild) {
        Main.getMySql().setString("guilds", "vkick", entry, "id", guild.getId());
    }


    public static String getR6OPSID(Guild guild) {
        String out = Main.getMySql().getString("guilds", "rsops", "id", guild.getId());
        if (out == null)
            return "OFF";
        return out;
    }

    public static void setR6OPSID(String entry, Guild guild) {
        Main.getMySql().setString("guilds", "rsops", entry, "id", guild.getId());
    }

    public static List<String> getBLACKLIST(Guild guild) {

        String out = Main.getMySql().getString("guilds", "blacklist", "id", guild.getId());
        if (out == null)
            return new ArrayList<>();
        return Arrays.asList(out.split(","));
    }

    public static void setBLACKLIST(List<String> entry, Guild guild) {
        Main.getMySql().setString("guilds", "blacklist", entry.stream().collect(Collectors.joining(",")), "id", guild.getId());
    }

}
