package solarsystem.coffee;

import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import solarsystem.coffee.utils.C;
import solarsystem.coffee.utils.configIO;

import java.io.File;

import static org.bukkit.Bukkit.getLogger;

import static org.bukkit.Bukkit.*;

public class zPBrainPlugin extends JavaPlugin {


    Player player;
    public int PersoanlityID = 0;
    public String tokens = "256";
    public String API_Key = "";
    @Override
    public void onEnable() {
        getLogger().info("zPBrainPlugin Initialized");
        configloader();

    }

    public void configloader(){

        FileConfiguration config = this.getConfig();

        int VersionID = 0;
        VersionID = Integer.valueOf(config.getString("Version"));
        if(VersionID != 1) {  // Version ID, increase at Update of config
            Bukkit.getConsoleSender().sendMessage("Updating config file");
            config.set("Version", '1');
            config.set("tokens", "512");
            //config.set("API_Key", "0000000000000000000"); \\
            //config.set("Personality", "awjhdoiawd"); \\
            saveConfig();
        }




        tokens = config.getString("tokens");
        API_Key= config.getString("API_Key"); // Needs to ve added at an upgrade
        Bukkit.getConsoleSender().sendMessage("Configuration read tokens=" + tokens + "API_Key=" + API_Key);

        Bukkit.getConsoleSender().sendMessage("Version: " + VersionID);


    }

    @Override
    public void onDisable() {

        getLogger().info("zPBrainPlugin deactivated");
    }


    @Override
    public boolean onCommand(CommandSender interpreter, Command cmd, String input, String[] args) {

        player = (Player) interpreter;
        Server server = interpreter.getServer();
        String serverName = server.getName();
        World world = server.getWorld("world");

        if (!input.equalsIgnoreCase(input)){

            input = input.toLowerCase();

        }

        if (interpreter instanceof Player) {

            if(input.equals("airequest")){
                String querry = "";
                if(args.length>0){
                    for(String a : args){
                        querry += a + " ";
                    }

                    ////
                    String Response = solarsystem.coffee.AIInterface.OpenAiChat.Request(querry, tokens, PersoanlityID);
                    ////


                    //String Response = solarsystem.coffee.OpenAiChat.Request(querry, "512",PersoanlityID);

                    //Filtering out syntax code from ai response
                    String[] AIAnswer = Response.split("content=");
                    Response = AIAnswer[1];
                    AIAnswer = Response.split("\\), finishReason");
                    Response = AIAnswer[0];

                    Bukkit.broadcastMessage(
                            C.color("" +
                                            "&3 //////////////// \n" +
                                            player.getDisplayName()  +
                                    " &6==>#AI-" + PersoanlityID + "# " +
                                            "\n" + querry    +"\n"   +
                                            "&3////////////////\n"   +
                                            "&6 " + Response + ""    +
                                            "\n&3////////////////"
                            )

                    );

                    //Bukkit.broadcastMessage(C.color("&0 AI: &6" + Response));

                }

            }
            if(input.equals("personality")){
                if(args.length==0) {
                    player.sendMessage("There are 3 Personalites available: type '/personality 0-4'");
                    player.sendMessage("0 = Devon");
                    player.sendMessage("1 = AI-Pal");
                    player.sendMessage("2 = AGI Assistant");
                    player.sendMessage("3 = 2Empty");
                    player.sendMessage("4 = Empty");
                } else {
                    if (args.length > 0) {
                        if (Integer.valueOf(args[0]) < 3) {
                            PersoanlityID = Integer.valueOf(args[0]);
                        }
                    }
                }
            }
            if (input.equals("luz")) {
                Location pos = player.getLocation();
                Block swap_current_blog = pos.getBlock();
                if(swap_current_blog.getType().toString()=="AIR"){
                        BlockData air = null;

                        //world.setBlockData(pos,);
                }
            }

        }
        return true;
    }
}
