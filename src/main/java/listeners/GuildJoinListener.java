package listeners;

import core.SSSS;
import net.dv8tion.jda.core.entities.PrivateChannel;
import net.dv8tion.jda.core.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.core.events.guild.member.GuildMemberLeaveEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

/**
 * Created by ROMVoid
 * © ROMVoid 2019
 */

public class GuildJoinListener extends ListenerAdapter {

    public void onGuildMemberJoin(GuildMemberJoinEvent event) {

        if (event.getMember().getUser().isBot()) return;

        if (!SSSS.getSERVERJOINMESSAGE(event.getGuild()).toLowerCase().equals("off")) {
            event.getGuild().getTextChannelsByName("general", true).get(0).sendMessage(
                SSSS.getSERVERJOINMESSAGE(event.getGuild()).replace("[USER]", event.getMember().getAsMention()).replace("[GUILD]", event.getGuild().getName())
            ).queue();
        }

        if (!SSSS.getAUTOROLE(event.getGuild()).equals("")) {

            event.getGuild().getController().addRolesToMember(event.getMember(), event.getGuild().getRolesByName(SSSS.getAUTOROLE(event.getGuild()), true)).queue();

            PrivateChannel pc = event.getMember().getUser().openPrivateChannel().complete();
            pc.sendMessage(
                    "**Hey,** " + event.getMember().getAsMention() + " **and welcome on the " + event.getGuild().getName() + " Discord server!**   :wave:\n\n" +
                         "You automatically got assigned the server role `" + SSSS.getAUTOROLE(event.getGuild()) + "` by me.\n\n" +
                         "Now, have a nice day and a lot of fun on the server! ;)"
            ).queue();
        }
    }

    public void onGuildMemberLeave(GuildMemberLeaveEvent event) {

        if (event.getMember().getUser().isBot()) return;

        if (!SSSS.getSERVERLEAVEMESSAGE(event.getGuild()).toLowerCase().equals("off")) {
            event.getGuild().getTextChannelsByName("general", true).get(0).sendMessage(
                    SSSS.getSERVERLEAVEMESSAGE(event.getGuild()).replace("[USER]", event.getMember().getAsMention()).replace("[GUILD]", event.getGuild().getName())
            ).queue();
        }

    }


}
