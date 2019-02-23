package listeners;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.VoiceChannel;
import net.dv8tion.jda.core.events.channel.voice.VoiceChannelDeleteEvent;
import net.dv8tion.jda.core.events.guild.voice.GuildVoiceJoinEvent;
import net.dv8tion.jda.core.events.guild.voice.GuildVoiceLeaveEvent;
import net.dv8tion.jda.core.events.guild.voice.GuildVoiceMoveEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

/**
 * Created by ROMVoid
 * � ROMVoid 2019
 */

public class AutochannelHandler extends ListenerAdapter {


    List<VoiceChannel> active = new ArrayList<>();


    @Override
    public void onGuildVoiceJoin(GuildVoiceJoinEvent event) {
        HashMap<VoiceChannel, Guild> autochans = commands.serverAdministration.Autochannel.getAutochans();
        VoiceChannel vc = event.getChannelJoined();
        Guild g = event.getGuild();
        String num = "0";
        int nNum = Integer.parseInt(num.substring(1,num.length())+1);

        if (autochans.containsKey(vc)) {
            VoiceChannel nvc = (VoiceChannel) g.getController().createVoiceChannel(vc.getName() + " " + ++nNum)
                    .setBitrate(vc.getBitrate())
                    .setUserlimit(vc.getUserLimit())
                    .complete();
            System.out.println(vc.getParent());

            if (vc.getParent() != null)
                nvc.getManager().setParent(vc.getParent()).queue();

            g.getController().modifyVoiceChannelPositions().selectPosition(nvc).moveTo(vc.getPosition() + 1).queue();
            g.getController().modifyVoiceChannelPositions().selectPosition(nvc).moveTo(vc.getPosition() + 1).queue();
            g.getController().moveVoiceMember(event.getMember(), nvc).queue();
            active.add(nvc);
        }
    }

    @Override
    public void onGuildVoiceMove(GuildVoiceMoveEvent event) {
        HashMap<VoiceChannel, Guild> autochans = commands.serverAdministration.Autochannel.getAutochans();
        Guild g = event.getGuild();

        VoiceChannel vc = event.getChannelJoined();

        if (autochans.containsKey(vc)) {
            VoiceChannel nvc = (VoiceChannel) g.getController().createVoiceChannel(vc.getName() + " [AC]")
                    .setBitrate(vc.getBitrate())
                    .setUserlimit(vc.getUserLimit())
                    .complete();

            if (vc.getParent() != null)
                nvc.getManager().setParent(vc.getParent()).queue();

            g.getController().modifyVoiceChannelPositions().selectPosition(nvc).moveTo(vc.getPosition() + 1).queue();
            g.getController().moveVoiceMember(event.getMember(), nvc).queue();
            active.add(nvc);
        }

        vc = event.getChannelLeft();

        if (active.contains(vc) && vc.getMembers().size() == 0) {
            active.remove(vc);
            vc.delete().queue();
        }
    }

    @Override
    public void onGuildVoiceLeave(GuildVoiceLeaveEvent event) {
        VoiceChannel vc = event.getChannelLeft();

        if (active.contains(vc) && vc.getMembers().size() == 0) {
            active.remove(vc);
            vc.delete().queue();
        }
    }

    public void onVoiceChannelDelete(VoiceChannelDeleteEvent event) {
        HashMap<VoiceChannel, Guild> autochans = commands.serverAdministration.Autochannel.getAutochans();
        if (autochans.containsKey(event.getChannel())) {
            commands.serverAdministration.Autochannel.unsetChan(event.getChannel());
        }

    }

}
