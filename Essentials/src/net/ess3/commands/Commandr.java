package net.ess3.commands;

import net.ess3.Console;
import static net.ess3.I18n._;
import net.ess3.utils.Util;
import net.ess3.api.IReplyTo;
import net.ess3.api.IUser;
import net.ess3.api.server.CommandSender;
import net.ess3.api.server.Player;
import net.ess3.permissions.Permissions;


public class Commandr extends EssentialsCommand
{
	@Override
	protected void run(final CommandSender sender, final String commandLabel, final String[] args) throws Exception
	{
		if (args.length < 1)
		{
			throw new NotEnoughArgumentsException();
		}

		String message = getFinalArg(args, 0);
		IReplyTo replyTo;
		String senderName;

		if (sender instanceof Player)
		{
			IUser user = Player.getUser((Player)sender);
			if (Permissions.MSG_COLOR.isAuthorized(user))
			{
				message = Util.replaceFormat(message);
			}
			else
			{
				message = Util.replaceFormat(message);
			}
			replyTo = user;
			senderName = user.getDisplayName();
		}
		else
		{
			message = Util.replaceFormat(message);
			replyTo = Console.getConsoleReplyTo();
			senderName = Console.NAME;
		}

		final CommandSender target = replyTo.getReplyTo();
		final String targetName = target instanceof Player ? ((Player)target).getDisplayName() : Console.NAME;

		if (target == null || ((target instanceof Player) && !((Player)target).isOnline()))
		{
			throw new Exception(_("foreverAlone"));
		}

		sender.sendMessage(_("msgFormat", _("me"), targetName, message));
		if (target instanceof Player)
		{
			IUser player = player.getUser((Player)target);
			if (sender instanceof Player && player.isIgnoringPlayer(player.getUser((Player)sender)))
			{
				return;
			}
		}
		target.sendMessage(_("msgFormat", senderName, _("me"), message));
		replyTo.setReplyTo(target);
		if (target != sender)
		{
			if (target instanceof Player)
			{
				player.getUser((Player)target).setReplyTo(sender);
			}
			else
			{
				Console.getConsoleReplyTo().setReplyTo(sender);
			}
		}
	}

	@Override
	public String getPermissionName()
	{
		return "essentials.msg";
	}
}
