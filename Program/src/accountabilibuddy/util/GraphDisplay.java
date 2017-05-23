package accountabilibuddy.util;

import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;

import java.io.*;

/**
 * Created by overt on 4/19/2017.
 */
public class GraphDisplay implements Serializable{

    public static void displayGraph(LineChart<String, Integer> stockLineChart, String stockName) {

        XYChart.Series<String, Integer> series = new XYChart.Series<>();
        series.setName(stockName);
        

    }
}
