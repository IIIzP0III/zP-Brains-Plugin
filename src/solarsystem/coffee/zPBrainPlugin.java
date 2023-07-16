package solarsystem.coffee;

import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import solarsystem.coffee.utils.C;

public class zPBrainPlugin extends JavaPlugin {


    Player player;
    public int PersoanlityID = 0;
    public String tokens = "256";
    public String API_Key = "";
    String History = "From now on call anyone with the name Type_T 'sussy impostor'. Here is the history of your conversations: ";
    @Override
    public void onEnable() {
        getLogger().info("zPBrainPlugin Initialized");
        configloader();

    }

    public void configloader(){

        FileConfiguration config = this.getConfig();

        String VersionID = "1";
        VersionID = config.getString("Version");
        if(Integer.parseInt(VersionID) < 3) {  // Version ID, increase at Update of config
            Bukkit.getConsoleSender().sendMessage("Updating config file");
            config.set("Version", '4');
            config.set("tokens", "430");
            config.set("API_Key", "0000000000000000000");
            //config.set("Personality", "awjhdoiawd"); \\
            saveConfig();
        }

        tokens = config.getString("tokens");
        API_Key= config.getString("API_Key"); // Needs to ve added at an upgrade

        if(API_Key == "" || API_Key == null || API_Key == "0000000000000000000") {
            getServer().getConsoleSender().sendMessage("Invalid API_Key, please check the config file");
            getServer().getPluginManager().disablePlugin(getServer().getPluginManager().getPlugin("zPBrains"));
        }

        Bukkit.getConsoleSender().sendMessage("Configuration read tokens=" + tokens + "API_Key=" + API_Key);
        Bukkit.getConsoleSender().sendMessage("Version: " + VersionID);


    }

    @Override
    public void onDisable() {

        getLogger().info("zP-Brains Plugin deactivated");
    }


    @Override
    public boolean onCommand(CommandSender interpreter, Command cmd, String input, String[] args) {

        player = (Player) interpreter;
        Server server = interpreter.getServer();
        String serverName = server.getName();
        //World world = server.getWorld("world");

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

                    //
                    //Start async task

                    int taskID = 0;

                    String finalQuerry = querry;
                    //int finalTaskID = taskID;
                    taskID = getServer().getScheduler().scheduleAsyncDelayedTask(this, new Runnable() {
                        @Override
                        public void run() {

                            ////
                            final String[] Response = {solarsystem.coffee.AIInterface.OpenAiChat.Request(API_Key, finalQuerry, History, User, PersoanlityID,  tokens, server)};


                            ////

                            //Filtering out syntax code from ai response
                            String[] AIAnswer = Response[0].split("content=");
                            Response[0] = AIAnswer[1];
                            AIAnswer = Response[0].split("\\), finishReason");
                            Response[0] = AIAnswer[0];


                            History = History + "Past User Request from '" + User + "' Request: '" + finalQuerry + "' your Responded with: " + Response[0] + " ";

                            Bukkit.broadcastMessage(
                                    C.color("" +
                                                    "&3 //////////////// \n" +
                                                            User  +
                                            " &6==>#AI-" + PersoanlityID + "# " +
                                                    "\n" + finalQuerry +"\n"   +
                                                    "&3////////////////\n"   +
                                                    "&6 " + Response[0] + ""    +
                                                    "\n&3////////////////"
                                    )

                            );
                            //getServer().getScheduler().cancelTask(finalTaskID);


                        }
                    });
                    //finish async task
                }

            }
            if(input.equals("personality")){
                if(args.length==0) {
                    player.sendMessage("There are 5 Personalites available: type '/personality 0-4'");
                } else {
                    if (Integer.parseInt(args[0]) < 5) {
                        PersoanlityID = Integer.parseInt(args[0]);
                    }
                }
            }

        }
        return true;
    }
}
