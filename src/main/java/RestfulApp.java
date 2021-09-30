

import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;


/**
 * 1. Необходимо сначала используя пакет java.net запросить имеющиеся в продаже билеты.
 * Сохранить полученный xml в файл tickets.xml. Создать класс Ticket со всеми необходимыми полями.
 * Считать файл tickets.xml и сохранить его как List<Ticket>. Из полученной коллекции выбрать все
 * фильмы с категорией STANDART и сохранить в json формате в файл tickets.json.
 *
 * 2. Необходимо для userId = 1 забронировать билет с id = 1
 *
 * 3. Unmarshall tickets.json файл в список Ticket и записать его в Excel файл
 */

public class RestfulApp {
    //private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

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
                .uri(new URI("https://reqres.in/api/users?page=2"))
                .GET()
//                .setHeader("Accept", "*/*")
//                .setHeader("Content-Type", "application/json")
//                .setHeader("Authorization", String.format("Bearer %s", "authToken"))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

    //    String json = GSON.toJson(response);
        System.out.println(response.body());
        try (FileWriter file = new FileWriter("users.json")) {
            //We can write any JSONArray or JSONObject instance to the file
            file.write(response.body());
            file.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println();
    }
}
