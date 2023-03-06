package solarsystem.coffee;

import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import solarsystem.coffee.utils.C;

import static org.bukkit.Bukkit.getLogger;

import static org.bukkit.Bukkit.*;

public class zPBrainPlugin extends JavaPlugin {


    Player player;
    public int PersoanlityID = 0;
    @Override
    public void onEnable() {
        getLogger().info("zPBrainPlugin deactivated");

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

                    String Response = solarsystem.coffee.OpenAiChat.Request(querry, "512",PersoanlityID);

                    String[] AIAnswer = Response.split("content=");
                    Response = AIAnswer[1];
                    AIAnswer = Response.split("\\), finishReason");
                    Response = AIAnswer[0];

                    Bukkit.broadcastMessage(C.color("&3 //////////////// \n&2 #AI Request# \n" + querry + "\n" +
                            "&3////////////////\n&6 " + Response + "\n&3////////////////"));
                    //Bukkit.broadcastMessage(C.color("&0 AI: &6" + Response));

                }

            }
            if(input.equals("personality")){
                player.sendMessage("There are 5 Personalites available: type '/personality 0-4'");
                if(args.length>0){
                    if(Integer.valueOf(args[0])<5){
                        PersoanlityID = Integer.valueOf(args[0]);
                    }
                }


                }


        }
        return true;
    }
}
