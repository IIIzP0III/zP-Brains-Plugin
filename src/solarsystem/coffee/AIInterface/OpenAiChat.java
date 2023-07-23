package solarsystem.coffee.AIInterface;

import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatCompletionResult;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.service.OpenAiService;
import org.bukkit.Server;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import static solarsystem.coffee.AIInterface.utilz.personality_creator;

public class OpenAiChat extends JavaPlugin {

    private final static String MODEL_ID = "gpt-4";

    public static String Request(String API_KEY, String ChatRequest, String History, String User, int PersonalityID, String CharacterLimit, Server server) {

        OpenAiService service = new OpenAiService(API_KEY, Duration.ofSeconds((long) 130));


        //todo add config files for personalities you can modify, -
        List<ChatMessage> list = new ArrayList<>();
        ChatMessage message = new ChatMessage();
        String[] PersonalityContainer = personality_creator();


        message.setRole("user");
        message.setContent("Respond in less then" + CharacterLimit + " characters. + " + PersonalityContainer[PersonalityID] + "'" + History + "' End of previous conversation history. " + "Current User: '" + User + "' Current Request: '" + ChatRequest + "'");
        list.add(message);

        ChatCompletionRequest chatCompletionRequest = ChatCompletionRequest.builder()
                .messages(list)
                .model(MODEL_ID)
                .temperature(0.7)
                .maxTokens(4000)
                .n(1)
                .build();

        ChatCompletionResult responseChat = service.createChatCompletion(chatCompletionRequest);

        return responseChat.getChoices().get(0).toString();

    }
}


/*
        int tookensused = tookenizer(server, list.toString()); //calculate tookens
        if(tookensused>=3000){ //todo calculate if over tooken limit and dump/reset/load memory of AI to prevent memory reset through to huge message through history
            //saveMemory();
            //clearHistory();
            //loadMemory();
        }
*/
//chat format

/*
    public static int tookenizer(Server server, String prompt){

        int tokens_user = 0;


        //depreciated
        /*
        char[] promptc = prompt.toCharArray();
        String[] promptw = prompt.split(" ");

        int tokens_c = 0;
        for(char o : promptc){
            tokens_c++;
        }
        int tokens_w = 0;
        for(String O : promptw){
            tokens_w++;
        }
        if(tokens_c>=tokens_w){
            server.getConsoleSender().sendMessage("tokens_c =" + Integer.toString(tokens_c));
            return tokens_c;
        } else {
            server.getConsoleSender().sendMessage("tokens_w =" + Integer.toString(tokens_w));
            return tokens_w;
        }

         */