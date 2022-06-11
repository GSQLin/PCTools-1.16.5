package me.gsqlin.pctools;

import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;
import com.pixelmonmod.pixelmon.api.storage.PCStorage;
import com.pixelmonmod.pixelmon.api.storage.PlayerPartyStorage;
import com.pixelmonmod.pixelmon.api.storage.StorageProxy;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class InvHub implements InventoryHolder {
    private final Inventory inv;
    private Player owner;
    private int box;
    boolean fy = false;
    boolean scgn = false;
    private final Map<ItemStack, Pokemon> photo = new HashMap<>();
    PCTools main = PCTools.pcTools;
    private Inventory upInv;
    private final String rawTitle;

    public InvHub(Player p,int box,String title,int slot,String rawTitle){
        this.owner = p;
        this.box = box;
        this.rawTitle = rawTitle;
        this.inv = Bukkit.createInventory(this,slot,title);
        PCStorage pcs = StorageProxy.getPCForPlayer(p.getUniqueId());
        PlayerPartyStorage pps = StorageProxy.getParty(p.getUniqueId());
        List<Pokemon> pokemonList = new ArrayList<>();
        List<Pokemon> pokelist = new ArrayList<>();
        for (Pokemon pokemon:pcs.getAll()){
            if (pokemon != null)pokemonList.add(pokemon);
        }
        for (Pokemon pokemon:pps.getAll()){
            if (pokemon != null)pokelist.add(pokemon);
        }
        for (Pokemon pokemon:pokemonList){
            if (Objects.requireNonNull(pokemon.getPosition()).box == box){
                int i = pokemon.getPosition().order;
                ItemStack itemStack = GSQUtil.getPokeItemStack(p,pokemon,main.getConfig());
                if (i <= 5){
                    inv.setItem(i, itemStack);
                }else{
                    inv.setItem(PCTools.boxGui.get(i),itemStack);
                }
                if (!photo.containsKey(itemStack)) photo.put(itemStack,pokemon);
            }
        }
        for (Pokemon pokemon:pokelist){
            ItemStack itemStack = GSQUtil.getPokeItemStack(p,pokemon,main.getConfig());
            inv.setItem(PCTools.boxGui.get(Objects.requireNonNull(pokemon.getPosition()).order),itemStack);
            if (!photo.containsKey(itemStack)) photo.put(itemStack,pokemon);
        }
        GSQUtil.setPcItem(this,this.owner,main);
    }

    public InvHub(String title,int slot,String rawTitle){
        this.inv = Bukkit.createInventory(this,slot,title);
        this.rawTitle = rawTitle;
    }

    @Override
    public Inventory getInventory() {
        return inv;
    }
    public Pokemon getPokemon(ItemStack itemStack){
        return this.photo.get(itemStack);
    }
    public void removePhoto(ItemStack itemStack){
        this.photo.remove(itemStack);
    }
    public PlayerPartyStorage getPps(){
        return StorageProxy.getParty(this.owner.getUniqueId());
    }
    public PCStorage getPcs(){
        return StorageProxy.getPCForPlayer(this.owner.getUniqueId());
    }
    public int getBox() {
        return this.box;
    }
    public void putPhoto(ItemStack itemStack,Pokemon pokemon){
        this.photo.put(itemStack,pokemon);
    }
    public void setBox(int i){
        this.box = i;
    }
    public void setUpInv(Inventory xInv){this.upInv = xInv;}
    public Inventory getUpInv(){return upInv;}
    public Player getOwner(){return this.owner;}
    public String getRawTitle(){return this.rawTitle;}
}
