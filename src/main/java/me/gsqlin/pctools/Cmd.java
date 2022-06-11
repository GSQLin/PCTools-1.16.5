package me.gsqlin.pctools;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.List;

public class Cmd implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        PCTools main = PCTools.pcTools;
        if (args.length > 0 && args[0].equalsIgnoreCase("help")){
            commandSender.sendMessage(new String[]{
                    "§f====§3HELP§7====",
                    "§r/pctools §7[page]",
                    "§r/pctools open §7[player]",
                    "§r/pctools §7help",
                    "§f============",
            });
            return false;
        }

        if (commandSender instanceof Player){
            InvHub invHub = null;
            Player p = (Player) commandSender;
            if (! p.hasPermission("pctools.use")){
                commandSender.sendMessage(GSQUtil.getMsg(((Player) commandSender),main.getConfig().getString("Message.NoPermission")));
                return false;
            }
            if (args.length >= 1) {
                int box;
                if (args[0].equalsIgnoreCase("open")){
                    if (! p.hasPermission("pctools.admin")){
                        commandSender.sendMessage(GSQUtil.getMsg(((Player) commandSender),main.getConfig().getString("Message.NoPermission")));
                        return false;
                    }
                    if (args.length >= 2) {
                        Player p2 = Bukkit.getPlayer(args[1]);
                        if (p2 != null) {
                            invHub = new InvHub(p2, 0, GSQUtil.getMsg(p, main.getConfig().getString("PCToolsTitle"), 1), 6 * 9,main.getConfig().getString("PCToolsTitle"));
                            p.openInventory(invHub.getInventory());
                        } else {
                            p.sendMessage(GSQUtil.getMsg(p, main.getConfig().getString("Message.PlayerNotOnline")));
                        }
                    } else {
                        p.sendMessage("&c/pctools open [player]");
                    }
                    return false;
                }
                try {
                    box = Integer.parseInt(args[0]);
                }catch (NumberFormatException e){
                    p.sendMessage(GSQUtil.getMsg(p,main.getConfig().getString("Message.Error0")));
                    return false;
                }
                if (box <= 0) {
                    p.sendMessage(GSQUtil.getMsg(p,main.getConfig().getString("Message.Error1")));
                    return false;
                }
                invHub = new InvHub(p,box-1,GSQUtil.getMsg(p,main.getConfig().getString("PCToolsTitle"),box),6*9,main.getConfig().getString("PCToolsTitle"));
            }else{
                invHub = new InvHub(p,0,GSQUtil.getMsg(p,main.getConfig().getString("PCToolsTitle"),1),6*9,main.getConfig().getString("PCToolsTitle"));
            }
            p.closeInventory();
            p.openInventory(invHub.getInventory());
        }else{
            commandSender.sendMessage(main.getConfig().getString("Message.Error2").replace("&","§"));
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        return null;
    }
}
