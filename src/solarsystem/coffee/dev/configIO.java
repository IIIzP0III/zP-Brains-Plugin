package solarsystem.coffee.dev;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class configIO extends JavaPlugin{
    public void setup(FileConfiguration config){


        File file;
        File configdata = new File("plugins/zP-Brains/config.yml");

        config.set("tokens", "256");
        //saveDefaultConfig();
        saveConfig();
        Bukkit.getConsoleSender().sendMessage("Configuration File created, setup your Mysql/Mariadb details there");

        //this.getPluginLoader().disablePlugin(Bukkit.getPluginManager().getPlugin("ZorgHomes"));

    }
}
