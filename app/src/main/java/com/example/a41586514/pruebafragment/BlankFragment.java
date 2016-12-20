package com.example.a41586514.pruebafragment;


import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class BlankFragment extends Fragment implements View.OnClickListener {
    public Button res1;


    public BlankFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_blank, container, false);
        Button b = (Button) view.findViewById(R.id.btn_mostrar);
        b.setOnClickListener(this);



/*
            res1 = (Button) getActivity().findViewById(R.id.btn_mostrar);
            res1.setOnClickListener(this);

*/
        return view;

    }

   ArrayList<Ciudad> traerCiudades() {

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://tp4ort.firebaseio.com/geonames.json")
                .build();

        ArrayList<Ciudad> ciudades;
        try

        {
            Response response = client.newCall(request).execute();  // Llamado al Google API
            ciudades = parsearResultado(response.body().string());      // Convierto el resultado en ArrayList<Direccion>
        } catch (IOException | JSONException e) {
            Log.d("Error", e.getMessage());             // Error de Network o al parsear JSON
            return null;
        }
       return ciudades;

    }

    ArrayList<Ciudad> parsearResultado(String result) throws JSONException {
        ArrayList<Ciudad> ciudades = new ArrayList<>();
        JSONArray jsonCities = new JSONArray(result);
        for (int i = 0; i < jsonCities.length(); i++) {
            JSONObject jsonResultado = jsonCities.getJSONObject(i);
            String nombre = jsonResultado.getString("name");
            int poblacion = Integer.valueOf(jsonResultado.getString("population"));
            double latitud = Double.valueOf(jsonResultado.getString("lat"));

            Ciudad c = new Ciudad(nombre, poblacion, latitud);
            ciudades.add(c);


        }
        return ciudades;
    }


    int maxPob(ArrayList<Ciudad> ciudades) {
        int valor = 0;
        for (Ciudad ciudad : ciudades) {
            if (ciudad.poblacion > valor) {
                valor = ciudad.poblacion;
            }

        }
        return valor;
    }

    int minPob(ArrayList<Ciudad> ciudades) {
        int valor = 0;
        for (Ciudad ciudad : ciudades) {
            if (ciudad.poblacion < valor) {
                valor = ciudad.poblacion;
            }

        }
        return valor;
    }


    @Override
    public void onClick(View view) {
        int valor = 0;
        ArrayList<Ciudad> ciudades = traerCiudades();
        int algo =maxPob(ciudades);
        int algo2 =minPob(ciudades);

        TextView textView = (TextView) getView().findViewById(R.id.texteleccion);
        textView.setText("Mayor poblacion: " + algo);

    }
}



