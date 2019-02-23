package listeners;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import commands.essentials.Ping;
import commands.etc.BotStats;
import core.CoreCommands;
import core.Main;
import core.SSSS;
import net.dv8tion.jda.core.entities.ChannelType;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import utils.STATICS;

public class BotListener extends ListenerAdapter{


    private void addToLogfile(MessageReceivedEvent e) throws IOException {

        File logFile = new File("CMDLOG.txt");
        BufferedWriter bw = new BufferedWriter(new FileWriter(logFile, true));

        if (!logFile.exists())
            logFile.createNewFile();

        bw.write(String.format("%s [%s (%s)] [%s (%s)] '%s'\n",
                CoreCommands.getCurrentSystemTime(),
                e.getGuild().getName(),
                e.getGuild().getId(),
                e.getAuthor().getName(),
                e.getAuthor().getId(),
                e.getMessage().getContentRaw()));
        bw.close();

    }


    @Override
    public void onMessageReceived(MessageReceivedEvent e) {

        BotStats.messagesProcessed++;

        if (e.getChannelType().equals(ChannelType.PRIVATE)) return;

        if (e.getMessage().getContentRaw().startsWith(SSSS.getPREFIX(e.getGuild())) && e.getMessage().getAuthor().getId() != e.getJDA().getSelfUser().getId()) {
            Ping.setInputTime(new Date().getTime());
                try {
                    Main.handleCommand(Main.parser.parse(e.getMessage().getContentRaw(), e));
                    if (STATICS.commandConsoleOutout)
                        System.out.println(CoreCommands.getCurrentSystemTime() + " [Info] [Commands]: Command '" + e.getMessage().getContentRaw() + "' was executed by '" + e.getAuthor() + "' (" + e.getGuild().getName() + ")!");
                    ArrayList<String> list = new ArrayList<>();
                    list.add(e.getGuild().getId());
                    list.add(CoreCommands.getCurrentSystemTime());
                    list.add(e.getMember().getEffectiveName());
                    list.add(e.getMessage().getContentRaw());
                    STATICS.cmdLog.add(list);
                    addToLogfile(e);
                } catch (ParseException e1) {
                    e1.printStackTrace();
                } catch (IOException e1) {
                    e1.printStackTrace();
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
        }
    }

}

