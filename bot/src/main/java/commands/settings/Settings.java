package commands.settings;

import java.io.IOException;
import java.text.ParseException;

import commands.Command;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import utils.STATICS;

/**
 * Created by ROMVoid
 * © ROMVoid 2019
 */

public class Settings implements Command {



    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        return false;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event) throws ParseException, IOException {

        if (core.Perms.check(2, event)) return;
        core.SSSS.listSettings(event);

    }

    @Override
    public void executed(boolean success, MessageReceivedEvent event) {

    }

    @Override
    public String help() {
        return "USING:\n" +
               "**settings**  -  `List all current Settings for the server`";
    }

    @Override
    public String description() {
        return "List all current Settings values of the current guild";
    }

    @Override
    public String commandType() {
        return STATICS.CMDTYPE.settings;
    }

    @Override
    public int permission() {
        return 0;
    }
}
