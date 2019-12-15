package com.example.moneymanager;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ConvertorActivity extends AppCompatActivity {

    private String accessKey = "110e0c2f5b00078cff5261aeb8587ad6";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_convertor);

        //bara de back
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //afiseaza currencies disponibile din api
        getCurrencies currencies = new getCurrencies() {
            @Override
            protected void onPostExecute(List<String> strings) {
                Spinner spinnerFrom = findViewById(R.id.spinnerFrom);
                ArrayAdapter<String> spinnerAdapter =
                        new ArrayAdapter<String>(ConvertorActivity.this, android.R.layout.simple_list_item_1, strings);
                spinnerFrom.setAdapter(spinnerAdapter);
                Spinner spinnerTo = findViewById(R.id.spinnerTo);
                spinnerTo.setAdapter(spinnerAdapter);
            }
        };
        currencies.execute("http://data.fixer.io/api/latest?access_key="+accessKey);
    }

    //clasa Async pentru a extrage ratele valutare selectate de utilizator in conversie
    public class GetSelectedCurrency extends AsyncTask<String, Void, String>{
        @Override
        protected String doInBackground(String... strings) {
            String result = null;
            try {
                URL url= new URL(strings[0]);
                HttpURLConnection http = (HttpURLConnection)url.openConnection();
                InputStream is = http.getInputStream();

                BufferedReader reader = new BufferedReader(new InputStreamReader(is));

                String linie = null;
                StringBuilder builder = new StringBuilder();
                while((linie=reader.readLine())!=null){
                    builder.append(linie);
                }

                String convertResult = builder.toString();
                JSONObject object = new JSONObject(convertResult);
                JSONObject rates = object.getJSONObject("rates");
                Iterator<String> it = rates.keys();
                String key = it.next();
                result = String.valueOf(rates.getDouble(key));

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return result;
        }
    }

    //metoda de conversie valutara
    public void convert(View view) {
        final double[] currencyFrom = new double[1];
        final double[] currencyTo = new double[1];
        GetSelectedCurrency getFrom=  new GetSelectedCurrency() {
            @Override
            protected void onPostExecute(String s) {
                currencyFrom[0] = Double.parseDouble(s);
                GetSelectedCurrency getTo= new GetSelectedCurrency() {
                    @Override
                    protected void onPostExecute(String s) {
                        currencyTo[0] = Double.parseDouble(s);
                        EditText ETsuma = findViewById(R.id.ETAmountFrom);
                        double suma = Double.parseDouble(ETsuma.getText().toString());
                        double result = suma*(currencyTo[0]/currencyFrom[0]);
                        EditText ETrezultat = findViewById(R.id.ETAmountTo);
                        ETrezultat.setText(String.valueOf(result));
                    }
                };
                Spinner spinnerTo = findViewById(R.id.spinnerTo);
                String to = spinnerTo.getSelectedItem().toString();
                getTo.execute("http://data.fixer.io/api/latest?access_key=110e0c2f5b00078cff5261aeb8587ad6&symbols="+to);

            }
        };
        Spinner spinnerFrom = findViewById(R.id.spinnerFrom);
        String from = spinnerFrom.getSelectedItem().toString();
        getFrom.execute("http://data.fixer.io/api/latest?access_key=110e0c2f5b00078cff5261aeb8587ad6&symbols="+from);
    }

    //clasa Async ce extrage toate currencies disponibile
    public class getCurrencies extends AsyncTask<String, Void, List<String>> {
        @Override
        protected List<String> doInBackground(String... strings) {
            List<String> availableCurrencies = new ArrayList<>();
            String result =null;
            try {
                URL url = new URL(strings[0]);
                HttpURLConnection http = (HttpURLConnection)url.openConnection();
                InputStream is = http.getInputStream();

                //citim linie cu linie si punem intr-un stringbuilder continutul
                BufferedReader reader = new BufferedReader(new InputStreamReader(is));

                String linie = null;
                StringBuilder builder = new StringBuilder();
                while((linie = reader.readLine())!=null) {
                    builder.append(linie);
                }

                //tot JSON-ul
                String allCurrencies = builder.toString();

                //parsare JSON
                JSONObject object = new JSONObject(allCurrencies);
                JSONObject rates = object.getJSONObject("rates");
                Iterator<String> iterator = rates.keys();
                while (iterator.hasNext()){
                    String currency = iterator.next();
                    availableCurrencies.add(currency);
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return availableCurrencies;
        }
    }
}
