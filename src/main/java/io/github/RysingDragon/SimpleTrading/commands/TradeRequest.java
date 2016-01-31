package io.github.RysingDragon.SimpleTrading.commands;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;

import io.github.RysingDragon.SimpleTrading.SimpleTrading;
import io.github.RysingDragon.SimpleTrading.events.TradeRequestEvent;
import io.github.RysingDragon.SimpleTrading.utils.Request;
import io.github.RysingDragon.SimpleTrading.utils.TradeUtils;

public class TradeRequest implements CommandExecutor{

	public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
		
		if (src instanceof Player) {
			
			Player sender = (Player)src;
			Player receiver = (Player)args.getOne("player").get();
			
			if (sender == receiver) {
				sender.sendMessage(Text.of("You may not trade with yourself"));
				return CommandResult.empty();
			}
			
			for (Request r : TradeUtils.requests) {
				if ( (sender == r.getSender() || sender == r.getReceiver()) && (receiver == r.getSender() || receiver == r.getReceiver()) ) {
					sender.sendMessage(Text.of("There is already a trade request pending between you and ", receiver.getName()));
					return CommandResult.empty();
				}
			}
			
			Sponge.getGame().getEventManager().post(new TradeRequestEvent(sender, receiver));
			sender.sendMessage(Text.of("You have sent a trade request to ", receiver.getName()));
			return CommandResult.success();
		} else {
			return CommandResult.empty();
		}
		
	}

}
