package commands.etc;

import java.awt.Color;
import java.io.IOException;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;
import commands.Command;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import utils.STATICS;

/**
 * Created by ROMVoid © ROMVoid 2019
 */

public class Listroles implements Command {


    String[] roles = {
            "Anthem Bois",
            "Destiny Raiders",
            "Apex Gamers"
    };

    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        return false;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event) throws ParseException, IOException {

        Guild g = event.getGuild();
        MessageChannel channel = event.getTextChannel();

        if (!g.getId().equals("164924117600960514"))
            return;

        StringBuilder game = new StringBuilder();
        
        Arrays.stream(roles).forEach(s -> game.append("**" + s + "**\n").append("Useage -->  `" + s.toLowerCase().replace(" ", "")  + "` \n "));
        
        if (args.length < 1) {
            EmbedBuilder eb = new EmbedBuilder()
                    .setColor(Color.cyan)
                    .setTitle("__Joinable Role List__")
                    .setDescription("role name after the --> is what you type.\n double click the name and copy for faster use.")
                    .addField("⁣        ", game.toString(), false);
            Message mymsg = channel.sendMessage(eb.build()).complete();
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    mymsg.delete().queue();
                }
            }, 10000);
        } 
        
        event.getMessage().delete().queue();

    }

    @Override
    public void executed(boolean success, MessageReceivedEvent event) {

    }

    @Override
    public String help() {
        return null;
    }

    @Override
    public String description() {
        return null;
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
