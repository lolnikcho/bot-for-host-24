package TestBotDiscord.TestDiscordBot;

import com.jagrosh.jdautilities.commandclient.Command;
import com.jagrosh.jdautilities.commandclient.CommandEvent;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.MessageBuilder;
import net.dv8tion.jda.core.entities.ChannelType;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Game;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageReaction.ReactionEmote;
import net.dv8tion.jda.core.entities.Role;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.VoiceChannel;
import okhttp3.internal.http2.Settings;

import com.jagrosh.jdautilities.commandclient.Command;
import com.jagrosh.jdautilities.commandclient.CommandEvent;
import com.jagrosh.jdautilities.waiter.EventWaiter;

import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

/**
 *
 * @author John Grosh (john.a.grosh@gmail.com)
 */
public class TestCommand extends Command {
	
    public static final String TADA = "\uD83C\uDF89";
	private final EventWaiter waiter;
    public TestCommand(EventWaiter waiter) {
        this.waiter = waiter;
        this.name = "stats";
        this.aliases = new String[]{"hi"};
        this.help = "says hello and waits for a response";
    }
    
	@Override
    /*protected void execute(CommandEvent event) {
		TextChannel channel = (TextChannel) event.getChannel();
    	//Message msg = "Hello, `"+ event.getMessage().getRawContent()+ "`! I'm `" + event.getJDA().getSelfUser().getName()+ "`!";
        event.reply("Hello. What is your name?");
        /*waiter.waitForEvent(MessageReceivedEvent.class, 
                e -> e.getAuthor().equals(event.getAuthor()) && e.getChannel().equals(event.getChannel()), 
                e -> e.getChannel().sendMessage("Hello, `" + e.getMessage().getRawContent()+"`! I'm `" + e.getJDA().getSelfUser().getName() + "`!").queue(),
                1, TimeUnit.MINUTES, () -> event.reply("Sorry, you took too long."));
        */
        /*final EmbedBuilder builder = new EmbedBuilder();
        builder.setColor(event.getGuild().getSelfMember().getColor());
        builder.setAuthor("All about " + event.getSelfUser().getName() + "!", (String)null, event.getSelfUser().getAvatarUrl());
        String descr = "Hello! I am **" + event.getSelfUser().getName() + "**";
        builder.setDescription((CharSequence)descr);
        if (event.getJDA().getShardInfo() == null) {
            builder.addField("Stats", event.getJDA().getGuilds().size() + " servers\n1 shard", true);
            builder.addField("Users", event.getJDA().getUsers().size() + " unique\n" + event.getJDA().getGuilds().stream().mapToInt(g -> g.getMembers().size()).sum() + " total", true);
            builder.addField("Channels", event.getJDA().getTextChannels().size() + " Text\n" + event.getJDA().getVoiceChannels().size() + " Voice", true);
        }
        builder.setFooter("Last restart", (String)null);
        builder.setTimestamp((TemporalAccessor)event.getClient().getStartTime());
        event.reply(builder.build()); 
        if(args)
        
        String asd = "dssss";
        channel.sendMessage(asd).queue(m -> {
            m.addReaction(TADA).queue();
        });
    }*/

		protected void execute(CommandEvent event) {
        if(event.getArgs().isEmpty())
        {
            event.replyError("Please include a member ID, or an `@mention` of a member.");
            return;
        }
        Member member;
        if(event.getMessage().getMentionedUsers().isEmpty())
        {
            try {
                member = event.getGuild().getMemberById(event.getArgs());
            } catch(Exception e) {
                member = null;
            }
        }
        else
            member = event.getGuild().getMember(event.getMessage().getMentionedUsers().get(0));
        if(member==null)
        {
            event.reply(event.getClient().getError()+" Could not find user from `"+event.getArgs()+" `!");
            return;
        }
        String title = (member.getUser().isBot()?"\uD83E\uDD16":"\uD83D\uDC64")+" Information about **"+member.getUser().getName()+"**#"+member.getUser().getDiscriminator();
        EmbedBuilder builder = new EmbedBuilder();
        builder.setColor(member.getColor());
        String roles="";
        roles = member.getRoles().stream().map((rol) -> rol.getName()).filter((r) -> (!r.equalsIgnoreCase("@everyone"))).map((r) -> "`, `"+r).reduce(roles, String::concat);
        if(roles.isEmpty())
            roles="None";
        else
            roles=roles.substring(3)+"`";
        builder.setDescription("Discord ID: **"+member.getUser().getId()+"**\n"
                            + (member.getNickname()==null ? "" : "Nickname: **"+member.getNickname()+"**\n")
                            + "Roles: "+roles+"\n"
                            + "Status: **"+member.getOnlineStatus().name()+"**"+(member.getGame()==null?"":" ("
                                    +(member.getGame().getType()== Game.GameType.STREAMING?"Streaming [*"+member.getGame().getName()+"*]("+member.getGame().getUrl()+")"
                                            :"Playing *"+member.getGame().getName()+"*")+")")+"\n");
        builder.setThumbnail(member.getUser().getEffectiveAvatarUrl());
        event.getChannel().sendMessage(new MessageBuilder().append(title).setEmbed(builder.build()).build()).queue();
    }
    
}
