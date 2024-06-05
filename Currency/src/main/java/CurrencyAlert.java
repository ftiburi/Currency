import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONObject;

public class CurrencyAlert {

    public static void main(String[] args) {
        try {
            double currentRate = getDollarRate(); // Obtém a taxa atual do dólar
            double previousRate = currentRate; // Inicializa com a taxa atual

            while (true) {
                Thread.sleep(600); // Verifica a cada minuto (ajuste conforme necessário)

                currentRate = getDollarRate(); // Obtém a taxa atualizada
                System.out.println("Está rodando!!!");
                System.out.println("Taxa atual: " + currentRate);
                if (currentRate > previousRate * 1.000001) {
                    System.out.println("ALERTA: A taxa de compra aumentou 0,0001%!");
                    System.out.println("Taxa anterior: " + previousRate);
                    System.out.println("Taxa atual: " + currentRate);
                }
                if (currentRate < previousRate / 1.000001) {
                    System.out.println("ALERTA: A taxa de compra diminuiu 0,0001%!");
                    System.out.println("Taxa anterior: " + previousRate);
                    System.out.println("Taxa atual: " + currentRate);
                }


                previousRate = currentRate; // Atualiza a taxa anterior
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static double getDollarRate() throws Exception {
        URL url = new URL("https://economia.awesomeapi.com.br/json/all/USD-BRL"); // API para cotação do dólar
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String response = reader.readLine();
        reader.close();

        JSONObject json = new JSONObject(response);
        return json.getJSONObject("USD").getDouble("bid");
    }
}

