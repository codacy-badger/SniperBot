package listeners;

import java.io.IOException;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import core.StartArgumentHandler;
import core.UpdateClient;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.events.ReadyEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import utils.Logger;
import utils.STATICS;


public class ReadyListener extends ListenerAdapter {

    static ReadyEvent readyEvent;

    private static void handleStartArgs() {

        String[] args = StartArgumentHandler.args;

        if (args.length > 0) {
            switch (args[0]) {

                case "-restart":
                    for (Guild g : readyEvent.getJDA().getGuilds()) {
                        g.getDefaultChannel().sendMessage(
                                ":ok_hand:  Bot successfully restarted!"
                        ).queue();
                    }
                    break;

                case "-update":
                    for (Guild g : readyEvent.getJDA().getGuilds()) {
                        g.getDefaultChannel().sendMessage(
                                ":ok_hand:  Bot successfully updated to version v." + STATICS.VERSION + "!\n\n"
                        ).queue();
                    }
                    break;
            }
        }

    }

    @Override
    public void onReady(ReadyEvent event) {

        StringBuilder sb = new StringBuilder();
        event.getJDA().getGuilds().forEach(guild -> sb.append("|  - \"" + guild.getName() + "\" - \n| \t ID:    " + guild.getId() + " \n| \t Owner: " + guild.getOwner().getUser().getName() + "#" + guild.getOwner().getUser().getDiscriminator() + " \n|\n"));

        System.out.println(String.format(
                "\n\n" +
                "#--------------------------------------------------------------------------------\n" +
                "| %s - v.%s (JDA: v.%s)\n" +														
                "#--------------------------------------------------------------------------------\n" +
                "| Running on %s guilds: \n" +
                "%s" +
                "#--------------------------------------------------------------------------------\n\n",
        Logger.Cyan + Logger.Bold + "SniperBot" + Logger.Reset, STATICS.VERSION, "3.8.1_454", event.getJDA().getGuilds().size(), sb.toString()));

        if (STATICS.BOT_OWNER_ID == 0) {
            Logger.ERROR(
                    "#######################################################\n" +
                    "# PLEASE INSERT YOUR DISCORD USER ID IN SETTINGS.TXT  #\n" +
                    "# AS ENTRY 'BOT_OWNER_ID' TO SPECIFY THAT YOU ARE THE #\n" +
                    "# BOTS OWNER!                                         #\n" +
                    "#######################################################"
            );
        }

        readyEvent = event;

        STATICS.lastRestart = new Date();


        handleStartArgs();


        if (STATICS.autoUpdate)
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    UpdateClient.checkIfUpdate(event.getJDA());
                }
            }, 0, 60000);

        commands.chat.Counter.loadAll(event.getJDA());
        commands.serverAdministration.Autochannel.load(event.getJDA());
        try {
			commands.serverAdministration.Mute.load();
		} catch (IOException e) {
			e.printStackTrace();
		}

    }
}
