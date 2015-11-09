package com.claytonspinner.poc.service.impl

import com.claytonspinner.poc.domain.HistoricalData
import com.claytonspinner.poc.domain.Transaction
import com.claytonspinner.poc.service.XIVHistoryService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

/**
 * XIVHistoryServiceImpl Class
 *
 * Created by Clayton Spinner on 2015-04-12 7:13 PM
 */

@Service
class XIVHistoryServiceImpl implements XIVHistoryService {

    HistoricalData[] vixHistory
    HistoricalData[] xivHistory
    HistoricalData[] vxvHistory

    public XIVHistoryServiceImpl() {
        vixHistory = getHistoryDataByTicker('vix_normalized')
        xivHistory = getHistoryDataByTicker('xiv')
        vxvHistory = getHistoryDataByTicker('vxv_normalized')
    }

    @Override
    Map getROI(Double startingCapital, String startDate, String endDate, Double commission) {
        Map returnMap = [
                roi: 0.0,
                capital: [:],
                transactionHistory: [:]
        ]
        Integer startDateIndex = vixHistory.findIndexOf { vixEntry ->
            vixEntry.date == startDate
        }
        Integer endDateIndex = vixHistory.findIndexOf { vixEntry ->
            vixEntry.date == endDate
        }
        List vixSubList = vixHistory.findAll { startDate <= it['date'] && it['date'] <= endDate }
        List xivSubList = xivHistory.findAll { startDate <= it['date'] && it['date'] <= endDate }
        List vxvSubList = vxvHistory.findAll { startDate <= it['date'] && it['date'] <= endDate }

        double capital = startingCapital
        int freeriding = 0
        double shares = 0

        // Don't worry about free riding for now
        for (int i = 0; i < endDateIndex - startDateIndex; i++) {
            capital = capital.round(2)
            /*if (freeriding > 0) {
                freeriding--
            }*/
            // First thing, set the current capital value
            returnMap.capital.put(xivSubList[i].date, capital + (shares * xivSubList[i].open))

            def ratio = vixSubList.get(i).adjustedClose / vxvSubList.get(i).adjustedClose

            // BUY
            if (ratio < 0.917 && Math.floor(capital/xivSubList[i + 1].open) > 0) {
                // Assume you buy it at the start of next day
                int sharesToBuy = Math.floor(capital/xivSubList[i + 1].open)
                shares = shares + sharesToBuy
                // Remaining capital
                capital = capital - (sharesToBuy * xivSubList[i + 1].open)
                returnMap.transactionHistory.put( xivSubList[i + 1].date, new Transaction(date: xivSubList[i + 1].date,shares: shares,price: xivSubList[i + 1].open,capital: capital))
            } else if (ratio > 0.917 && shares > 0) {
                capital = capital + (shares * xivSubList[i + 1].open)
                shares = 0
                returnMap.transactionHistory.put( xivSubList[i + 1].date, new Transaction(date: xivSubList[i + 1].date,shares: shares,price: xivSubList[i + 1].open,capital: capital))
            }
        }
        returnMap.put('roi', capital + (shares * xivSubList[endDateIndex - startDateIndex].open))
        returnMap
    }

    @Override
    public HistoricalData[] getHistoryDataByTicker(String tickerSymbol) {
        def historicalDataList = []
        def String filename = tickerSymbol + ".csv"
        InputStream inputFile = this.getClass().classLoader.getResourceAsStream(filename)
        String[] lines = inputFile.text.split('\n')
        lines = lines.reverse()
        lines.each {line ->
            String[] fields = line.split(',')
            HistoricalData historicalData = new HistoricalData()
            historicalData.date = fields[0]
            historicalData.open = fields[1].toDouble()
            historicalData.high = fields[2].toDouble()
            historicalData.low = fields[3].toDouble()
            historicalData.close = fields[4].toDouble()
            historicalData.volume = fields[5].toDouble()
            historicalData.adjustedClose = fields[6].toDouble()
            historicalDataList.add(historicalData)
        }
        historicalDataList
    }
}
