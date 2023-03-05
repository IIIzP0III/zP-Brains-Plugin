package solarsystem.coffee;


import com.theokanning.openai.OpenAiApi;
import com.theokanning.openai.completion.*;
import com.theokanning.openai.service.AuthenticationInterceptor;
import com.theokanning.openai.service.OpenAiService;
import com.theokanning.openai.OpenAiResponse;


import javax.naming.AuthenticationException;
import java.util.Collections;
import java.util.Scanner;

public class openai {
    private final static String API_KEY = "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";
    private final static String MODEL_ID = "text-davinci-002";

    public static void main(String[] args) throws AuthenticationException {

        OpenAiService service = new OpenAiService(API_KEY);

        //System.out.println(rs.getChoices().toString());

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Prompt:");
//            String input = "Respond in less than 128 characters, -" + scanner.nextLine().trim();
            String input = scanner.nextLine().trim();

            if (input.isEmpty()) {
                continue;
            }

            CompletionRequest completionRequest = null;
            //service.createCompletion(completionRequest).getChoices().forEach(System.out::println);

            completionRequest = CompletionRequest.builder()
                    .prompt(input)
                    .model("text-davinci-003")
                    .maxTokens(100)
                    .n(1) // Amount of responses generated
                   //.stop(Collections.singletonList("\n"))
                    .echo(true)
                    .build();
            //CompletionResult rs = service.createCompletion(completionRequest);
        /*    CompletionRequest request = CompletionRequest.builder()
                    .prompt(input)
                    .model(MODEL_ID)
                    .maxTokens(100)
                    .n(1)
                    .stop(Collections.singletonList("\n"))
                    .build();
        */
            CompletionResult response = service.createCompletion(completionRequest);
            String text = response.getChoices().get(0).getText();
            System.out.println("AI:" + text);

            //    OpenAiResponse openaiResponse = new OpenAiResponse();
            //    System.out.println("Bot: " + openaiResponse.toString());
        }




/*
        while (true) {
            System.out.print("You: ");
            String input = scanner.nextLine().trim();

            if (input.isEmpty()) {
                continue;
            }
            if (response.getChoices().size() > 0) {
                String text = response.getChoices().get(0).getText();

                System.out.println("Bot: " + text.trim());
            }
        }*/
    }
}