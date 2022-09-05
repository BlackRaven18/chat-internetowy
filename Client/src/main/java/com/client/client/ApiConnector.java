package com.client.client;

import org.json.JSONArray;
import org.json.JSONObject;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class ApiConnector {

    //private static final java.util.logging.Logger log = java.util.logging.Logger.getLogger(Main.class.getName());

    private static HttpURLConnection conn;


    public List<String> parseJSONObject(String responseBody) {

        List<String> tmpList = new ArrayList<>();

        JSONObject album = new JSONObject(responseBody);

        tmpList.add(String.valueOf(album.getInt("id")));
        tmpList.add(album.getString("name"));
        tmpList.add(album.getString("surname"));
        tmpList.add(album.getString("login"));
        tmpList.add(album.getString("password"));

        return tmpList;
    }

    public List<List<String>> parseJSONArray(String responseBody) {

        List<List<String>> mainList = new ArrayList<>();

        JSONArray albums = new JSONArray(responseBody);
        for (int i = 0; i < albums.length(); i++) {
            JSONObject album = albums.getJSONObject(i);
            List<String> tmpList = new ArrayList<>();

            tmpList.add(String.valueOf(album.getInt("id")));
            tmpList.add(album.getString("name"));
            tmpList.add(album.getString("surname"));
            tmpList.add(album.getString("login"));
            tmpList.add(album.getString("password"));
            mainList.add(tmpList);
        }
        return mainList;
    }

    private String getResponseFromUrl(URL url, String method) {
        BufferedReader reader;
        String line;
        StringBuilder responseContent = new StringBuilder();

        try {
            conn = (HttpURLConnection) url.openConnection();

            // Request setup
            conn.setRequestMethod(method.toUpperCase(Locale.ROOT));
            conn.setConnectTimeout(5000);// 5000 milliseconds = 5 seconds
            conn.setReadTimeout(5000);

            // Test if the response from the server is successful
            int status = conn.getResponseCode();

            if(status < 300){
                reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                while ((line = reader.readLine()) != null) {
                    responseContent.append(line);
                }
                reader.close();
            }

        } catch (IOException | NullPointerException e) {
            e.printStackTrace();
        } finally {
            conn.disconnect();
        }

        return responseContent.toString();
    }


    public List<String> GETuser(int index) {
        try {
            URL url = new URL("http://localhost:8080/users/" + index);
            String response = getResponseFromUrl(url, "GET");
            if(response.isEmpty()){
                return null;
            }

            return (parseJSONObject(response));

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<List<String>> GETusers() {
        try {
            URL url = new URL("http://localhost:8080/users");

            String response = getResponseFromUrl(url, "GET");
            if(response.isEmpty()){
                return null;
            }
            return (parseJSONArray(response));

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }


    public static int POSTuser(String name, String surname, String login, String password, int id){
        int status = 0;
        StringBuilder postValue = new StringBuilder()
                .append("{")
                .append("\"name\":").append("\"").append(name).append("\"").append(",")
                .append("\"surname\":").append("\"").append(surname).append("\"").append(",")
                .append("\"login\":").append("\"").append(login).append("\"").append(",")
                .append("\"password\":").append("\"").append(password).append("\"").append(",")
                .append("\"id\":").append(id)
                .append("}");


        try {
            URL url = new URL("http://localhost:8080/users");
            conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json");
            conn.setDoOutput(true);
            conn.setDoInput(true);

            //status = conn.getResponseCode();


            OutputStream os = conn.getOutputStream();
            byte[] input = postValue.toString().getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);


            status = conn.getResponseCode();





        } catch (IOException | NullPointerException e) {
            e.printStackTrace();
        } finally {
            conn.disconnect();
        }

        return status;
    }

    public static int DELETEuser(int id){
        int status = 0;
        try {
            URL url = new URL("http://localhost:8080/users/" + id);
            conn = (HttpURLConnection) url.openConnection();

            conn.setDoOutput(true);
            conn.setRequestMethod("DELETE");

            status = conn.getResponseCode();


        } catch (IOException | NullPointerException e) {
            e.printStackTrace();
        } finally {
            conn.disconnect();
        }

        return status;
    }

    public static int PUTuser(String name, String surname, String login, String password, int id){
        int status = 0;
        StringBuilder putValue = new StringBuilder()
                .append("{")
                .append("\"name\":").append("\"").append(name).append("\"").append(",")
                .append("\"surname\":").append("\"").append(surname).append("\"").append(",")
                .append("\"login\":").append("\"").append(login).append("\"").append(",")
                .append("\"password\":").append("\"").append(password).append("\"").append(",")
                .append("\"id\":").append(id)
                .append("}");


        try {
            URL url = new URL("http://localhost:8080/users/" + id);
            conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("PUT");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json");
            conn.setDoOutput(true);

            OutputStream os = conn.getOutputStream();
            byte[] input = putValue.toString().getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);


            status = conn.getResponseCode();

        } catch (IOException | NullPointerException e) {
            e.printStackTrace();
        } finally {
            conn.disconnect();
        }

        return status;
    }




}