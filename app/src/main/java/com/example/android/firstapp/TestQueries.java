/**
 * Created by bighandsam on 9/23/17.
 */
package com.example.android.firstapp;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.*;
import java.util.HashMap;
import java.util.Arrays;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
//import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.DateTimeFormat;

public class TestQueries {

    private static String[] daysOfTheWeek = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};

    public static String callYelpSearchApi(String location, int ratingOrDist, int responseLimit) throws IOException {
        OkHttpClient client = new OkHttpClient();

        String sortBy;
        if (ratingOrDist == 0) {
            sortBy = "rating";
        } else {
            sortBy = "distance";
        }

        String url = "https://api.yelp.com/v3/businesses/search?location=" + location + "&categories=foodtrucks&sort_by=" + sortBy + "&limit=" + responseLimit;

        Request request = new Request.Builder()
                .header("Authorization", "Bearer " + "HcTCNVYOsG_LV9yHz6mmCuOjV3Crqh0iL8LC1NAoJiTC7map-qy21gCnPoE9kgaK9R2nHVU6zAacsmG-E4Xn-O8NE79UNppcOwJjnC042a-TlBF6fj_ODzQnJ6zGWXYx")
                .url(url)
                .build();

        Response response = client.newCall(request).execute();
        return response.body().string();
    }

    public static String callYelpBusinessApi(String id) throws IOException {
        OkHttpClient client = new OkHttpClient();

        String url = "https://api.yelp.com/v3/businesses/" + id;

        Request request = new Request.Builder()
                .header("Authorization", "Bearer " + "HcTCNVYOsG_LV9yHz6mmCuOjV3Crqh0iL8LC1NAoJiTC7map-qy21gCnPoE9kgaK9R2nHVU6zAacsmG-E4Xn-O8NE79UNppcOwJjnC042a-TlBF6fj_ODzQnJ6zGWXYx")
                .url(url)
                .build();

        Response response = client.newCall(request).execute();
        return response.body().string();
    }

    public static FoodTruck[] generateArray(JSONObject jsonObject) {

        JSONArray msg = (JSONArray) jsonObject.get("businesses");

        FoodTruck[] result = new FoodTruck[msg.size()];

        // loop through the JSON array
        int index = 0;
        Iterator<JSONObject> iterator = msg.iterator();
        while (iterator.hasNext()) {
            JSONObject current = iterator.next();

            // get name
            String n = (String) current.get("name");

            // get coordinates
            JSONObject coordinates = (JSONObject) current.get("coordinates");
            double lat = (double) coordinates.get("latitude");
            double longit = (double) coordinates.get("longitude");

            // get rating
            double rat = (double) current.get("rating");

            // set default distance
            double dist = -1.0;

            // get cuisine
            String cuis = "";
            JSONArray categories = (JSONArray) current.get("categories");
            // the 2nd element in the index displays the cuisine
            // but sometimes, this 2nd element doesn't exist
            if (categories.size() > 1) {
                JSONObject cuisine = (JSONObject) categories.get(1);
                cuis = (String) cuisine.get("title");
            }

            // set default website
            String site = "";

            // get image url
            String imageUrl = (String) current.get("image_url");

            // create instance of FoodTruck
            FoodTruck curr = new FoodTruck(n, lat, longit, rat, dist, cuis, site, imageUrl);
            // add instance to array
            result[index++] = curr;

            // call Business API to find open/close hours
            /*try {
                String businessDetails = callYelpBusinessApi((String) current.get("id"));

                // use json-simple library to change string into JSON
                JSONParser parser = new JSONParser();
                Object obj = parser.parse(businessDetails);
                JSONObject jsonObject2 = (JSONObject) obj;

                // drill down JSON to reach hours
                // (sometimes there are no hours, so check for null)
                JSONArray hours = (JSONArray) jsonObject2.get("hours");
                if (hours != null) {
                    hours = (JSONArray) ((JSONObject) hours.get(0)).get("open");

                    Iterator<JSONObject> iterator2 = hours.iterator();
                    // loop through each day to find specific hours
                    while (iterator2.hasNext()) {
                        current = iterator2.next();

                        // start time
                        String rawTimestamp = (String) current.get("start");
                        DateTimeFormatter inputFormatter = DateTimeFormat.forPattern("HHmm");
                        DateTimeFormatter outputFormatter = DateTimeFormat.forPattern("hh:mm a");
                        DateTime dateTime = inputFormatter.parseDateTime(rawTimestamp);
                        String formattedTimestampStart = outputFormatter.print(dateTime.getMillis());

                        // end time
                        rawTimestamp = (String) current.get("end");
                        dateTime = inputFormatter.parseDateTime(rawTimestamp);
                        String formattedTimestampEnd = outputFormatter.print(dateTime.getMillis());

                        String[] startAndEnd = {formattedTimestampStart, formattedTimestampEnd};
                        int num = (int) ((long) current.get("day")); // this line is weird! I can't go directly to int..
                        // add start/end times
                        curr.addKey(daysOfTheWeek[num], startAndEnd);
                    }
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ParseException e) {
                e.printStackTrace();
            }*/
        }

        return result;
    }

    public static FoodTruck[] findMyFoodTrucks(String location, int ratingOrDist, int responseLimit) {
        JSONParser parser = new JSONParser();

        FoodTruck[] foodTruckArray = null;

        try {
            // call Yelp APIs
            String result = callYelpSearchApi(location, ratingOrDist, responseLimit);

            // use json-simple library to change string into JSON
            Object obj = parser.parse(result);
            JSONObject jsonObject = (JSONObject) obj;

            // generate array of FoodTruck objects
            foodTruckArray = generateArray(jsonObject);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return foodTruckArray;
    }

    public static void main(String[] args) {
        FoodTruck[] foodTruckArray = findMyFoodTrucks("Los Angeles, CA", 0, 5);

        // print out details of each food truck
        for (int i = 0; i < foodTruckArray.length; i++) {
            System.out.println((i+1) + ") " + foodTruckArray[i].getName() + " " + foodTruckArray[i].getImage());
        }
    }
}
