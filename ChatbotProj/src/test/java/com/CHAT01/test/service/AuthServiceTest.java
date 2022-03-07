package test.service;


import com.CHAT01.connector.dlp.google.token.AccessTokenRestConnector;
import com.CHAT01.connector.dlp.google.token.dto.AccessTokenResponse;
import com.CHAT01.connector.dlp.google.token.model.ChannelEnum;
import com.CHAT01.connector.dlp.google.token.transformer.AccessTokenRestRequestTransformer;
import com.CHAT01.connector.dlp.google.token.transformer.AccessTokenRestResponseTransformer;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.HttpClientErrorException;
import com.CHAT01.service.google.AuthService;
import test.BaseTest;

import static org.mockito.Mockito.*;



@RunWith(SpringJUnit4ClassRunner.class)
public class AuthServiceTest extends BaseTest {

    @SpyBean private AccessTokenRestRequestTransformer accessTokenRestRequestTransformer;
    @SpyBean private AccessTokenRestResponseTransformer accessTokenRestResponseTransformer;
    @SpyBean private AccessTokenRestConnector connector;
    @MockBean private AuthService authService;

   private AccessTokenResponse outputResponse;

    @Before
    public void init(){
        outputResponse = new AccessTokenResponse();
        outputResponse.setAccess_token("Token");
        outputResponse.setExpires_in((long) 5);
    }

 @Test
    public void getAccessTokenTestOK() {
        when(connector.call(any(),accessTokenRestRequestTransformer,accessTokenRestResponseTransformer)).thenReturn(outputResponse);
        authService.autorefreshAccessToken();
      assertEquals("True",authService.getAccessToken(ChannelEnum.APP));
}

@Test
    public void getAccessTokenKO(){
    when(connector.call(any(),accessTokenRestRequestTransformer,accessTokenRestResponseTransformer)).thenThrow(HttpClientErrorException.class );
 try{
        authService.autorefreshAccessToken();
    }
    catch (Exception exception){
     assert(true);
    }
}


}
