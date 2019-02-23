package commands.etc;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import commands.Command;
import core.Main;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import utils.STATICS;

/**
 * Created by ROMVoid
 * © ROMVoid 2019
 */


public class BotStats implements Command {

    static File f = new File("botstatics.donotdelete");

    public static long messagesProcessed = 0;
    public static long commandsExecuted = 0;

    private int membersDeserving = 0;

    public static void save() {

        if (!f.exists())
            try {
                f.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }

        try {

            BufferedWriter bw = new BufferedWriter(new FileWriter(f));
            bw.write(messagesProcessed + "\n" + commandsExecuted);
            bw.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static long[] stats() {

        if (!f.exists())
            return new long[] {0, 0};

        try {

            BufferedReader br = new BufferedReader(new FileReader(f));
            br.close();
            return new long[] {Long.parseLong(br.readLine()), Long.parseLong(br.readLine())};

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new long[] {0, 0};
        
        
    }

    public static void load() {
        messagesProcessed = stats()[0];
        commandsExecuted = stats()[1];
    }

    private void countUpMembers() {
        membersDeserving++;
    }


    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        return false;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event) throws ParseException, IOException {

        int commandsSize = Main.commands.size();

        event.getJDA().getGuilds().forEach(g -> g.getMembers().forEach(m -> countUpMembers()));

        event.getTextChannel().sendMessage(
                new EmbedBuilder()
                .setColor(Color.cyan)
                .setDescription(
                        "**SniperBot Statistics**\n\n" +
                        "```\n" +
                        "Registered commands:  " + commandsSize + "\n" +
                        "Serving members:      " + membersDeserving + "\n" +
                        "Messages processed:   " + messagesProcessed + "\n" +
                        "Commands executed:    " + commandsExecuted + "\n" +
                        "```"
                ).build()
        ).queue();

        membersDeserving = 0;

    }

    @Override
    public void executed(boolean success, MessageReceivedEvent event) {

    }

    @Override
    public String help() {
        return "**USAGE:**\n`-botstats`";
    }

    @Override
    public String description() {
        return "Displays stats of the bot.";
    }

    @Override
    public String commandType() {
        return STATICS.CMDTYPE.etc;
    }

    @Override
    public int permission() {
        return 0;
    }
}
