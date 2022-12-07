package com.project.devgram.oauth;

import com.project.devgram.oauth2.principal.PrincipalOauth2UserService;
import com.project.devgram.repository.UserRepository;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.MockBeans;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.test.context.web.WebAppConfiguration;

@MockBeans({
        @MockBean(JpaMetamodelMappingContext.class),
        @MockBean(PrincipalOauth2UserService.class),
        @MockBean(UserRepository.class)
})
@WebAppConfiguration
@SpringBootTest
@AutoConfigureMockMvc
class PrincipalOauth2UserServiceTest {



}