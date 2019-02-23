package listeners;

import java.awt.Color;
import java.util.HashMap;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

/**
 * Created by ROMVoid
 * © ROMVoid 2019
 */

public class MuteHanlder extends ListenerAdapter {

    @Override
    public void onMessageReceived(MessageReceivedEvent e) {

        User sender = e.getAuthor();
        HashMap<String, String> mutes = commands.serverAdministration.Mute.getMuted();

        if (mutes.containsKey(sender.getId())) {
            sender.openPrivateChannel().queue(pc -> pc.sendMessage(
                    new EmbedBuilder().setColor(Color.orange).setDescription(
                            "Your not allowed to post in any text channels, you have been muted!\n" +
                            "Please contact a Moderator or Admin to unmute.\n\n" +
                            "Mute Reason: `" + mutes.get(sender.getId()) + "`"
                    ).build()
            ).queue());
            e.getMessage().delete().queue();
        }
    }

}
