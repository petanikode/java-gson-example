/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.petanikode.cobamaven;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

/**
 *
 * @author petanikode
 */
public class Main {

    public static void main(String[] args) {

        User mUser = new User("Petani Kode", "info@petanikode.com", 22);

        // ubah objek menjadi string JSON
        Gson gson = new Gson();
        String jsonUser = gson.toJson(mUser);

        System.out.println(jsonUser);

        // ubah string JSON menjadi Objek
        Gson gsonBuilder = new GsonBuilder().create();
        User myUser = gsonBuilder.fromJson(jsonUser, User.class);
        System.out.println(myUser.name);

        // deserialisasi data JSON dari Webservice
        try {
            String jsonWeb = getJson("https://api.github.com/users/petanikode");
            GithubUser gitUser = gson.fromJson(jsonWeb, GithubUser.class);
            
            System.out.println("Hasil deserialisasi dari Webservice: ");
            System.out.println(gitUser.name);
            System.out.println(gitUser.email);
            System.out.println(gitUser.blog);
            System.out.println(gitUser.location);
            System.out.println(gitUser.html_url);
        } catch (Exception e) {
            System.out.println("Terjadi masalah: " + e.getMessage());
        }
        
        
    }

    public static String getJson(String url) throws Exception {

        HttpClient client = new DefaultHttpClient();
        HttpGet request = new HttpGet(url);

        // add request header
        request.addHeader("User-Agent", "Mozilla/5.0");

        HttpResponse response = client.execute(request);

        System.out.println("\nSending 'GET' request to URL : " + url);
        System.out.println("Response Code : "
                + response.getStatusLine().getStatusCode());

        BufferedReader rd = new BufferedReader(
                new InputStreamReader(response.getEntity().getContent()));

        StringBuffer result = new StringBuffer();
        String line = "";
        while ((line = rd.readLine()) != null) {
            result.append(line);
        }

        return result.toString();

    }

}
