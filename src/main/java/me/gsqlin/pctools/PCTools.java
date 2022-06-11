package me.gsqlin.pctools;

import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;

public class PCTools extends JavaPlugin {
    static public Map<Integer,Integer> boxGui = new HashMap<>();
    static PCTools pcTools;

    @Override
    public void onEnable() {
        boxGui.put(0,7);
        boxGui.put(1,16);
        boxGui.put(2,25);
        boxGui.put(3,34);
        boxGui.put(4,43);
        boxGui.put(5,52);

        boxGui.put(6,9);
        boxGui.put(7,10);
        boxGui.put(8,11);
        boxGui.put(9,12);
        boxGui.put(10,13);
        boxGui.put(11,14);

        boxGui.put(12,18);
        boxGui.put(13,19);
        boxGui.put(14,20);
        boxGui.put(15,21);
        boxGui.put(16,22);
        boxGui.put(17,23);

        boxGui.put(18,27);
        boxGui.put(19,28);
        boxGui.put(20,29);
        boxGui.put(21,30);
        boxGui.put(22,31);
        boxGui.put(23,32);

        boxGui.put(24,36);
        boxGui.put(25,37);
        boxGui.put(26,38);
        boxGui.put(27,39);
        boxGui.put(28,40);
        boxGui.put(29,41);

        saveDefaultConfig();
        pcTools = this;
        PluginCommand pluginCommand = getServer().getPluginCommand("pctools");
        pluginCommand.setExecutor(new Cmd());
        pluginCommand.setTabCompleter(new Cmd());
        getServer().getPluginManager().registerEvents(new PlayerListener(),this);

        getServer().getConsoleSender().sendMessage(new String[]{
                        "§3 ______   ______ §7_______            _        ",
                        "§3(_____ \\ / _____§7|_______)          | |      ",
                        "§3 _____) ) /      §7_       ___   ___ | | ___   ",
                        "§3|  ____/| |     §7| |     / _ \\ / _ \\| |/___)",
                        "§3| |     | \\_____§7| |____| |_| | |_| | |___ | ",
                        "§3|_|      \\______)§7\\______)___/ \\___/|_(___/"
        });
    }
}
