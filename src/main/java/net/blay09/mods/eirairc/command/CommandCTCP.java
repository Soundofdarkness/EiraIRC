package net.blay09.mods.eirairc.command;

import net.blay09.mods.eirairc.api.EiraIRCAPI;
import net.blay09.mods.eirairc.api.SubCommand;
import net.blay09.mods.eirairc.api.event.ChatMessageEvent;
import net.blay09.mods.eirairc.api.irc.IRCContext;
import net.blay09.mods.eirairc.api.irc.IRCUser;
import net.blay09.mods.eirairc.config.settings.BotBooleanComponent;
import net.blay09.mods.eirairc.config.settings.BotSettings;
import net.blay09.mods.eirairc.util.ConfigHelper;
import net.blay09.mods.eirairc.util.MessageFormat;
import net.blay09.mods.eirairc.util.Utils;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.util.IChatComponent;
import net.minecraftforge.common.MinecraftForge;

import java.util.List;

/**
 * @author soniex2
 */
public class CommandCTCP implements SubCommand {

    @Override
    public String getCommandName() {
        return "ctcp";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "eirairc:irc.commands.ctcp";
    }

    @Override
    public String[] getAliases() {
        return null;
    }

    @Override
    public boolean processCommand(ICommandSender sender, IRCContext context, String[] args, boolean serverSide) throws CommandException {
        if (!ConfigHelper.getBotSettings(context).getBoolean(BotBooleanComponent.AllowCTCP)) {
            Utils.sendLocalizedMessage(sender, "irc.ctcp.disabled");
            return true;
        }
        if (args.length < 2) {
            throw new WrongUsageException(getCommandUsage(sender));
        }
        IRCContext target = EiraIRCAPI.parseContext(null, args[0], null);
        if (target.getContextType() == IRCContext.ContextType.Error) {
            Utils.sendLocalizedMessage(sender, target.getName(), args[0]);
            return true;
        } else if (target.getContextType() == IRCContext.ContextType.IRCUser) {
            if (!ConfigHelper.getBotSettings(context).getBoolean(BotBooleanComponent.AllowPrivateMessages)) {
                Utils.sendLocalizedMessage(sender, "irc.msg.disabled");
                return true;
            }
        }
        String message = Utils.joinStrings(args, " ", 1).trim();
        if (message.isEmpty()) {
            throw new WrongUsageException(getCommandUsage(sender));
        }
        String format = "{MESSAGE}";
        BotSettings botSettings = ConfigHelper.getBotSettings(target);
        IRCUser botUser = target.getConnection().getBotUser();

        String ircMessage = message;
        if (serverSide) {
            ircMessage = MessageFormat.formatMessage(format, target.getConnection(), target, botUser, message, MessageFormat.Target.IRC, MessageFormat.Mode.Message);
        }
        target.ctcpMessage(ircMessage);

        format = "{MESSAGE}";
        if (target.getContextType() == IRCContext.ContextType.IRCChannel) {
            format = botSettings.getMessageFormat().mcSendChannelMessage;
        } else if (target.getContextType() == IRCContext.ContextType.IRCUser) {
            format = botSettings.getMessageFormat().mcSendPrivateMessage;
        }

        IChatComponent chatComponent = MessageFormat.formatChatComponent(format, target.getConnection(), target, botUser, message, MessageFormat.Target.IRC, MessageFormat.Mode.Message);
        MinecraftForge.EVENT_BUS.post(new ChatMessageEvent(sender, chatComponent));
        return true;
    }

    @Override
    public boolean canCommandSenderUseCommand(ICommandSender sender) {
        return true;
    }

    @Override
    public void addTabCompletionOptions(List<String> list, ICommandSender sender, String[] args) {
    }

    @Override
    public boolean isUsernameIndex(String[] args, int idx) {
        return false;
    }

    @Override
    public boolean hasQuickCommand() {
        return false;
    }
}