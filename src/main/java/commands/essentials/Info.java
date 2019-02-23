package commands.essentials;

import java.awt.Color;

import commands.Command;
import core.UpdateClient;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import utils.STATICS;

public class Info implements Command {
    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        return false;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event) {

        String version = "NOT AVAILABLE";
        try {

            version = UpdateClient.PRE.tag;

        } catch (Exception e) {
            e.printStackTrace();
        }
        event.getTextChannel().sendMessage(
                new EmbedBuilder()
                        .setColor(Color.MAGENTA)
                        .setThumbnail("https://avatars3.githubusercontent.com/u/42848897?s=460&v=4")
                        .setDescription("__**SniperBot** JDA Discord Bot__")
                        .addField("Current Version", STATICS.VERSION, true)
                        .addField("Latest Version", version, true)
                        .addField("Copyright © 2019 | Apache License",
                                "By ROMVoid", false)
                        .addField("Information and Links",
                                "GitHub Repository: \n*https://github.com/ROMVoid95/SniperBot.git*\n\n" +
                                      "Github Profile: \n*https://github.com/ROMVoid95*", false)
                        .addField("Libraries and Dependencies",
                                " -  JDA  *(https://github.com/DV8FromTheWorld/JDA)*\n"
                              + " -  JSpeedTest  *(https://github.com/bertrandmartel/speed-test-lib)*\n", false)
                        .addField("Bug Reporting / Idea Suggestion",
                                "please contact me here:\n" +
                                      " - **Please use this document to report a Bug or suggest an idea: \n" +
                                      " -  (Temporarily Offline) https://romvoid.site/thevoid**\n" +
                                      " -  E-Mail:  rom.void95@gmail.com\n" +
                                      " -  Discord:  https://discord.gg/uymYHMp (or directly: `ROMVoid#1909`)", false)
                        .build())
        				.queue();
    }

    @Override
    public void executed(boolean success, MessageReceivedEvent event) {

    }

    @Override
    public String help() {
        return "USAGE: -info";
    }

    @Override
    public String description() {
        return "Get info about the bot";
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
