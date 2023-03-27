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
    String History = "The history of your conversations: ";
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

        if(API_Key == "" || API_Key == null){
            getServer().getConsoleSender().sendMessage("Invalid API_Key, please check the config file");
            getServer().getPluginManager().disablePlugin(getServer().getPluginManager().getPlugin("zPBrains"));
        }

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
                String User = interpreter.getName();
                if(args.length>0){
                    for(String a : args){
                        querry += a + " ";
                    }

                    //todo make AI remember past chat history - maybe for user && make AI respond in private mode too
                    //Start async task

                    //String Response = solarsystem.coffee.OpenAiChat.Request(querry, "512",PersoanlityID);
                    int taskID = 0;

                    String finalQuerry = querry;
                    int finalTaskID = taskID;
                    taskID = getServer().getScheduler().scheduleAsyncDelayedTask(this, new Runnable() {
                        @Override
                        public void run() {

                            ////
                            final String[] Response = {solarsystem.coffee.AIInterface.OpenAiChat.Request(API_Key, finalQuerry, History, User, PersoanlityID,  tokens)};


                            ////

                            //Filtering out syntax code from ai response
                            String[] AIAnswer = Response[0].split("content=");
                            Response[0] = AIAnswer[1];
                            AIAnswer = Response[0].split("\\), finishReason");
                            Response[0] = AIAnswer[0];


                            History = History + "this User Request is from: '" + User + "' Request: '" + finalQuerry + "' you responded with Response: " + Response[0];

                            Bukkit.broadcastMessage(
                                    C.color("" +
                                                    "&3 //////////////// \n" +
                                            ((Player)interpreter).getDisplayName()  +
                                            " &6==>#AI-" + PersoanlityID + "# " +
                                                    "\n" + finalQuerry +"\n"   +
                                                    "&3////////////////\n"   +
                                                    "&6 " + Response[0] + ""    +
                                                    "\n&3////////////////"
                                    )

                            );
                            getServer().getScheduler().cancelTask(finalTaskID);

                    //Bukkit.broadcastMessage(C.color("&0 AI: &6" + Response));

                        }
                    });
                    //finish async task
                }

            }
            if(input.equals("personality")){
                if(args.length==0) {
                    player.sendMessage("There are 3 Personalites available: type '/personality 0-4'");
                    player.sendMessage("0 = Devon");
                    player.sendMessage("1 = AI-Pal");
                    player.sendMessage("2 = AGI Assistant");
                    player.sendMessage("3 = GPT-3");
                    player.sendMessage("4 = GPT-3");
                } else {
                    if (args.length > 0) {
                        if (Integer.valueOf(args[0]) < 5) {
                            PersoanlityID = Integer.valueOf(args[0]);
                        }
                    }
                }
            }
            if (input.equals("luz")) {
                Location pos = player.getLocation();
                Block swap_current_blog = pos.getBlock();
                //if(swap_current_blog.getType().toString()=="AIR"){
                if(swap_current_blog.getType().isAir()){
                        //Block.isAir()
                        swap_current_blog.setType(Material.LIGHT);
                        //world.setBlockData(pos,);
                }
            }

        }
        return true;
    }
}
