package com.currencyconverter;

import com.currencyconverter.config.security.Router;
import com.currencyconverter.model.User;
import com.currencyconverter.service.UserService;
import com.currencyconverter.util.api.ExchengeApiHelp;
import com.currencyconverter.util.api.data.RequestData;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static net.bytebuddy.matcher.ElementMatchers.is;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.IsIterableContaining.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class CurrencyConverterApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserService userService;

    @Test
    void shouldValidateNameAndPasswordLength() throws Exception {
        String username = IntStream.range(0, 51).mapToObj(String::valueOf).collect(Collectors.joining());
        String password = IntStream.range(0, 11).mapToObj(String::valueOf).collect(Collectors.joining());

        User user = new User.Builder()
            .username(username)
            .password(password)
            .build();

        ObjectMapper mapper = getObjectMapper();

        this.mockMvc
            .perform(
                post(Router.USER)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(mapper.writeValueAsBytes(user))
            )
            .andDo(print())
            .andExpect(status().isUnprocessableEntity())
            .andExpect(
                jsonPath(
                    "$.message",
                    is("Error processing request!")
                ).exists()
            )
            .andExpect(
                jsonPath(
                    "$.statusCode",
                    is(status().isUnprocessableEntity())
                ).exists()
            )
            .andExpect(
                jsonPath(
                    "$.errors"
                ).isArray()
            )
            .andExpect(
                jsonPath(
                    "$.errors",
                    hasSize(2)
                )
            )
            .andExpect(
                jsonPath(
                    "$.errors",
                    hasItem("User password cannot be longer than 10 characters.")
                )
            )
            .andExpect(
                jsonPath(
                    "$.errors",
                    hasItem("The username cannot be longer than 50 characters.")
                )
            );
    }

    @Test
    void shouldEnterAUser() throws Exception {
        String uuid = UUID.randomUUID().toString();

        User user = new User.Builder()
            .username(uuid)
            .password(uuid.substring(0, 8))
            .build();

        ObjectMapper mapper = getObjectMapper();

        this.mockMvc
            .perform(
                post(Router.USER)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(mapper.writeValueAsBytes(user))
            )
            .andDo(print())
            .andExpect(status().isCreated())
            .andExpect(
                jsonPath(
                    "$.message",
                    is("Request processed successfully!")
                ).exists()
            )
            .andExpect(
                jsonPath(
                    "$.statusCode",
                    status().isCreated()
                ).exists()
            )
            .andExpect(
                jsonPath(
                    "$.data.username",
                    is(user.getUsername())
                ).isNotEmpty()
            )
            .andExpect(
                jsonPath(
                    "$.data.password",
                    is(user.getPassword())
                ).isNotEmpty()
            );
    }

    @Test
    void shouldValidateUrlApiExchenge() {
        String URL = "http://api.exchangeratesapi.io/latest?access_key=bca312a1593dcd663a55085bf1ab3203&base=EUR";
        assertThat(ExchengeApiHelp.getUrl())
            .isEqualTo(URL);
    }

    @Test
    void shouldValidateIfTheFromParameterWasInformed() throws Exception {
        User user = getUser();

        ObjectMapper mapper = getObjectMapper();

        RequestData requestData = new RequestData.Builder()
            .to("BRL")
            .amount(BigDecimal.valueOf(2))
            .user(user)
            .build();

        this.mockMvc
            .perform(
                post(Router.TRANSACTION + "/convert")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(mapper.writeValueAsBytes(requestData))
            )
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(
                jsonPath(
                    "$.message",
                    is("Request processed successfully!")
                ).exists()
            )
            .andExpect(
                jsonPath(
                    "$.statusCode",
                    is(status().isBadRequest())
                ).exists()
            )
            .andExpect(
                jsonPath(
                    "$.data",
                    is("The origin must be informed.")
                ).exists()
            );
    }

    @Test
    void shouldValidateIfTheToParameterWasInformed() throws Exception {
        User user = getUser();

        ObjectMapper mapper = getObjectMapper();

        RequestData requestData = new RequestData.Builder()
            .from("BRL")
            .amount(BigDecimal.valueOf(2))
            .user(user)
            .build();

        this.mockMvc
            .perform(
                post(Router.TRANSACTION + "/convert")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(mapper.writeValueAsBytes(requestData))
            )
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(
                jsonPath(
                    "$.message",
                    is("Request processed successfully!")
                ).exists()
            )
            .andExpect(
                jsonPath(
                    "$.statusCode",
                    is(status().isBadRequest())
                ).exists()
            )
            .andExpect(
                jsonPath(
                    "$.data",
                    is("The destination must be informed.")
                ).exists()
            );
    }

    @Test
    void shouldValidateIfTheAmountParameterWasInformed() throws Exception {
        User user = getUser();

        ObjectMapper mapper = getObjectMapper();

        RequestData requestData = new RequestData.Builder()
            .from("BRL")
            .to("EUR")
            .user(user)
            .build();

        this.mockMvc
            .perform(
                post(Router.TRANSACTION + "/convert")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(mapper.writeValueAsBytes(requestData))
            )
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(
                jsonPath(
                    "$.message",
                    is("Request processed successfully!")
                ).exists()
            )
            .andExpect(
                jsonPath(
                    "$.statusCode",
                    is(status().isBadRequest())
                ).exists()
            )
            .andExpect(
                jsonPath(
                    "$.data",
                    is("The amount must be informed.")
                ).exists()
            );
    }

    @Test
    void shouldValidateIfTheAmountParameterIsGreaterThanZero() throws Exception {
        User user = getUser();

        ObjectMapper mapper = getObjectMapper();

        RequestData requestData = new RequestData.Builder()
            .from("BRL")
            .to("EUR")
            .amount(BigDecimal.valueOf(0))
            .user(user)
            .build();

        this.mockMvc
            .perform(
                post(Router.TRANSACTION + "/convert")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(mapper.writeValueAsBytes(requestData))
            )
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(
                jsonPath(
                    "$.message",
                    is("Request processed successfully!")
                ).exists()
            )
            .andExpect(
                jsonPath(
                    "$.statusCode",
                    is(status().isBadRequest())
                ).exists()
            )
            .andExpect(
                jsonPath(
                    "$.data",
                    is("The value must be greater than zero.")
                ).exists()
            );
    }

    @Test
    void shouldValidateIfTheUserParameterWasInformed() throws Exception {
        ObjectMapper mapper = getObjectMapper();

        RequestData requestData = new RequestData.Builder()
            .from("BRL")
            .to("EUR")
            .build();

        this.mockMvc
            .perform(
                post(Router.TRANSACTION + "/convert")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(mapper.writeValueAsBytes(requestData))
            )
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(
                jsonPath(
                    "$.message",
                    is("Request processed successfully!")
                ).exists()
            )
            .andExpect(
                jsonPath(
                    "$.statusCode",
                    is(status().isBadRequest())
                ).exists()
            )
            .andExpect(
                jsonPath(
                    "$.data",
                    is("The user must be informed.")
                ).exists()
            );
    }

    @Test
    void shouldValidateIfTheTransactionWasSuccessful() throws Exception {
        String uuid = UUID.randomUUID().toString();

        User user = new User.Builder()
            .username(uuid)
            .password(uuid.substring(0, 8))
            .build();

        userService.save(user);

        ObjectMapper mapper = getObjectMapper();

        RequestData requestData = new RequestData.Builder()
            .from("BRL")
            .to("JPY")
            .amount(BigDecimal.valueOf(2))
            .user(user)
            .build();

        this.mockMvc
            .perform(
                post(Router.TRANSACTION + "/convert")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(mapper.writeValueAsBytes(requestData))
            )
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(
                jsonPath(
                    "$.message",
                    is("Request processed successfully!")
                ).exists()
            )
            .andExpect(
                jsonPath(
                    "$.statusCode",
                    is(status().isOk())
                ).exists()
            )
            .andExpect(jsonPath("$.data.id").isNotEmpty())
            .andExpect(jsonPath("$.data.date").isNotEmpty())
            .andExpect(
                jsonPath(
                    "$.data.sourceCurrency",
                    is(requestData.getFrom())
                ).isNotEmpty()
            )
            .andExpect(jsonPath("$.data.sourceValue").isNotEmpty())
            .andExpect(
                jsonPath(
                    "$.data.destinationCurrency",
                    is(requestData.getTo())
                ).isNotEmpty()
            )
            .andExpect(jsonPath("$.data.destinationValue").isNotEmpty())
            .andExpect(jsonPath("$.data.conversionRate").isNotEmpty())
            .andExpect(
                jsonPath(
                    "$.data.user.id",
                    is(user.getId())
                ).isNotEmpty()
            );
    }

    private User getUser() {
        return new User.Builder()
            .id(9999L)
            .build();
    }

    private ObjectMapper getObjectMapper() {
        return JsonMapper.builder()
            .findAndAddModules()
            .build();
    }
}
