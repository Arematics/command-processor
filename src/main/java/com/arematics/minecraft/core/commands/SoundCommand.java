package com.arematics.minecraft.core.commands;

import com.arematics.minecraft.core.annotations.AnyAccess;
import com.arematics.minecraft.core.annotations.Default;
import com.arematics.minecraft.core.annotations.PluginCommand;
import com.arematics.minecraft.core.annotations.SubCommand;
import com.arematics.minecraft.core.command.CoreCommand;
import com.arematics.minecraft.core.messaging.Messages;
import com.arematics.minecraft.core.messaging.advanced.JsonColor;
import com.arematics.minecraft.core.messaging.injector.advanced.AdvancedMessageInjector;
import com.arematics.minecraft.core.utils.ListUtils;
import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.time.LocalDate;
import java.time.LocalDateTime;

@AnyAccess
@PluginCommand(names = {"sound"})
public class SoundCommand extends CoreCommand {

    @Default
    public boolean sendInfo(CommandSender sender){
        Messages.create("cmd_not_valid")
                .to(sender)
                .DEFAULT()
                .replace("cmd_usage", "\n/sound list\n/sound list <startsWith>\n/sound <Name>")
                .handle();
        return true;
    }

    @SubCommand("list")
    public boolean list(Player player){
        return listSelected(player, "");
    }

    @SubCommand("list {startsWith}")
    public boolean listSelected(CommandSender sender, String startsWith){
        Messages.create("listing")
                .to(sender)
                .setInjector(AdvancedMessageInjector.class)
                .replace("list_type", "Sound")
                .END()
                .replace("list_value", ListUtils.getNameListStartsWith(Sound.class, startsWith))
                .setColor(JsonColor.GREEN)
                .END()
                .handle();
        return true;
    }

    @SubCommand("list date {date}")
    public boolean executeDate(CommandSender sender, LocalDate date){
        sender.sendMessage(date.toString());
        return true;
    }

    @SubCommand("list add {message}")
    public boolean addSelected(CommandSender sender, String message){
        sender.sendMessage(message);
        return true;
    }

    @SubCommand("{sound}")
    public boolean executeSound(CommandSender sender, Sound sound){
        Player player = (Player) sender;
        player.playSound(player.getLocation(), sound, 1, 1);
        return true;
    }
}
