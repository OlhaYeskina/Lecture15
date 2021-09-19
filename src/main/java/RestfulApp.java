import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

public class RestfulApp {
    public static void main(String[] args) throws URISyntaxException, IOException, InterruptedException {
        HttpClient client = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_2)
                .connectTimeout(Duration.ofSeconds(60))
                .build();
//
//        boolean result = false;
//        String url = "https://www.example.com";
//        String json = "{\"json\":\"object\"}";
//
//        RestTemplate rest = new RestTemplate();
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.add("Content-Type", "application/json");
//        headers.add("Authorization", String.format("Bearer %s", authToken));
//
//        HttpEntity<String> requestEntity = new HttpEntity<String>(json, headers);
//        ResponseEntity<String> responseEntity =
//                rest.exchange(url, HttpMethod.PUT, requestEntity, String.class);
//
//        HttpStatus status = responseEntity.getStatusCode();
        
        
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI("https://api.twitter.com/labs/2/tweets/1138505981460193280?expansions=attachments.media_keys&tweet.fields=created_at,author_id,lang,source,public_metrics,context_annotations,entities"))
                .GET()
                .setHeader("Accept", "*/*")
                .setHeader("Content-Type", "application/json")
                .setHeader("Authorization", String.format("Bearer %s", "authToken"))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println();
    }
}
