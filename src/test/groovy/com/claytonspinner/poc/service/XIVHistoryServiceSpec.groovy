package com.claytonspinner.poc.service

import com.claytonspinner.poc.service.impl.XIVHistoryServiceImpl
import spock.lang.Specification

/**
 * XIVHistoryServiceSpec Class
 *
 * Created by Clayton Spinner on 2015-04-12 7:32 PM
 */
class XIVHistoryServiceSpec extends Specification {

    def XIVHistoryService historyService

    def setup() {
        historyService = new XIVHistoryServiceImpl()
    }

    def "getting data from csv works" () {
        when:
        def list = historyService.getHistoryDataByTicker("vix")

        then:
        list != null
    }

    def "Test getting ROI" () {
        when:
        def map = historyService.getROI(1000.00, '2010-11-30', '2015-04-10', 10.00)

        then:
        map != null
    }
}
