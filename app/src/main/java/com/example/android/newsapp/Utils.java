package com.example.android.newsapp;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rushi Patel on 8/5/2016.
 */
public class Utils
{
    private static final String LOG_TAG = Utils.class.getSimpleName();

    private Utils()
    {

    }

    /**
     * Create url
     * @param stringUrl the website we like to access
     * @return the stringUrl parameter part of the URL object
     */
    private static URL createUrl(String stringUrl)
    {
        URL url = null;

        try
        {
            url = new URL(stringUrl);
        }
        catch(MalformedURLException e)
        {
            Log.e(LOG_TAG, "Problem retrieving the url.", e);
        }

        return url;
    }

    /**
     * Requesting connection to the http
     * @param url parameter containing the link to the website
     * @return JSON response with the json abstracted from the url
     * @throws IOException for openConnection()
     */
    private static String makeHttpRequest(URL url) throws IOException
    {
        String jsonResponse = "";

        if (url == null)
        {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;

        try
        {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000);//milliseconds
            urlConnection.setConnectTimeout(15000); //milliseconds
            urlConnection.connect();

            if (urlConnection.getResponseCode() == 200)
            {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            }
            else
            {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        }
        catch (IOException e)
        {
            Log.e(LOG_TAG, "Problem retrieving the json response", e);
        }
        finally
        {
            if (urlConnection != null)
            {
                urlConnection.disconnect();
            }

            if (inputStream != null)
            {
                inputStream.close();
            }
        }

        return jsonResponse;
    }

    /**
     * Putting together the characters into a a string from the INputStream
     * @param inputStream is what has been gathered from the URL connection
     * @return output as a string
     * @throws IOException foe readLine()
     */
    private static String readFromStream(InputStream inputStream) throws IOException
    {
        StringBuilder output = new StringBuilder();

        if (inputStream != null)
        {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while(line != null)
            {
                output.append(line);
                line = reader.readLine();
            }
        }

        return output.toString();
    }

    /**
     * Parse the info from the JSON text
     * @param newsJSON the JSON text obtained
     * @return parsed info inserted into the News ArrayList
     */
    private static List<News> extractFromNews(String newsJSON)
    {
        if (TextUtils.isEmpty(newsJSON))
        {
            return null;
        }

        List<News> news = new ArrayList<>();

        try
        {
            JSONObject news_json_response = new JSONObject(newsJSON);
            JSONObject response = news_json_response.getJSONObject("response");
            JSONArray resultsArray = response.getJSONArray("results");

            for(int i = 0; i < resultsArray.length(); i++)
            {
                JSONObject arrayObject = resultsArray.getJSONObject(i);

                String title = arrayObject.getString("webTitle");
                String webUrl = arrayObject.getString("webUrl");
                String section = arrayObject.getString("sectionName");
                String detail = arrayObject.getString("type");

                news.add(new News(section, title, detail, webUrl));
            }
        }
        catch (JSONException e)
        {
            Log.e(LOG_TAG, "Problem parsing the JSON results.", e);
        }

        return news;
    }

    /**
     * Fetch the request url for the app
     * @param requestUrl that will be used for the news info
     * @return ArrayList filled with the news
     */
    public static List<News> fetchNewsData(String requestUrl)
    {
        URL url = createUrl(requestUrl);

        String jsonResponse = null;

        try
        {
            jsonResponse = makeHttpRequest(url);
        }
        catch (IOException e)
        {
            Log.e(LOG_TAG, "Problem making the http request.", e);
        }

        List<News> news = extractFromNews(jsonResponse);
        return news;
    }
}
