package solarsystem.coffee;


import com.theokanning.openai.completion.CompletionRequest;
import com.theokanning.openai.completion.CompletionResult;
import com.theokanning.openai.service.OpenAiService;

import javax.naming.AuthenticationException;
import java.util.Scanner;

public class openaicomplete {
    private final static String API_KEY = "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";
    private final static String MODEL_ID = "text-davinci-003";
//    private final static String MODEL_ID = "text-davinci-003";
//    private final static String MODEL_ID = "gpt-3.5-turbo";

    public static void main(String[] args) throws AuthenticationException {

        OpenAiService service = new OpenAiService(API_KEY);

        //System.out.println(rs.getChoices().toString());

        Scanner scanner = new Scanner(System.in);


        while (true) {
            System.out.println("Prompt:");
          //String input = "Respond in less than 128 characters, -" + scanner.nextLine().trim();
            String input = scanner.nextLine().trim();

            if (input.isEmpty()) {
                continue;
            }


            //service.createCompletion(completionRequest).getChoices().forEach(System.out::println);
            //completion format
            CompletionRequest completionRequest = null;
            completionRequest = CompletionRequest.builder()
                    .prompt(input)
                    .model(MODEL_ID)
                    .maxTokens(100)
                    .n(1) // Amount of responses generated
                    .build();

            CompletionResult response = service.createCompletion(completionRequest);
            String text = response.getChoices().get(0).getText();
            System.out.println("AI:" + text);

              //CompletionResult rs = service.createCompletion(completionRequest);
        /*    CompletionRequest request = CompletionRequest.builder()
                    .prompt(input)
                    .model(MODEL_ID)
                    .maxTokens(100)
                    .n(1)
                    .stop(Collections.singletonList("\n"))
                    .echo(true)
                    .build();
        */
            //String that = Integer.toString(response.getChoices().size());

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