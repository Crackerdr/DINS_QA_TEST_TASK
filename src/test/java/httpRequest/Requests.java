package httpRequest;

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


    public static JSONArray sendGetRequestArray(String query) throws IOException {
        String result = sendRequestWithoutBody(query,"GET");
        return new JSONArray(result);
    }

   public static int sendPostRequestAndGetId(String query, String inputString) throws IOException {
        String result = sendRequestWithBody(query,inputString,"POST");
        JSONObject myResponse = new JSONObject(result);
        return Integer.parseInt(myResponse.get("id").toString());
    }

    public static String sendPutRequest(String query, String inputString) throws IOException {
        return sendRequestWithBody(query,inputString,"PUT");
    }

    public static String sendDeleteRequest(String query) throws IOException {
        return sendRequestWithoutBody(query,"DELETE");
    }

    private static String sendRequestWithBody(String query,String inputString, String typeRequest) throws IOException {
        HttpURLConnection conn = connectHttp(query,typeRequest);
            sendMessageBody(conn, inputString);
        StringBuilder response = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8))) {
            return successResponse(br,response);
        } catch (IOException e){
            failResponse(conn,response);
            throw new IOException("Ошибка отправки запроса: сервис вернул ошибку");
        } finally {
            finnishAndCloseConnect(conn,response);
        }
    }
    private static String sendRequestWithoutBody(String query,String typeRequest) throws IOException {
        HttpURLConnection conn = connectHttp(query,typeRequest);
        StringBuilder response = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8))) {
            return successResponse(br,response);
        } catch (IOException e){
           failResponse(conn,response);
            throw new IOException("Ошибка отправки запроса: сервис вернул ошибку");
        } finally {
            finnishAndCloseConnect(conn,response);
        }
    }

    public static HttpURLConnection connectHttp(String query, String method) throws IOException {
        URL url = new URL(PHONE_BOOK_BASE+query);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestProperty("Content-Type", "application/json; utf-8");
        conn.setRequestProperty("Accept", "application/json");
        conn.setRequestProperty("Referer", PHONE_BOOK_BASE);
        conn.setRequestMethod(method);
        conn.setDoOutput(true);
        conn.connect();
        System.out.println("\nSending "+method+ " request to URL : " + conn.getURL());
        return conn;
    }


    public static String successResponse(BufferedReader br,StringBuilder response) throws IOException {
        String responseLine;
        while ((responseLine = br.readLine()) != null) {
            response.append(responseLine.trim());
        }
        return response.toString();
    }
    public static void failResponse(HttpURLConnection conn,StringBuilder response) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getErrorStream(), StandardCharsets.UTF_8));
        String responseLine;
        while ((responseLine = br.readLine()) != null) {
            response.append(responseLine.trim());
        }
    }
    public static void finnishAndCloseConnect(HttpURLConnection conn, StringBuilder response) throws IOException {
        System.out.println("Response status: " + conn.getResponseCode());
        System.out.println("Результаты отправки запроса: "+response.toString());
        conn.disconnect();
    }
    public static void sendMessageBody(HttpURLConnection conn,String inputString) throws IOException {
        try (OutputStream os = conn.getOutputStream()) {
            byte[] input = inputString.getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);
        }
    }
}
