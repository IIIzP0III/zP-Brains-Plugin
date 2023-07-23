package solarsystem.coffee;

import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import solarsystem.coffee.utils.C;

import java.io.File;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.Objects;

public class zPBrainPlugin extends JavaPlugin {


    Player player;
    public int PersoanlityID = 0;
    public String tokens = "256";
    public String API_Key = "";
    String History = "From now on call anyone with the name Type_T 'sussy impostor'. Here is the history of your conversations: ";

    Boolean Debug = false;
    @Override
    public void onEnable() {
        getLogger().info("zPBrainPlugin Initialized");
        configloader();

    }

    public void configloader() {
        Server server = getServer();
        ConsoleCommandSender cs = server.getConsoleSender();
        File configdata = new File("plugins/zP_Brain/config.yml");


        FileConfiguration config = this.getConfig();

        //Build config if not exists
        if (!configdata.exists()) {

            config.set("MODEL", "gpt-4");
            config.set("version", 0.05);
            config.set("Usage_Limit", 4000);
            config.set("API_KEY", "B0DCD");
            config.set("default_personality", 0);
            //saveDefaultConfig();
            saveConfig();
            cs.sendMessage("Configuration File created, setup your API KEY there");

            this.getPluginLoader().disablePlugin(Objects.requireNonNull(Bukkit.getPluginManager().getPlugin("solarsystem.coffee.zPBrainPlugin")));
            //this.getPluginLoader().enablePlugin(Objects.requireNonNull(Bukkit.getPluginManager().getPlugin("solarsystem.coffee.zPBrainPlugin")));

        } else {
            tokens = config.getString("Usage_Limit");
            API_Key = config.getString("API_KEY");

            if (Objects.equals(API_Key, "") || API_Key == null || API_Key.equals("00000")) {
                cs.sendMessage("Invalid API_Key, please check the config file");
                //getServer().getPluginManager().disablePlugin(Objects.requireNonNull(getServer().getPluginManager().getPlugin("zPBrains")));
            }

            cs.sendMessage("Configuration read tokens= " + tokens + "   <|||> API_Key= " + API_Key);


        }
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

        if (input.equals("airequest")) {
            String querry = "";
            String User = interpreter.getName();
            if (args.length > 0) {
                for (String a : args) {
                    querry += a + " ";
                }

                //
                //Start async task

                int taskID = 0;

                String finalQuerry = querry;
                //int finalTaskID = taskID
                taskID = getServer().getScheduler().scheduleAsyncDelayedTask(this, new Runnable() {
                    @Override
                    public void run() {

                        ////
                        final String[] Response = {solarsystem.coffee.AIInterface.OpenAiChat.Request(API_Key, finalQuerry, History, User, PersoanlityID, tokens, server)};

                        ////

                            //Filtering out syntax code from ai response
                            String[] AIAnswer = Response[0].split("content=");
                            Response[0] = AIAnswer[1];
                            AIAnswer = Response[0].split("\\), finishReason");
                            Response[0] = AIAnswer[0];

                        History = History + "User: " + User + "' chat: '" + finalQuerry + "' you: " + Response[0] + " ";

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
                player.sendMessage("There are 5 Personalities available: type '/personality 0-4'");
            } else {
                if (Integer.parseInt(args[0]) < 5) {
                    PersoanlityID = Integer.parseInt(args[0]);
                }
            }
        }

        return true;
    }
}
