package com.claytonspinner.poc.controller

import com.claytonspinner.poc.service.XIVHistoryService
import com.claytonspinner.poc.service.impl.XIVHistoryServiceImpl
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

/**
 * HistoryController Class
 *
 * Created by Clayton Spinner on 2015-04-12 6:51 PM
 */
@RestController
@RequestMapping("/v1/history")
class HistoryController {

    XIVHistoryService xivHistoryService

    @Autowired
    HistoryController(XIVHistoryService xivHistoryService) {
        this.xivHistoryService = xivHistoryService
    }

    @RequestMapping("")
    public Map getHistory(
            @RequestParam("capital") Double capital,
            @RequestParam("startDate") String startDate,
            @RequestParam("endDate") String endDate,
            @RequestParam("commission") Double commission) {
        xivHistoryService.getROI(capital, startDate, endDate, commission)
    }
}
