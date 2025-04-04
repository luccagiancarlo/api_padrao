package br.com.luccasoftware.api_dw.utils;

import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONArray;
import org.json.JSONObject;

public class ApiToCsv {
    private static final String API_URL = "https://dw.institutoaocp.org.br/api_dw/v1/retornarTitulos/all?inicio=2010&fim=2024";
    private static final String BEARER_TOKEN = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1sb2dAaW5zdGl0dXRvYW9jcC5vcmcuYnIiLCJpYXQiOjE3NDAzMzIxOTAsImV4cCI6MTc0MDM2ODE5MH0.YdTYWCI48aOCcw0M_Y5PWlmc0tqtW8UKP6VPEwoByFI";
    private static final String CSV_FILE = "dados_titulos.csv";

    public static void main(String[] args) {
        try {
            // Fazer a requisição HTTP GET
            HttpURLConnection connection = (HttpURLConnection) new URL(API_URL).openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Authorization", "Bearer " + BEARER_TOKEN);
            connection.setRequestProperty("Accept", "application/json");

            int responseCode = connection.getResponseCode();
            if (responseCode == 200) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();

                // Processar o JSON e gerar CSV
                processJsonToCsv(response.toString());
                System.out.println("Arquivo CSV gerado com sucesso: " + CSV_FILE);
            } else {
                System.err.println("Erro na requisição: Código " + responseCode);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void processJsonToCsv(String jsonResponse) {
        try {
            JSONArray jsonArray = new JSONArray(jsonResponse);
            FileWriter writer = new FileWriter(CSV_FILE);

            // Escrever cabeçalho do CSV
            if (jsonArray.length() > 0) {
                JSONObject firstObj = jsonArray.getJSONObject(0);
                writer.append(String.join(";", firstObj.keySet())); // Adiciona os nomes das colunas
                writer.append("\n");
            }

            // Escrever os dados
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject obj = jsonArray.getJSONObject(i);
                for (String key : obj.keySet()) {
                    writer.append(obj.get(key).toString()).append(";");
                }
                writer.append("\n");
            }

            writer.flush();
            writer.close();
        } catch (IOException e) {
            System.err.println("Erro ao escrever o arquivo CSV: " + e.getMessage());
        }
    }
}
