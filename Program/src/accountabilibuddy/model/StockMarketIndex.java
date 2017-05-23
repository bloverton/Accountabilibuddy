package accountabilibuddy.model;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.BufferedReader;
import java.util.HashMap;

/**
 * Created on 2/21/2017.
 */
public class StockMarketIndex extends StockDisplay{

    private HashMap<String, StockData> topStocks;
    private String[] stockKeys;
    private String stockIndexTxtURL;
    private String stockIndexName;
    /**
     * Constructor: Initializes the stock exchange name and the list of stocks
     * @param stockIndexName: Name of the stock index
     *                              Used for comparing stocks to stock market index
     */
    public StockMarketIndex(String stockIndexName, String stockIndexTxtURL){
        super(stockIndexName);
        this.stockIndexName = stockIndexName;
        this.stockIndexTxtURL = stockIndexTxtURL;
        topStocks = new HashMap<>(200);
        stockKeys = new String[200];
        this.addStocks();
    }

    public StockMarketIndex(String stockIndexName){
        super(stockIndexName);
        this.stockIndexName = stockIndexName;
        topStocks = new HashMap<>(200);
        stockKeys = new String[200];
    }

    /**
     * Adds stocks to the stockIndex
     */
    public void addStocks(){
        StockData stock;
        BufferedReader br = null;
        try {
            String filePath = new File("").getAbsolutePath();
            br = new BufferedReader(new FileReader(filePath + stockIndexTxtURL));
            String line;
            int index = 0;
            while ((line = br.readLine()) != null) {
                stock = new StockData(line);
                stock.setStockIndex(stockIndexName);
                stockKeys[index] = line;
                topStocks.put(line, stock);
                index++;
            }
            for(StockData stockData : topStocks.values()){
                stockData.getStock().print();
            }
            br.close();
        }catch(IOException error) {
            System.out.println("An error has occurred");
        }
    }

    public void addStocks(String stockIndexName){

    }

    public StockData getStock(String key){ return topStocks.get(key); }

    public String[] getStockKeys(){return this.stockKeys;}

}
