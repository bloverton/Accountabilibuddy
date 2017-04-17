package accountabilibuddy.util;

import yahoofinance.Stock;
import yahoofinance.YahooFinance;

import java.io.IOException;

/**
 * Created by overt on 3/17/2017.
 */
public class CalculationUtilities {

    /**
     * Used to find the Covariance of two sets of data
     * Note: Used for calculating the Beta Value of a Stock
     * @param primaryDataSet: The X or primary set of data
     * @param secondaryDataSet: The Y or secondary set of data
     * @return The covariance of the two sets of data
     */
    public static double covariance(double[] primaryDataSet, double[] secondaryDataSet){

        double covariance = 0;
        double primaryXAvg = 0;
        double secondaryYAvg = 0;

        //loops through array and sums all values in primaryDataSet & secondaryDataSet
        for(int i = 0; i < primaryDataSet.length; i++){
            primaryXAvg += primaryDataSet[i];
            secondaryYAvg += secondaryDataSet[i];
        }

        //Calculates the X average & Y average
        primaryXAvg /= primaryDataSet.length;
        secondaryYAvg /= secondaryDataSet.length;

        //loops through primary and secondary data sets and subtracts each value by its corresponding average
        //multiplies the X and Y averages and stores them in the covariance
        for(int i = 0; i < primaryDataSet.length; i++){
            double XDataAvg = (primaryDataSet[i] - primaryXAvg);
            double YDataAvg = (secondaryDataSet[i] - secondaryYAvg);
            covariance += (XDataAvg * YDataAvg);
        }

        //Finalizes covariance calculation by dividing by the mean - 1
        covariance /= (primaryDataSet.length - 1);

        return covariance;
    }

    /**
     * Used to find the variance of one set of data
     * @param primaryDataSet: The set of data you want to find the variance of.
     *                        Note: The set of data must be doubles
     * @return Variance of the data set
     */
    public static double variance(double[] primaryDataSet){

        double primaryXAvg = 0;
        double XDataAvg = 0;
        double variance = 0;

        //loops through data set and adds all values
        for(int i = 0; i < primaryDataSet.length; i++){
            primaryXAvg += primaryDataSet[i];
        }

        //Finds the average for the data set
        primaryXAvg /= primaryDataSet.length;

        //loops through array and squares the data in data set minus the average
        for(int i = 0; i < primaryDataSet.length; i++){
            XDataAvg = Math.pow(primaryDataSet[i] - primaryXAvg, 2);
            variance += XDataAvg;
        }

        //Finalizes variance calculation by dividing by the total elements in data set
        variance /= primaryDataSet.length;

        return variance;
    }

    /**
     * Used to find the daily percent change in a stock
     * @param primaryPrice: The price of the stock on the current day
     * @param secondaryPrice: The price of the stock on the next day
     * @return Percentage of the daily change
     */
    public static double dailyStockPercentChange(double primaryPrice, double secondaryPrice){
        return (primaryPrice - secondaryPrice) / primaryPrice * 100;
    }
}
