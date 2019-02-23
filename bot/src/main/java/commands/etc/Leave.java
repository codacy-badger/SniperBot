package commands.etc;

import java.awt.Color;
import java.io.IOException;
import java.text.ParseException;
import commands.Command;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.MessageEmbed.Field;
import net.dv8tion.jda.core.entities.Role;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import utils.Logger;
import utils.MSGS;
import utils.STATICS;

/**
 * Created by ROMVoid © ROMVoid 2019
 */

public class Leave implements Command {


    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        return false;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event)
            throws ParseException, IOException {

        MessageChannel channel = event.getChannel();
        Member m = event.getMember();
        Guild g = event.getGuild();
        Role anthemBois;
        Role apexGamers;
        Role destinyRaiders;

        destinyRaiders = g.getRoleById("547877300725612544");
        anthemBois = g.getRoleById("543671897137872896");
        apexGamers = g.getRoleById("547879041911488537");
        Field drmsg = new Field("You Left Destiny Raiders!", "To re-join use  `-join <role>`", false);
        Field abmsg = new Field("You Left Anthem Bois!", "To re-join use  `-join <role>`", false);
        Field agmsg = new Field("You Left Apex Gamers!", "To re-join use  `-join <role>`", false);

        if (args.length == 1) {

            
            switch (args[0]) {

                case "destinyraiders":
                    g.getController().removeSingleRoleFromMember(m, destinyRaiders).queue();
                    MSGS.sendEmbedWithImg(m.getEffectiveName().toString() + "#" + m.getUser().getDiscriminator().toString(), drmsg, channel, Color.white, "https://i.imgur.com/JWCUxyx.png");
                    break;
                case "anthembois":
                    g.getController().removeSingleRoleFromMember(m, anthemBois).queue();
                    MSGS.sendEmbedWithImg(m.getEffectiveName().toString() + "#" + m.getUser().getDiscriminator().toString(), abmsg, channel, Color.white, "https://i.imgur.com/AQU8Rb8.png");
                    break;
                case "apexgamers":
                    g.getController().removeSingleRoleFromMember(m, apexGamers).queue();
                    MSGS.sendEmbedWithImg(m.getEffectiveName().toString() + "#" + m.getUser().getDiscriminator().toString(), agmsg, channel, Color.white, "https://i.imgur.com/tjE3lHO.png");
                    break;

                default:
                    MSGS.sendEmbed(m.getAsMention() + " Uh Oh, \n I would check your spelling or be sure that role exists. \n\n Type `-listroles` to check available roles.", channel, Color.CYAN);
                    return;
            }
            event.getMessage().delete().queue();

        }
    }

    @Override
    public void executed(boolean success, MessageReceivedEvent event) {
        
        Logger.SUCCESS(event);

    }

    @Override
    public String help() {
        return "**USAGE:** `-join <role>`";
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
