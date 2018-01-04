package TestBotDiscord.TestDiscordBot;

import java.util.concurrent.TimeUnit;

import com.jagrosh.jdautilities.commandclient.Command;
import com.jagrosh.jdautilities.commandclient.CommandEvent;
import com.jagrosh.jdautilities.waiter.EventWaiter;

import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class RestartCommand extends Command {
	
    public static final String TADA = "\uD83C\uDF89";
    public RestartCommand() {
        this.name = "bugreport";
        this.aliases = new String[]{"bug"};
        this.help = "you can send a bug to our staff";
        this.arguments = "information about the bug";
    }
	
	protected void execute(CommandEvent event) {
		
		TextChannel channel = event.getTextChannel();
		
		if(event.getArgs().length() < 0) {
			event.replyError("Please include a information about this bug.");
		} else {
			if(channel.getName().equalsIgnoreCase("bug-reports")) {
				event.getChannel().sendMessage("Bug report from " + event.getMember() + " has been send to us `information` " + event.getArgs());
			}
		}
	}
}
