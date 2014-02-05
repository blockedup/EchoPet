package io.github.dsh105.echopet.listeners;

import io.github.dsh105.echopet.data.PetHandler;
import io.github.dsh105.echopet.entity.CraftPet;
import io.github.dsh105.echopet.entity.EntityPet;
import net.minecraft.server.v1_7_R1.Entity;
import org.bukkit.craftbukkit.v1_7_R1.CraftChunk;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.ChunkUnloadEvent;

import java.util.NoSuchElementException;

public class ChunkListener implements Listener {

    @EventHandler
    public void onChunkUnload(ChunkUnloadEvent event) {
        for (int i = 0; i < ((CraftChunk) event.getChunk()).getHandle().entitySlices.length; ++i) {
            java.util.Iterator<Object> iter = ((CraftChunk) event.getChunk()).getHandle().entitySlices[i].iterator();
            while (iter.hasNext()) {
                try {
                    Entity entity = (Entity) iter.next();
                    if (entity != null && entity instanceof EntityPet) {
                        PetHandler.getInstance().removePet(((EntityPet) entity).getPet(), false);
                        iter.remove();
                        event.setCancelled(true);
                        event.getChunk().unload();
                    }
                } catch (NoSuchElementException e) {
                    continue;
                }
            }
        }
    }

    /*@EventHandler
    public void onChunkLoad(ChunkLoadEvent event) {
        for (int i = 0; i < ((CraftChunk) event.getChunk()).getHandle().entitySlices.length; ++i) {
            java.util.Iterator<Object> iter = ((CraftChunk) event.getChunk()).getHandle().entitySlices[i].iterator();
            while (iter.hasNext()) {
                Entity entity = (Entity) iter.next();
                if (entity instanceof EntityLivingPet) {
                    PetHandler.getInstance().removePet(((EntityLivingPet) entity).getPet(), false);
                    iter.remove();
                    ConsoleLogger.log(Logger.LogLevel.NORMAL, "Removed pet entity from loaded chunk.");
                }
            }
        }
    }*/
}