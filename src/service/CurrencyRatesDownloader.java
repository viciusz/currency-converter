package service;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.Currency;
import model.CurrencyRates;
public class CurrencyRatesDownloader {
    public BigDecimal downloadRate(Currency from, Currency walutaDocelowa) {
        try {
            URL url = new URL("http://api.fixer.io/latest?base="+from+"&symbols="+walutaDocelowa);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Accept", "application/json");
            
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.findAndRegisterModules();
            CurrencyRates ratesFromJson = objectMapper.readValue(connection.getInputStream(), CurrencyRates.class);
            
//          System.out.println(rates);
            Map<Currency, BigDecimal> ratesMap = ratesFromJson.getRates();
            return ratesMap.get(walutaDocelowa); // pobranie kursu dla waluty docelowej
            
//          return ratesFromJson.getRates().get(to);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return null;
    }
    
}
