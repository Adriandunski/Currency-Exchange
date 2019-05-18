package classes;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;

public class CurrencyConverter {

    private static String nic = "USD_PLN";

    private static String web = "https://api.currconv.com/api/v7/convert?q=";
    private static String compact = "&compact=ultra";
    private static String apiKey = "&apiKey=3f5f12cc-e64d-4830-a840-9420b734cc58";

    public static BigDecimal convert(Currence from, Currence to) {

        try {
            URL url = new URL(web + from.getCurrence() + "_" + to.getCurrence() + compact + apiKey);
            BufferedReader bf = new BufferedReader(new InputStreamReader(url.openStream()));

            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = bf.readLine()) != null) {
                response.append(inputLine);
            }

            bf.close();

            JSONObject myResponse = new JSONObject(response.toString());
            String s = myResponse.getString(String.format("%s_%s", from.getCurrence(), to.getCurrence()));

            return new BigDecimal(s);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }
}