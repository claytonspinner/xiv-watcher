package com.claytonspinner.poc.service

import com.claytonspinner.poc.domain.HistoricalData

/**
 * XIVHistoryService Class
 *
 * Created by Clayton Spinner on 2015-04-12 7:13 PM
 */
public interface XIVHistoryService {

    Map getROI(Double startingCapital, String startDate, String endDate, Double commission)

    HistoricalData[] getHistoryDataByTicker(String tickerSymbol)
}