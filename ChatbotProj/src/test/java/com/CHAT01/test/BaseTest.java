package test;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.CHAT01.connector.dlp.google.token.AccessTokenRestConnector;
import com.CHAT01.connector.dlp.google.token.dto.AccessTokenResponse;
import com.CHAT01.connector.dlp.google.token.transformer.AccessTokenRestRequestTransformer;
import com.CHAT01.connector.dlp.google.token.transformer.AccessTokenRestResponseTransformer;
import com.CHAT01.controller.intent.dto.DetectRequest;
import com.CHAT01.controller.intent.dto.DetectResponse;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("unittests")
@ContextConfiguration(classes = {TestConfiguration.class})
public abstract class BaseTest {
    protected static final String ACCESS_TOKEN_MOCK = randomAlphanumeric(15);
    protected static final Long EXPIRES_IN_MOCK = 3599L;
    protected static final String TOKEN_TYPE_MOCK = "Bearer";
    protected static final AccessTokenResponse ACCESS_TOKEN_RESPONSE_MOCK = new AccessTokenResponse();

    static {
        ACCESS_TOKEN_RESPONSE_MOCK.setAccess_token(ACCESS_TOKEN_MOCK);
        ACCESS_TOKEN_RESPONSE_MOCK.setExpires_in(EXPIRES_IN_MOCK);
        ACCESS_TOKEN_RESPONSE_MOCK.setToken_type(TOKEN_TYPE_MOCK);
    }

    @Autowired protected ObjectMapper objectMapper;

    @Autowired protected MockMvc mockMvc;

    @MockBean protected AccessTokenRestConnector accessTokenRestConnector;
    @MockBean protected AccessTokenRestRequestTransformer accessTokenRestRequestTransformer;
    @MockBean protected AccessTokenRestResponseTransformer accessTokenRestResponseTransformer;

    @Before
    public void initOAuthServiceMock() {
        Mockito.when(accessTokenRestConnector.call(any(), accessTokenRestRequestTransformer, accessTokenRestResponseTransformer))
                .thenReturn(ACCESS_TOKEN_RESPONSE_MOCK);
    }

    //POST
    public void postDetectIntentTest(String URI, DetectRequest request, MediaType mediaType, DetectResponse expectedResponse) throws Exception {
        mockMvc.perform(post(URI)
                .contentType(mediaType)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(expectedResponse)));
    }

    public void postDetectIntentBadRequestTest(String URI, DetectRequest request, MediaType mediaType) throws Exception {
        mockMvc.perform(post(URI)
                .contentType(mediaType)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }
}