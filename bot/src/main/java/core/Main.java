package core;

import java.awt.Color;
import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import javax.security.auth.login.LoginException;
import commands.Command;
import commands.chat.Clear;
import commands.chat.Count;
import commands.chat.Counter;
import commands.chat.EightBall;
import commands.chat.Quote;
import commands.chat.Say;
import commands.essentials.Info;
import commands.essentials.Ping;
import commands.essentials.Stats;
import commands.essentials.UserInfo;
import commands.etc.BotStats;
import commands.etc.Bug;
import commands.etc.CmdLog;
import commands.etc.Join;
import commands.etc.Leave;
import commands.etc.Listroles;
import commands.etc.Log;
import commands.etc.Speedtest;
import commands.etc.Uptime;
import commands.serverAdministration.Autochannel;
import commands.serverAdministration.Ban;
import commands.serverAdministration.Kick;
import commands.serverAdministration.Moveall;
import commands.serverAdministration.Mute;
import commands.serverAdministration.Report;
import commands.serverAdministration.VoiceKick;
import commands.settings.AutoRole;
import commands.settings.Prefix;
import listeners.AutochannelHandler;
import listeners.BotGuildJoinListener;
import listeners.BotListener;
import listeners.GuildJoinListener;
import listeners.MuteHanlder;
import listeners.PrivateMessageListener;
import listeners.ReactionListener;
import listeners.ReadyListener;
import listeners.ReconnectListener;
import listeners.VkickListener;
import listeners.VoiceChannelListener;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.exceptions.RateLimitedException;
import utils.STATICS;


public class Main {

    static JDABuilder builder;

    public static final CommandParser parser = new CommandParser();

    public static HashMap<String, Command> commands = new HashMap<>();

    public static JDA jda;

    private static MySql mySql;

    public static void main(String[] args) throws IOException, InterruptedException, RateLimitedException {

        StartArgumentHandler.args = args;

        SettingsCore.loadSettings();

        mySql = new MySql(STATICS.SQL_HOST, STATICS.SQL_PORT, STATICS.SQL_USER, STATICS.SQL_PASS, STATICS.SQL_DB).initialize();

        BotStats.load();

        builder = new JDABuilder(AccountType.BOT)
                .setToken(STATICS.TOKEN)
                .setAudioEnabled(true)
                .setAutoReconnect(true)
                .setStatus(STATICS.STATUS)
                .setGame(STATICS.GAME);

        initializeListeners();
        initializeCommands();

        try {
            builder.build();
        } catch (LoginException e) {
            e.printStackTrace();
        }

    }

    private static void initializeCommands() {

        commands.put("ping", new Ping());
        commands.put("8ball", new EightBall());
        commands.put("clear", new Clear());
        commands.put("purge", new Clear());
        //commands.put("help", new Help());
        commands.put("info", new Info());
        commands.put("say", new Say());
        commands.put("stats", new Stats());
        commands.put("userinfo", new UserInfo());
        commands.put("user", new UserInfo());
        commands.put("kick", new Kick());
        commands.put("vkick", new VoiceKick());
        commands.put("moveall", new Moveall());
        commands.put("mvall", new Moveall());
        commands.put("uptime", new Uptime());
        commands.put("prefix", new Prefix());
        //commands.put("permlvl", new PermLvls());
        commands.put("ban", new Ban());
        commands.put("autorole", new AutoRole());
        commands.put("settings", new commands.settings.Settings());
        commands.put("cmdlog", new CmdLog());
        commands.put("speedtest", new Speedtest());
        commands.put("quote", new Quote());
        commands.put("mute", new Mute());
        commands.put("log", new Log());
        commands.put("report", new Report());
        commands.put("bug", new Bug());
        commands.put("suggestion", new Bug());
        commands.put("botstats", new BotStats());
        commands.put("count", new Count());
        commands.put("counter", new Counter());
        commands.put("c", new Counter());
        commands.put("autochannel", new Autochannel());
        commands.put("guild", new Stats());
        commands.put("join", new Join());
        commands.put("listroles", new Listroles());
        commands.put("leave", new Leave());
    }

    private static void initializeListeners() {

        builder .addEventListener(new ReadyListener())
                .addEventListener(new BotListener())
                .addEventListener(new ReconnectListener())
                .addEventListener(new VoiceChannelListener())
                .addEventListener(new GuildJoinListener())
                .addEventListener(new PrivateMessageListener())
                .addEventListener(new ReactionListener())
                .addEventListener(new VkickListener())
                .addEventListener(new AutochannelHandler())
                .addEventListener(new MuteHanlder())
                .addEventListener(new BotGuildJoinListener());
    }

    public static void handleCommand(CommandParser.CommandContainer cmd) throws ParseException, IOException {

        if (commands.containsKey(cmd.invoke.toLowerCase())) {

            BotStats.commandsExecuted++;
            boolean safe = commands.get(cmd.invoke.toLowerCase()).called(cmd.args, cmd.event);

            if (!safe) {
                try {
                    commands.get(cmd.invoke.toLowerCase()).action(cmd.args, cmd.event);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                commands.get(cmd.invoke.toLowerCase()).executed(safe, cmd.event);
            } else {
                commands.get(cmd.invoke.toLowerCase()).executed(safe, cmd.event);
            }

            List<TextChannel> tcs = cmd.event.getGuild().getTextChannelsByName("cmdlog", true);
            if (tcs.size() > 0) {
                User author = cmd.event.getAuthor();
                EmbedBuilder eb = new EmbedBuilder()
                        .setColor(Color.orange)
                        .setAuthor(author.getName() + "#" + author.getDiscriminator(), null, author.getAvatarUrl())
                        .setDescription("```" + cmd.event.getMessage().getContentRaw() + "```")
                        .setFooter(CoreCommands.getCurrentSystemTime(), null);
                tcs.get(0).sendMessage(eb.build()).queue();
            }
        }
    }

    public static MySql getMySql() {
        return mySql;
    }

}
