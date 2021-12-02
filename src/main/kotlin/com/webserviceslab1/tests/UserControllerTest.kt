package com.webserviceslab1.tests

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.ObjectWriter
import com.webserviceslab1.helpers.UserRequest
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@Suppress("FunctionName")
@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner::class)
class UserControllerTest {

    @Autowired
    lateinit var mockMvc: MockMvc

    val ow: ObjectWriter = ObjectMapper().writer().withDefaultPrettyPrinter()

    @Test
    fun `user should not be able to register twice`() {
        mockMvc.perform(
            MockMvcRequestBuilders.post("/api/user/register").contentType(MediaType.APPLICATION_JSON).content(
                ow.writeValueAsString(
                    UserRequest("test", "test", "test")
                )
            )
        ).andExpect(status().isOk)
            .andDo {
                mockMvc.perform(
                    MockMvcRequestBuilders.post("/api/user/register").contentType(MediaType.APPLICATION_JSON).content(
                        ow.writeValueAsString(
                            UserRequest("test", "test", "test")
                        )
                    )
                ).andExpect(status().isInternalServerError)
            }
    }

    @Test
    fun `user should be able to login after registering`() {
        mockMvc.perform(
            MockMvcRequestBuilders.post("/api/user/register").contentType(MediaType.APPLICATION_JSON).content(
                ow.writeValueAsString(
                    UserRequest("login", "test", "test")
                )
            )
        ).andExpect(status().isOk)
            .andDo {
                mockMvc.perform(
                    MockMvcRequestBuilders.post("/api/user/login").contentType(MediaType.APPLICATION_JSON).content(
                        ow.writeValueAsString(
                            UserRequest("login", "test", "test")
                        )
                    )
                ).andExpect(status().isOk)
            }
    }

    @Test
    fun `user should be able to remove existing user`() {
        mockMvc.perform(
            MockMvcRequestBuilders.post("/api/user/register").contentType(MediaType.APPLICATION_JSON).content(
                ow.writeValueAsString(
                    UserRequest("remove", "test", "test")
                )
            )
        ).andExpect(status().isOk)
            .andDo {
                mockMvc.perform(
                    MockMvcRequestBuilders.delete("/api/user/").contentType(MediaType.APPLICATION_JSON).content(
                        ow.writeValueAsString(
                            UserRequest("remove", "test", "test")
                        )
                    )
                ).andExpect(status().isOk)
            }
    }
}