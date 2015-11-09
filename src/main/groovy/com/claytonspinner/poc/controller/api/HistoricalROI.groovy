package com.claytonspinner.poc.controller.api

import com.claytonspinner.poc.domain.Transaction

/**
 * HistoricalROI Class
 *
 * Created by Clayton Spinner on 2015-04-12 7:16 PM
 */
class HistoricalROI {
    Double roi
    Map<String, Double> capitalHistory
    List<Transaction> transactionHistory
}
