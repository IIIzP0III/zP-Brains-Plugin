/*package solarsystem.coffee;

import com.theokanning.openai.*;
import com.theokanning.openai.engine.Engine;
import com.theokanning.openai.model.*;


import java.util.Scanner;


public class openaiconnector {

    private final static String API_KEY = "your_api_key_here";
    private final static String MODEL_ID = "your_model_id_here";

    public static void main(String[] args) {
        Engine engine = new Engine();
        engine.setId(MODEL_ID);

        Model model = new Model();
        model.setObject(String.valueOf(engine));

        Prompt prompt = new Prompt();
        prompt.setText("Hello, how may I assist you?");

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print("You: ");
            String input = scanner.nextLine().trim();

            if (input.isEmpty()) {
                continue;
            }

            prompt.append(input + " ");

            CompletionRequest request = new CompletionRequest();
            request.setModel(model);
            request.setPrompt(prompt.getText());
            request.setMaxTokens(100);
            request.setN(1);
            request.setStop(new String[]{"\n"});

            CompletionResponse response = CompletionResponse.create(API_KEY).createCompletion(request);

            if (response.getChoices().size() > 0) {
                String text = response.getChoices().get(0).getText();
                LogProbs logProbs = response.getChoices().get(0).getLogProbs();

                System.out.println("Bot: " + text.trim());
            }
        }
    }
}
*/