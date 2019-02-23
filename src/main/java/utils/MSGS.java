package utils;

import java.awt.Color;
import java.util.Timer;
import java.util.TimerTask;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.MessageEmbed.Field;

/**
 * Created by ROMVoid © ROMVoid 2019
 */

public class MSGS {

    public static EmbedBuilder success() {
        return new EmbedBuilder().setColor(Color.green);
    }

    public static EmbedBuilder error() {
        return new EmbedBuilder().setColor(Color.red);
    }


    public static void sendEmbed(String content, MessageChannel channel, Color color) {
        EmbedBuilder embed = new EmbedBuilder().setDescription(content).setColor(color);
        Message mymsg = channel.sendMessage(embed.build()).complete();
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                mymsg.delete().queue();
            }
        }, 10000);
    }
    
    public static void sendEmbedWithImg(String title, Field drmsg, MessageChannel channel, Color color, String url){
        EmbedBuilder embed = new EmbedBuilder().setTitle(title).addField(drmsg).setColor(color).setThumbnail(url);
        Message mymsg = channel.sendMessage(embed.build()).complete();
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                mymsg.delete().queue();
            }
        }, 10000);
    }

}
