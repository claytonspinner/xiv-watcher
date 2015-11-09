package com.claytonspinner.poc.controller

import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * XivController Class
 * <p/>
 * Created by Clayton Spinner on 2015-02-03 10:58 PM
 */

@RestController
@RequestMapping("/v1/current")
public class XivController {

    //It's the ZIV you want, idiot
    @RequestMapping("")
    public String index() {
        Double vix, vxv, ratio
        String returnString = null

        vxv = getPriceFromSymbol("^VXV")
        vix = getPriceFromSymbol("^VIX")
        ratio = vix/vxv

        System.out.println("VIX price: " + vix)
        returnString = "VIX price: " + vix + "\n"
        System.out.println("VXV price: " + vxv)
        returnString = returnString + "VXV price: " + vxv + "\n"
        System.out.println("VIX/VXV ratio: " + vix/vxv)
        returnString = returnString + "VIX/VXV ratio: " + vix/vxv + "\n"
        if (ratio > 0.917) {
            System.out.println("Sell XIV")
            returnString = returnString + "Sell XIV\n"
        } else {
            System.out.println("Buy XIV")
            returnString = returnString + "Buy XIV\n"
        }

        returnString
    }

    private Double getPriceFromSymbol(String symbol) {
        String url
        InputStream is = null
        BufferedReader br
        String line = null
        String response = null
        StringBuilder contentBuilder = new StringBuilder()

        url = "http://finance.yahoo.com/q?s=" + symbol
        Document document = null
        try {
            document = Jsoup.connect(url).get()
        } catch (IOException e) {
            e.printStackTrace()
        }
        Double.parseDouble(document.select("span[class=time_rtq_ticker]").first().child(0).text())
    }

}