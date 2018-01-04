package TestBotDiscord.TestDiscordBot;

import TestBotDiscord.TestDiscordBot.*;
import com.jagrosh.jdautilities.commandclient.CommandClientBuilder;
import com.jagrosh.jdautilities.commandclient.examples.AboutCommand;
import com.jagrosh.jdautilities.commandclient.examples.PingCommand;
import com.jagrosh.jdautilities.commandclient.examples.ShutdownCommand;
import com.jagrosh.jdautilities.waiter.EventWaiter;
import java.awt.Color;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import javax.security.auth.login.LoginException;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.OnlineStatus;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.Game;
import net.dv8tion.jda.core.exceptions.RateLimitedException;
/**
 *
 * @author John Grosh (john.a.grosh@gmail.com)
 */
public class Main
{
    
    public static void main(String[] args) throws IOException, LoginException, IllegalArgumentException, RateLimitedException
    {
        EventWaiter waiter = new EventWaiter();
        CommandClientBuilder client = new CommandClientBuilder();
        //client.useDefaultGame();
        client.setEmojis("\uD83D\uDE03", "\uD83D\uDE2E", "\uD83D\uDE26");
        client.setPrefix("+");
        client.setOwnerId("398530380510265344");
        client.setGame(Game.of("twitter.com/lolnikcho"));
        client.setStatus(OnlineStatus.DO_NOT_DISTURB);

        client.addCommands(
                new RestartCommand(),
                new TestCommand(waiter));

        new JDABuilder(AccountType.BOT).setToken("Mzk4NTMwMzgwNTEwMjY1MzQ0.DS_3_Q.qLYs-_RE-kMhY2y57YqtmasEuxA")
        		.setStatus(OnlineStatus.DO_NOT_DISTURB)
                .setGame(Game.of("twitter.com/lolnikcho"))
                .addEventListener(waiter)
                .addEventListener(client.build())
                .buildAsync();
    }
}
