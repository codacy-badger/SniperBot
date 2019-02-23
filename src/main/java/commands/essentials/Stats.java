package commands.essentials;

import java.awt.Color;
import java.text.ParseException;
import java.util.List;
import java.util.stream.Collectors;

import commands.Command;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.OnlineStatus;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Role;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import utils.STATICS;

/**
 * Created by ROMVoid
 * © ROMVoid 2019
 */

public class Stats implements Command {

    private class GuildStats {

        private String name, id, region, avatar, roles;
        private int textChans, voiceChans, rolesCount;
        private long all, users, onlineUsers, bots, onlineBots;
        private Member owner;

        private GuildStats(Guild g) {

            List<Member> l = g.getMembers();

            this.name = g.getName();
            this.id = g.getId();
            this.region = g.getRegion().getName();
            this.avatar = g.getIconUrl() == null ? "not set" : g.getIconUrl();
            this.textChans = g.getTextChannels().size();
            this.voiceChans = g.getVoiceChannels().size();
            g.getCategories().size();
            this.rolesCount = g.getRoles().size();
            this.owner = g.getOwner();

            this.all = l.size();
            this.users = l.stream().filter(m -> !m.getUser().isBot()).count();
            this.onlineUsers = l.stream().filter(m -> !m.getUser().isBot() && !m.getOnlineStatus().equals(OnlineStatus.OFFLINE)).count();
            this.bots = l.stream().filter(m -> m.getUser().isBot()).count();
            this.onlineBots = l.stream().filter(m -> m.getUser().isBot() && !m.getOnlineStatus().equals(OnlineStatus.OFFLINE)).count();

            this.roles = g.getRoles().stream()
                    .filter(r -> !r.getName().contains("everyone"))
                    .map(r -> String.format("%s (`%d`)", r.getName(), getMembsInRole(r)))
                    .collect(Collectors.joining(", "));
        }

        long getMembsInRole(Role r) {
            return r.getGuild().getMembers().stream().filter(m -> m.getRoles().contains(r)).count();
        }
    }

    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        return false;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event) throws ParseException {

        Guild g = event.getGuild();

        GuildStats gs = new GuildStats(g);

        String usersText = String.format(
                "**All Clients:**   %d\n" +
                "**Members:**   %d   (Online:  %d)\n" +
                "**Bots:**   %d   (Online:  %d)",
                gs.all, gs.users, gs.onlineUsers, gs.bots, gs.onlineBots
        );

        EmbedBuilder eb = new EmbedBuilder()
                .setColor(Color.cyan)
                .setTitle(gs.name + "  -  SERVER STATS \n")
                .addField("\n **Owner:**", gs.owner.getUser().getName() + "#" + gs.owner.getUser().getDiscriminator(), true)
                .addField("\n **ID:**", gs.id, true)
                .addBlankField(true)
                .addField("**Server Region:**", gs.region, true)
                .addBlankField(false)
                .addField("Channels", "**Text Channels:**  " + gs.textChans + "\n**Voice Channels:**  " + gs.voiceChans, true)
                .addField("Members:", usersText, true)
                .addField("Roles (" + gs.rolesCount + "): ", gs.roles, false)
                .addField("Server Avatar", gs.avatar, false);

        if (!gs.avatar.equals("not set"))
            eb.setThumbnail(gs.avatar);

        event.getTextChannel().sendMessage(eb.build()).queue();
    }

    @Override
    public void executed(boolean success, MessageReceivedEvent event) {

    }

    @Override
    public String help() {
        return "USAGE: -stats";
    }

    @Override
    public String description() {
        return "Get stats of the server";
    }

    @Override
    public String commandType() {
        return STATICS.CMDTYPE.essentials;
    }

    @Override
    public int permission() {
        return 0;
    }
}
