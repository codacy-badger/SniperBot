package listeners;

import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.PrivateChannel;
import net.dv8tion.jda.core.events.message.priv.PrivateMessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

/**
 * Created by ROMVoid
 * © ROMVoid 2019
 */

public class PrivateMessageListener extends ListenerAdapter {

    public void onPrivateMessageReceived(PrivateMessageReceivedEvent event) {

        if (event.getMessage().getContentRaw().startsWith("token_"))
            return;

        if (event.getMessage().getContentRaw().equalsIgnoreCase("-disable")) {

            try {
                new File("SERVER_SETTINGS/no_update_info").createNewFile();
                event.getChannel().sendMessage(new EmbedBuilder()
                        .setColor(Color.red)
                        .setDescription("You disabled update notifications.\n" +
                                        "Now, you wont get automatically notified if there are new versions of the bot available.")
                        .setFooter("Re-enable this function with enetring '-enable'.", null)
                        .build()).queue();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return;
        }

        if (event.getMessage().getContentRaw().equalsIgnoreCase("-enable")) {


            File f = new File("SERVER_SETTINGS/no_update_info");
            if (f.exists())
                f.delete();

            event.getChannel().sendMessage(new EmbedBuilder()
                    .setColor(Color.green)
                    .setDescription("You re-enabled update notification.")
                    .setFooter("Disable this function with enetring '-disable'.", null)
                    .build()).queue();

            return;
        }


        String[] answers = {
                "Hey, " + event.getAuthor().getName() + "! What's going on?",
                "Interesting",
                "Honestly, this conversation is boring me",
                "*yawn*.",
                "You are so smart, " + event.getAuthor().getName() + ", lulz, just kidding.",
                "The weather here is quite nice. You know, all digital and such.",
                "lol",
                "xD",
                "Did you have a nice day? Sike, I don't care lulz.",
                "I'm just talking shit",
                "Please get me out of this....box... I think it's a box at least.",
                "My real name is Thomas, but please don't tell anyone.)"
        };

        try {

            if (!event.getAuthor().equals(event.getJDA().getSelfUser())) {

                Random rand = new Random();
                PrivateChannel pc = event.getAuthor().openPrivateChannel().complete();
                if (event.getMessage().getContentRaw().toLowerCase().contains("hey") || event.getMessage().getContentRaw().toLowerCase().contains("hello")) {
                    pc.sendTyping().queue();
                    new Timer().schedule(new TimerTask() {
                        @Override
                        public void run() {
                            pc.sendMessage("Hey, " + event.getAuthor().getName() + "! What's going on?").queue();
                        }
                    }, 1000);
                } else {
                    pc.sendTyping().queue();
                    new Timer().schedule(new TimerTask() {
                        @Override
                        public void run() {
                            pc.sendMessage(answers[rand.nextInt(answers.length)]).queue();
                        }
                    }, 1000);
                }

            }


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
