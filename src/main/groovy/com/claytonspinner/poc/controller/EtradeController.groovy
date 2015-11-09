package com.claytonspinner.poc.controller

import com.etrade.etws.account.Account;
import com.etrade.etws.account.AccountListResponse;
import com.etrade.etws.oauth.sdk.client.IOAuthClient;
import com.etrade.etws.oauth.sdk.client.OAuthClientImpl;
import com.etrade.etws.oauth.sdk.common.Token;
import com.etrade.etws.sdk.client.ClientRequest
import com.etrade.etws.sdk.client.Environment
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

import java.awt.Desktop;

/**
 * EtradeController Class
 *
 * Created by Clayton Spinner on 2015-02-21 1:35 PM
 */
@RestController
class EtradeController {

    // Variables
    public IOAuthClient client = null;
    public ClientRequest request = null;
    public Token token = null;
    public String oauth_consumer_key = null; // Your consumer key
    public String oauth_consumer_secret = null; // Your consumer secret
    public String oauth_request_token = null; // Request token
    public String oauth_request_token_secret = null; // Request token secret
    public String authorizeURL = null;

    @RequestMapping("/etrade")
    public index() {
        client = OAuthClientImpl.getInstance(); // Instantiate IOAUthClient
        request = new ClientRequest(); // Instantiate ClientRequest
        request.setEnv(Environment.SANDBOX); // Use sandbox environment

        request.setConsumerKey(oauth_consumer_key); //Set consumer key
        request.setConsumerSecret(oauth_consumer_secret); // Set consumer secret
        token= client.getRequestToken(request); // Get request-token object
        oauth_request_token  = token.getToken(); // Get token string
        oauth_request_token_secret = token.getSecret(); // Get token secret

        authorizeURL = client.getAuthorizeUrl(request); // E*TRADE authorization URL
        URI uri = new java.net.URI(authorizeURL);
        Desktop desktop = Desktop.getDesktop();
        desktop.browse(authorizeURL);
    }
}
