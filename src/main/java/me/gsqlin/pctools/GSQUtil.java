package me.gsqlin.pctools;

import com.pixelmonmod.pixelmon.Pixelmon;
import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;
import com.pixelmonmod.pixelmon.api.pokemon.stats.BattleStatsType;
import com.pixelmonmod.pixelmon.api.storage.StorageProxy;
import com.pixelmonmod.pixelmon.items.SpriteItem;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.craftbukkit.v1_16_R3.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GSQUtil {
    static public Map<Player,Boolean> OnlinePC = new HashMap<>();

    static Integer[] fjxwz = new Integer[]{
            6,15,24,33,42,8,17,26,35,44,46,47,48,49,51,53
    };
    static PCTools main = PCTools.pcTools;

    static public void setPcItem(InvHub invHub,Player p,PCTools main){
        Inventory inv = invHub.getInventory();
        ItemStack fjx = new ItemStack(Material.GRAY_STAINED_GLASS_PANE,1,(short)7);
        ItemStack syy = invHub.getBox()+1!=1 ? new ItemStack(Material.WHITE_STAINED_GLASS_PANE):new ItemStack(Material.ORANGE_STAINED_GLASS_PANE);
        ItemStack xyy = invHub.getBox()+1!=GSQUtil.getMaxbox(p)? new ItemStack(Material.WHITE_STAINED_GLASS_PANE):
                new ItemStack(Material.ORANGE_STAINED_GLASS_PANE);
        ItemStack scgn = new ItemStack(Material.LIGHT_BLUE_STAINED_GLASS_PANE);

        ItemMeta fjx_meta = fjx.getItemMeta();
        ItemMeta syy_meta = fjx.getItemMeta();
        ItemMeta xyy_meta = fjx.getItemMeta();
        ItemMeta scgn_meta = scgn.getItemMeta();

        fjx_meta.setDisplayName(GSQUtil.getMsg(p,main.getConfig().getString("item0.name")));
        syy_meta.setDisplayName(GSQUtil.getMsg(p,main.getConfig().getString("item1.name")));
        xyy_meta.setDisplayName(GSQUtil.getMsg(p,main.getConfig().getString("item2.name")));
        scgn_meta.setDisplayName("§c删除功能!!");
        List<String> list =  new ArrayList<>();
        list.add("§c危险功能§7(待GSQWarehouse插件完成后配合使用)");
        list.add("§6状态:§a已关闭");
        list.add("§6开启代表着你点到的精灵就将被删除");
        list.add("§6关闭反之~§7(待GSQWarehouse插件完成即可做到撤销删除)");
        scgn_meta.setLore(list);
        fjx_meta.setLore(GSQUtil.getMsg(p,main.getConfig().getStringList("item0.lore")));
        syy_meta.setLore(GSQUtil.getMsg(p,main.getConfig().getStringList("item1.lore")));
        xyy_meta.setLore(GSQUtil.getMsg(p,main.getConfig().getStringList("item2.lore")));

        fjx.setItemMeta(fjx_meta);
        syy.setItemMeta(syy_meta);
        xyy.setItemMeta(xyy_meta);
        scgn.setItemMeta(scgn_meta);

        for (int i :fjxwz){
            inv.setItem(i,fjx);
        }

        if (main.getConfig().getBoolean("scgn")) inv.setItem(47,scgn);
        inv.setItem(45,syy);
        inv.setItem(50,xyy);
    }
    static public void qrsc(Player p,ItemStack poke_item,InvHub invHub){
        Inventory inv = invHub.getInventory();
        ItemStack confirm = new ItemStack(Material.RED_STAINED_GLASS_PANE);
        ItemStack cancel = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
        ItemMeta confirm_meta = confirm.getItemMeta();
        ItemMeta cancel_meta = cancel.getItemMeta();
        confirm_meta.setDisplayName(GSQUtil.getMsg(p,main.getConfig().getString("item3.name")));
        cancel_meta.setDisplayName(GSQUtil.getMsg(p,main.getConfig().getString("item4.name")));
        confirm_meta.setLore(GSQUtil.getMsg(p,main.getConfig().getStringList("item3.lore")));
        cancel_meta.setLore(GSQUtil.getMsg(p,main.getConfig().getStringList("item4.lore")));
        confirm.setItemMeta(confirm_meta);
        cancel.setItemMeta(cancel_meta);
        inv.setItem(20,confirm);
        inv.setItem(24,cancel);
        inv.setItem(4,poke_item);
    }
    static public ItemStack getPokeItemStack(Player p, Pokemon poke, FileConfiguration config){
        ItemStack itemStack = CraftItemStack.asBukkitCopy((net.minecraft.server.v1_16_R3.ItemStack)(Object)SpriteItem.getPhoto(poke));
        ItemMeta itemMeta = itemStack.getItemMeta();
        if (poke.isEgg()) if (!config.getBoolean("DisplayEgg")){
            itemMeta.setDisplayName(GSQUtil.getMsg(p,poke,config.getString("EggInfo.name")));
            itemMeta.setLore(GSQUtil.getMsg(p,poke,config.getStringList("EggInfo.lore")));
            itemStack.setItemMeta(itemMeta);
            return itemStack;
        }
        itemMeta.setDisplayName(GSQUtil.getMsg(p,poke,config.getString("PokePhoto.name")));
        itemMeta.setLore(GSQUtil.getMsg(p,poke,config.getStringList("PokePhoto.lore")));
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    static public List<Integer> getKdj(){
        List<Integer> list = new ArrayList<>();
        list.add(0);
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        list.add(5);
        for (int i = 0;i < 30;i++){
            list.add(PCTools.boxGui.get(i));
        }
        return list;
    }
    static public int getMaxbox(Player p){
        return StorageProxy.getPCForPlayer(p.getUniqueId()).getBoxCount();
    }

    static public Integer getNullPoke(List<Integer> list,Inventory inv){
        int x = 55;
        for (int i : list){
            ItemStack item = inv.getItem(i);
            if (item == null) return i;
            if (item.getType().equals(Material.AIR)) return i;
        }
        return x;
    }
    static public List<Integer> getDF(int slot){
        List<Integer> right = new ArrayList<>();
        for (int i = 0;i < 6;i++){
            right.add(PCTools.boxGui.get(i));
        }
        List<Integer> left = getKdj();
        left.removeAll(right);
        if (right.contains(slot)){
            return left;
        }else return right;
    }


    //papi和自己的变量处理
    static public String getMsg(Player p, Pokemon pokemon,String str){
        String msg;
        msg = setMyPapi(pokemon,str);
        return PlaceholderAPI.setPlaceholders(p,msg);
    }
    static public String getMsg(Player p,String str){
        String msg = str.replace("§","&");
        return PlaceholderAPI.setPlaceholders(p,msg);
    }
    static public String getMsg(Player p,String str,int box){
        String msg = str.replace("§","&").
                replace("{pc_box}",String.valueOf(box)).
                replace("{pc_max_box}",String.valueOf(GSQUtil.getMaxbox(p)));
        return PlaceholderAPI.setPlaceholders(p,msg);
    }
    static public List<String> getMsg(Player p,Pokemon poke,List<String> list){
        List<String> msg = new ArrayList<>();
        for (String str :list){
            msg.add(getMsg(p,poke,str));
        }
        return msg;
    }
    static public List<String> getMsg(Player p,List<String> list){
        List<String> msg = new ArrayList<>();
        for (String str :list){
            msg.add(getMsg(p,str));
        }
        return msg;
    }
    static public String setMyPapi(Pokemon poke,String str){
        return str.replace("{pokemon_name}",poke.getLocalizedName()).
                replace("{pokemon_uuid}",poke.getUUID().toString()).
                replace("{poke_name}",poke.getDisplayName()).
                replace("{poke_level}",String.valueOf(poke.getPokemonLevel())).
                replace("{poke_ivs}",String.valueOf((int)poke.getIVs().getPercentage(poke.getIVs().getTotal()))).
                replace("{poke_evs}",String.valueOf((int)poke.getIVs().getPercentage(poke.getIVs().getTotal()))).
                replace("{poke_ivs_hp}",String.valueOf(poke.getIVs().getStat(BattleStatsType.HP))).
                replace("{poke_evs_hp}",String.valueOf(poke.getEVs().getStat(BattleStatsType.HP))).
                replace("{poke_ivs_attack}",String.valueOf(poke.getIVs().getStat(BattleStatsType.ATTACK))).
                replace("{poke_evs_attack}",String.valueOf(poke.getEVs().getStat(BattleStatsType.ATTACK))).
                replace("{poke_ivs_defence}",String.valueOf(poke.getIVs().getStat(BattleStatsType.DEFENSE))).
                replace("{poke_evs_defence}",String.valueOf(poke.getEVs().getStat(BattleStatsType.DEFENSE))).
                replace("{poke_ivs_speed}",String.valueOf(poke.getIVs().getStat(BattleStatsType.SPEED))).
                replace("{poke_evs_speed}",String.valueOf(poke.getEVs().getStat(BattleStatsType.SPEED))).
                replace("{poke_ivs_specialDefence}",String.valueOf(poke.getIVs().getStat(BattleStatsType.SPECIAL_DEFENSE))).
                replace("{poke_evs_specialDefence}",String.valueOf(poke.getEVs().getStat(BattleStatsType.SPECIAL_DEFENSE))).
                replace("{poke_ivs_specialAttack}",String.valueOf(poke.getIVs().getStat(BattleStatsType.SPECIAL_ATTACK))).
                replace("{poke_evs_specialAttack}",String.valueOf(poke.getEVs().getStat(BattleStatsType.SPECIAL_ATTACK))).
                replace("{poke_ability}",poke.getAbilityName()).
                replace("{poke_shiny}",getShiny(poke));
    }
    static public String getShiny(Pokemon poke){
        String s = null;
        if (poke.isShiny()){
            s = "§a是";
        }else s = "§c否";
        return s;
    }

    static File folder = new File(main.getDataFolder(),"\\pokeData");

    static public File getFile(String uuid){
        if (!folder.exists())folder.mkdirs();
        File file = new File(folder.getAbsolutePath()+"\\" + uuid);
        if (!file.exists()) {
            return null;
        }
        return file;
    }
    static public File getFile(Pokemon poke){
        if (!folder.exists())folder.mkdirs();
        File file = new File(folder.getAbsolutePath()+"\\" + poke.getUUID().toString());
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return file;
    }
}
