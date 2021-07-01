package com.bilicraft.icebreakboat;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import net.coreprotect.CoreProtect;
import net.coreprotect.CoreProtectAPI;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.craftbukkit.v1_17_R1.CraftWorld;
import org.bukkit.entity.Boat;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.concurrent.TimeUnit;

public final class IceBreakBoat extends JavaPlugin implements Listener {
    private final Cache<Location, Material> blockCache = CacheBuilder.newBuilder()
            .expireAfterAccess(1, TimeUnit.MINUTES)
            .build();
    private CoreProtectAPI api;

    @Override
    public void onEnable() {
        // Plugin startup logic
       api = ((net.coreprotect.CoreProtect) Bukkit.getPluginManager().getPlugin("CoreProtect")).getAPI();
       Bukkit.getPluginManager().registerEvents(this,this);
       Bukkit.getScheduler().runTaskTimer(this,()->{
           Bukkit.getOnlinePlayers().forEach(player->{
               Entity vehicle = player.getVehicle();
               if(vehicle == null || vehicle.getType() != EntityType.BOAT){
                   return;
               }
               Block block = vehicle.getLocation().clone().add(0,-1,0).getBlock();
               if(Tag.ICE.isTagged(block.getType())){
                   player.breakBlock(block);
                   //if(!player.breakBlock(block)){
                   //    block.breakNaturally();
                   //    api.logRemoval(player.getName(),block.getLocation() , block.getType(), block.getBlockData());
                   //}
               }
           });
       }, 0,5L);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
    
    
}
