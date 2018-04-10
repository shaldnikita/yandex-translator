package ru.testwork.yandextranslateapp;

import java.io.*;
import java.util.*;
import java.util.logging.*;

import org.apache.http.Consts;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

public class Translator {

    private static final String PATH = "https://translate.yandex.net/api/v1.5/tr.json/translate";
    private static final String LANG = "en-ru";
    private static String API_KEY;

    public static void main(String[] args) throws IOException {
        new Translator(args[0]).run();
    }

    public Translator(String key) {
        API_KEY=key;
    }

    public void run() throws IOException {
        try (Scanner in = new Scanner(System.in)) {
            String toTranslate = in.nextLine();
            String translatedText = doTranslate(toTranslate);

            System.out.println(translatedText);
        }
    }

    private String doTranslate(String input) throws IOException {
        HttpPost httpPost = createPostRequest(input);

        String responseString = sendPost(httpPost);
        ObjectMapper mapper = new ObjectMapper();


        Response response = mapper.readValue(responseString, Response.class);
        return response.getText()[0];
    }

    private HttpPost createPostRequest(String input) {
        HttpPost httpPost = new HttpPost(PATH);
        List<NameValuePair> params = new ArrayList<>();


        params.add(new BasicNameValuePair("key", API_KEY));
        params.add(new BasicNameValuePair("text", input));
        params.add(new BasicNameValuePair("lang", LANG));

        httpPost.setEntity(new UrlEncodedFormEntity(params, Consts.UTF_8));

        return httpPost;
    }

    private String sendPost(HttpPost post) {
        try {
            HttpClient client = HttpClients.createDefault();
            HttpResponse httpResponse = client.execute(post);

            try (BufferedReader br = new BufferedReader(
                    new InputStreamReader(httpResponse.getEntity().getContent(), "UTF-8"))) {
                return (br.readLine());
            }
        } catch (IOException ex) {
            Logger.getLogger(Translator.class.getName()).log(Level.SEVERE, null, ex);
            return "Error,your message was not translated!";
        }
    }
}
