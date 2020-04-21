package com.arematics.minecraft.core.commands;

import com.arematics.minecraft.core.command.CMD;
import com.arematics.minecraft.core.command.CoreCommand;
import com.arematics.minecraft.core.command.Default;
import com.arematics.minecraft.core.command.Sub;
import com.arematics.minecraft.core.utils.ListUtils;
import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CMD
public class SoundCommand extends CoreCommand {

    @Override
    public String[] getCommandNames() {
        return new String[]{"sound"};
    }

    @Override
    public boolean matchAnyAccess() {
        return true;
    }

    @Default
    public boolean sendInfo(CommandSender sender){
        sender.sendMessage("Nutzung:\n/sound <Name>\n/sound list\n/sound list <Start>\n/sound list end <Zahl>");
        return true;
    }

    @Sub("list")
    public boolean list(CommandSender sender){
        return listSelected(sender, "");
    }

    @Sub("list {startsWith}")
    public boolean listSelected(CommandSender sender, String startsWith){
        sender.sendMessage("Sounds: " + ListUtils.getNameListStartsWith(Sound.class, startsWith));
        return true;
    }

    @Sub("list end {endsWith}")
    public boolean listSelected(CommandSender sender, Integer value){
        sender.sendMessage("Wert ist: " + value);
        return true;
    }

    @Sub("{sound}")
    public boolean executeSound(CommandSender sender, Sound sound){
        Player player = (Player) sender;
        player.playSound(player.getLocation(), sound, 1, 1);
        return true;
    }
}
