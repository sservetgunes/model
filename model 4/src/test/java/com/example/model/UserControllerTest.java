package com.example.model;
import com.example.model.veri.Identity;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


import com.example.model.controller.UserController;
import com.example.model.repository.UserRepository;
import com.example.model.veri.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;


@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;
    @Test
    public void addUserTest() throws Exception{
        String username= "sservetgunes";
        String password="gunes123";
        String name="servet";
        String surname="gunes";
        String email="servetgunes662@gmail.com";

        User user = new User(username, password, new Identity(name, surname, email));

        mockMvc.perform(post("/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(user)))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login.html"));



    }
    @Test
    public void loginUserTest() throws Exception{
        String username="sservetgunes";
        String password="gunes123";

        User user = new User(username, password, null);
        userRepository.save(user);



        mockMvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(user)))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/welcome.html?username=null"));
    }


    @Test
    public void successfulSignupRedirectTest() throws Exception{
        String username= "sservetgunes";
        String password="gunes123";
        String name="servet";
        String surname="gunes";
        String email="servetgunes662@gmail.com";

        User user = new User(username, password, new Identity(name, surname, email));


        mockMvc.perform(post("/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(user)))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login.html"));
    }
    @Test
    public void successfulLoginRedirectTest() throws Exception{
        String username="sservetgunes";
        String password="gunes123";

        User user = new User(username, password, null);
        userRepository.save(user);


        mockMvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(user)))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/welcome.html?username=null"));
    }
    @Test
    public void failedLoginRedirectTest() throws Exception{
        String username="sservetgunes";
        String password="wrong_password";

        User user = new User(username, password, null);
        userRepository.save(user);
         mockMvc.perform(post("/login")
                         .contentType(MediaType.APPLICATION_JSON)
                                 .content(asJsonString(user)))
                 .andExpect(status().is3xxRedirection())
                 .andExpect(redirectedUrl("/login.html"));
    }
    private String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}




