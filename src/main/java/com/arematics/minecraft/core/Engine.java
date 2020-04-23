package com.arematics.minecraft.core;

import com.arematics.minecraft.core.command.parser.Parser;
import org.bukkit.Bukkit;

public class Engine {

    private static Engine INSTANCE;

    /**
     * @return Instance Engine Object
     */
    public static Engine getInstance(){
        return INSTANCE;
    }

    /**
     * Generates new Engine Class as Instace
     * @param bootstrap JavaPlugin
     */
    static void startEngine(Bootstrap bootstrap){
        INSTANCE = new Engine(bootstrap);
    }

    /**
     * Stopping Engine and calling Shutdown Hooks
     */
    static void shutdownEngine(){
        if(INSTANCE != null){
            //TODO Fire Shutdown Hook
        }else{
            Bukkit.getLogger().severe("Instance not found System shutdown whiteout saving");
        }
    }

    private final Bootstrap plugin;
    private final Parser parser;

    /**
     * Hooking Config File
     * Starts the Multi Hook to get all Hooks loaded (Language, Commands, Listener)
     * @param bootstrap JavaPlugin
     */
    public Engine(Bootstrap bootstrap){
        this.plugin = bootstrap;
        this.parser = new Parser();
    }

    public Bootstrap getPlugin() {
        return plugin;
    }

    public Parser getParser() {
        return parser;
    }
}
