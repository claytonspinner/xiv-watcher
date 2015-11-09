package com.claytonspinner.poc.controller

import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import org.jsoup.select.Elements
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * CryptoController Class
 *
 * Created by Clayton Spinner on 2015-05-02 1:09 PM
 */

@RestController
@RequestMapping("/v1/crypto")
class CryptoController {

    @RequestMapping("")
    public Map get() {
        String url
        InputStream is = null
        BufferedReader br
        String line = null
        String response = null
        StringBuilder contentBuilder = new StringBuilder()

        url = "https://www.cryptonator.com/rates"
        System.setProperty("javax.net.ssl.trustStore", "C:\\dev\\duckduckgo\\cryptonator.jks")
        Document document = null
        try {
            document = Jsoup.connect(url).get()
        } catch (IOException e) {
            e.printStackTrace()
        }
        Elements headers = document.select("h2")
        Map<String, Integer> currencyMap = [:]
        headers.each { header ->
            String currentCurrency = header.nextElementSibling()?.child(0)?.child(1)?.child(0)?.child(0)?.text()
            print(currentCurrency + "\n")
            if (currentCurrency != null) {
                Elements currencies = header.nextElementSibling()?.child(0)?.children()
                currencies = currencies[1..<currencies.size()]
                currencies.each { tr ->
                    String currencyText = tr?.child(1)?.child(0)?.text()
                    //?.child(0)?.child(0)?.text()
                    if (currencyText != null && currencyMap.containsKey(currencyText)) {
                        currencyMap[currencyText] = currencyMap[currencyText] + 1
                    } else if (currencyText != null) {
                        currencyMap.put(currencyText, 1)
                    }
                }
            }
        }
        //com s = Double.parseDouble(document.select("span[class=time_rtq_ticker]").first().child(0).text())
        currencyMap
    }
}