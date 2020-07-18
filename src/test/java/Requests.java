import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class Requests {

    public final static String PHONE_BOOK_BASE = "http://localhost:8080/";

    public static JSONObject sendGetRequestObject(String base, String query) throws IOException {
        HttpURLConnection conn = connectHttp(base,query);
        // optional default is GET
        conn.setRequestMethod("GET");
        //add request header
        conn.setRequestProperty("User-Agent", "Mozilla/5.0");

        conn.setRequestProperty("Referer", base);

//        conn.getResponseCode();

        System.out.println("\nSending 'GET' request to URL : " + conn.getURL());

        System.out.println(conn.getResponseCode());

        BufferedReader in = new BufferedReader(
                new InputStreamReader(conn.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        //print in String

        JSONObject myResponse = new JSONObject(response.toString());

        return myResponse;
    }

    public static JSONArray sendGetRequestArray(String base, String query) throws IOException {
        HttpURLConnection conn = connectHttp(base,query);
        // optional default is GET
        conn.setRequestMethod("GET");
        //add request header
        conn.setRequestProperty("User-Agent", "Mozilla/5.0");

        conn.setRequestProperty("Referer", base);

//        conn.getResponseCode();

        System.out.println("\nSending 'GET' request to URL : " + conn.getURL());

        System.out.println("Response status: " + conn.getResponseCode());

        BufferedReader in = new BufferedReader(
                new InputStreamReader(conn.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        //print in String

        JSONArray myResponse = new JSONArray(response.toString());
        return myResponse;
    }


    static String sendPostRequest(String base, String query, String inputString) throws IOException {
        HttpURLConnection conn = connectHttp(base,query);
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json; utf-8");
        conn.setRequestProperty("Accept", "application/json");

        conn.setDoOutput(true);
        try (OutputStream os = conn.getOutputStream()) {
            byte[] input = inputString.getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);
        }
        StringBuilder response = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8))) {
            String responseLine;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }

            return response.toString();
        } catch (IOException e){

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getErrorStream(), StandardCharsets.UTF_8));
            String responseLine;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }
            throw new IOException("Ошибка отправки запроса: сервис вернул ошибку");
        } finally {
            System.out.println("Response status: " + conn.getResponseCode());
            System.out.println("Результаты отправки запроса: "+response.toString());
            conn.disconnect();
        }
    }

    static int sendPostRequestAndGetId(String base, String query, String inputString) throws IOException {
        HttpURLConnection conn = connectHttp(base,query);
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json; utf-8");
        conn.setRequestProperty("Accept", "application/json");

        conn.setDoOutput(true);
        try (OutputStream os = conn.getOutputStream()) {
            byte[] input = inputString.getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);
        }
        StringBuilder response = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8))) {
            String responseLine;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }
            JSONObject myResponse = new JSONObject(response.toString());
            int id = Integer.parseInt(myResponse.get("id").toString());
            return id;
        } catch (IOException e){

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getErrorStream(), StandardCharsets.UTF_8));
            String responseLine;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }
            throw new IOException("Ошибка отправки запроса: сервис вернул ошибку");
        } finally {
            System.out.println("Response status: " + conn.getResponseCode());
            System.out.println("Результаты отправки запроса: "+response.toString());
            conn.disconnect();
        }
    }

    static String sendPutRequest(String base, String query, String inputString) throws IOException {
        HttpURLConnection conn = connectHttp(base,query);
        conn.setRequestMethod("PUT");
        conn.setRequestProperty("Content-Type", "application/json; utf-8");
        conn.setRequestProperty("Accept", "application/json");

        conn.setDoOutput(true);
        try (OutputStream os = conn.getOutputStream()) {
            byte[] input = inputString.getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);
        }
        StringBuilder response = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8))) {
            String responseLine;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }
            return response.toString();
        } catch (IOException e){

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getErrorStream(), StandardCharsets.UTF_8));
            String responseLine;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }
            throw new IOException("Ошибка отправки запроса: сервис вернул ошибку");
        } finally {
            System.out.println("Response status: " + conn.getResponseCode());
            System.out.println("Результаты отправки запроса: "+response.toString());
            conn.disconnect();
        }
    }

    public static String sendDeleteRequest(String base, String query) throws IOException {
        HttpURLConnection conn = connectHttp(base,query);
        conn.setDoOutput(true);
        conn.setRequestProperty(
                "Content-Type", "application/x-www-form-urlencoded" );
        conn.setRequestMethod("DELETE");
        conn.connect();
        StringBuilder response = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8))) {
            String responseLine;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }
            return response.toString();
        } catch (IOException e){

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getErrorStream(), StandardCharsets.UTF_8));
            String responseLine;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }
            throw new IOException("Ошибка отправки запроса: сервис вернул ошибку");
        } finally {
            System.out.println("Response status: " + conn.getResponseCode());
            System.out.println("Результаты отправки запроса: "+response.toString());
            conn.disconnect();
        }
    }

    public static HttpURLConnection connectHttp(String base, String query) throws IOException {
        URL url = new URL(base+query);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        return conn;
    }
}
