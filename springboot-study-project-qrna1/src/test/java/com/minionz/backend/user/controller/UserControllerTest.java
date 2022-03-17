package com.minionz.backend.user.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.minionz.backend.ApiDocument;
import com.minionz.backend.common.domain.Address;
import com.minionz.backend.common.domain.Message;
import com.minionz.backend.common.exception.BadRequestException;
import com.minionz.backend.common.exception.NotFoundException;
import com.minionz.backend.user.controller.dto.JoinRequestDto;
import com.minionz.backend.user.controller.dto.LoginRequestDto;
import com.minionz.backend.user.controller.dto.LoginResponseDto;
import com.minionz.backend.user.controller.dto.UserPageResponseDto;
import com.minionz.backend.user.domain.User;
import com.minionz.backend.user.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.BDDMockito.willThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
@MockBean(JpaMetamodelMappingContext.class)
@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
class UserControllerTest extends ApiDocument {

    @MockBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    @DisplayName("유저 로그인 성공")
    @Test
    public void 유저로그인테스트_성공() throws Exception {
        Long id = 1L;
        final LoginRequestDto LoginRequestDto = new LoginRequestDto("email", "password");
        final LoginResponseDto loginResponseDto = new LoginResponseDto(id, new Message("로그인 성공"));
        willReturn(loginResponseDto).given(userService).login(any(LoginRequestDto.class));
        final ResultActions resultActions = 유저_로그인_요청(LoginRequestDto);
        유저_로그인_성공(loginResponseDto, resultActions);
    }

    @DisplayName("유저 로그인 실패")
    @Test
    public void 유저로그인테스트_실패() throws Exception {
        final LoginRequestDto LoginRequestDto = new LoginRequestDto("email1", "password");
        Message errorMessage = new Message("로그인 실패");
        willThrow(new NotFoundException("로그인 실패")).given(userService).login(any(LoginRequestDto.class));
        final ResultActions resultActions = 유저_로그인_요청(LoginRequestDto);
        유저_로그인_실패(errorMessage, resultActions);
    }

    @DisplayName("유저 로그아웃 성공")
    @Test
    public void 유저로그아웃테스트_성공() throws Exception {
        final Long id = 1L;
        Message message = new Message("로그아웃 성공");
        willReturn(message).given(userService).logout(any(Long.class));
        final ResultActions resultActions = 유저_로그아웃_요청(id);
        유저_로그아웃_성공(resultActions);
    }

    @DisplayName("유저 로그아웃 실패")
    @Test
    public void 유저로그아웃테스트_실패() throws Exception {
        final Long id = 1L;
        Message errorMessage = new Message("로그아웃 실패");
        willThrow(new NotFoundException("로그아웃 실패")).given(userService).logout(any(Long.class));
        final ResultActions resultActions = 유저_로그아웃_요청(id);
        유저_로그아웃_실패(errorMessage, resultActions);
    }

    @DisplayName("유저 회원가입 성공")
    @Test
    void 유저회원가입_성공() throws Exception {
        Address address = new Address("안산시", "상록구", "성포동", 1.0, 2.0);
        JoinRequestDto signUpRequest = JoinRequestDto.builder()
                .name("정재욱")
                .email("operation@naver.com")
                .nickName("라이언")
                .telNumber("11111111")
                .password("1234")
                .address(address)
                .build();
        Message message = new Message("회원가입 성공");
        willReturn(message).given(userService).signUp(any(JoinRequestDto.class));
        final ResultActions response = 유저_회원가입_요청(signUpRequest);
        유저_회원가입_성공(message, response);
    }

    @DisplayName("유저 회원가입 실패")
    @Test
    void 유저회원가입_실패() throws Exception {
        final Address address = new Address("안산시", "상록구", "성포동", 1.0, 2.0);
        JoinRequestDto signUpRequest = JoinRequestDto.builder()
                .name("정재욱")
                .email("operation@naver.com")
                .nickName("라이언")
                .telNumber("11111111")
                .password("1234")
                .address(address)
                .build();
        Message errorMessage = new Message("회원가입 실패");
        willThrow(new BadRequestException("회원가입 실패")).given(userService).signUp(any(JoinRequestDto.class));
        final ResultActions response = 유저_회원가입_요청(signUpRequest);
        유저_회원가입_실패(errorMessage, response);
    }

    @DisplayName("유저회원탈퇴 성공")
    @Test
    void 유저회원탈퇴_성공() throws Exception {
        final Long id = 1L;
        Message message = new Message("회원탈퇴 성공");
        willReturn(message).given(userService).withdraw(any(Long.class));
        final ResultActions response = 유저_회원탈퇴_요청(id);
        유저_회원탈퇴_성공(response);
    }

    @DisplayName("유저회원탈퇴 실패")
    @Test
    void 유저회원탈퇴_실패() throws Exception {
        final Long id = 1L;
        Message errorMessage = new Message("회원탈퇴 실패");
        willThrow(new NotFoundException("회원탈퇴 실패")).given(userService).withdraw(any(Long.class));
        final ResultActions response = 유저_회원탈퇴_요청(id);
        유저_회원탈퇴_실패(errorMessage, response);
    }

    @DisplayName("유저 마이페이지 조회 성공")
    @Test
    void 유저_마이페이지_조회_성공() throws Exception {
        Long id = 1L;
        Address userAddress = new Address("안산시", "상록구", "월피동", 1.0, 2.0);
        Address address = Address.builder().zipcode("111-222").street("구월동").city("인천시 남동구").build();
        LocalDateTime visitedDate = LocalDateTime.now();
        User user = User.builder()
                .nickName("바보")
                .name("정재욱")
                .address(userAddress)
                .telNumber("010-2242-5567")
                .build();
        UserPageResponseDto userPageResponseDto = new UserPageResponseDto(user);
        willReturn(userPageResponseDto).given(userService).viewMyPage(any(Long.class));
        ResultActions resultActions = 유저_마이페이지_조회_요청(id);
        유저_마이페이지_조회_성공(resultActions, userPageResponseDto);
    }

    @DisplayName("유저 마이페이지 조회 실패")
    @Test
    void 유저_마이페이지_조회_실패() throws Exception {
        Long id = 1L;
        Message errorMessage = new Message("해당 유저가 존재하지 않습니다.");
        willThrow(new NotFoundException("해당 유저가 존재하지 않습니다.")).given(userService).viewMyPage(any(Long.class));
        ResultActions resultActions = 유저_마이페이지_조회_요청(id);
        유저_마이페이지_조회_실패(resultActions, errorMessage);
    }

    private void 유저_마이페이지_조회_성공(ResultActions response, UserPageResponseDto userPageResponseDto) throws Exception {
        response.andExpect(status().isOk())
                .andExpect(content().json(toJson(userPageResponseDto)))
                .andDo(print())
                .andDo(toDocument("user-view-page-success"));
    }

    private void 유저_마이페이지_조회_실패(ResultActions response, Message errorMessage) throws Exception {
        response.andExpect(status().isNotFound())
                .andExpect(content().json(toJson(errorMessage)))
                .andDo(print())
                .andDo(toDocument("user-view-page-fail"));
    }

    private ResultActions 유저_마이페이지_조회_요청(Long id) throws Exception {
        return mockMvc.perform(get("/api/v1/users/page/" + id));
    }

    private ResultActions 유저_회원가입_요청(JoinRequestDto signUpRequest) throws Exception {
        return mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/users/join")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(signUpRequest)));
    }

    private ResultActions 유저_회원탈퇴_요청(Long id) throws Exception {
        return mockMvc.perform(delete("/api/v1/users/withdraw/" + id));
    }

    private void 유저_회원가입_성공(Message message, ResultActions response) throws Exception {
        response.andExpect(status().isCreated())
                .andExpect(content().json(toJson(message)))
                .andDo(print())
                .andDo(toDocument("user-signup-success"));
    }

    private void 유저_회원가입_실패(Message errorMessage, ResultActions response) throws Exception {
        response.andExpect(status().isBadRequest())
                .andExpect(content().json(toJson(errorMessage)))
                .andDo(print())
                .andDo(toDocument("user-signup-fail"));
    }

    private void 유저_회원탈퇴_성공(ResultActions response) throws Exception {
        response.andExpect(status().isNoContent())
                .andDo(print())
                .andDo(toDocument("user-withdraw-success"));
    }

    private void 유저_회원탈퇴_실패(Message errorMessage, ResultActions response) throws Exception {
        response.andExpect(status().isNotFound())
                .andExpect(content().json(toJson(errorMessage)))
                .andDo(print())
                .andDo(toDocument("user-withdraw-fail"));
    }

    private void 유저_로그아웃_실패(Message errorMessage, ResultActions resultActions) throws Exception {
        resultActions.andExpect(status().isNotFound())
                .andExpect(content().json(toJson(errorMessage)))
                .andDo(print())
                .andDo(toDocument("user-logout-fail"));
    }

    private void 유저_로그아웃_성공(ResultActions resultActions) throws Exception {
        resultActions.andExpect(status().isNoContent())
                .andDo(print())
                .andDo(toDocument("user-logout-success"));
    }

    private ResultActions 유저_로그아웃_요청(Long id) throws Exception {
        return mockMvc.perform(get("/api/v1/users/logout/" + id));
    }

    private void 유저_로그인_실패(Message errorMessage, ResultActions resultActions) throws Exception {
        resultActions.andExpect(status().isNotFound())
                .andExpect(content().json(toJson(errorMessage)))
                .andDo(print())
                .andDo(toDocument("user-login-fail"));
    }

    private void 유저_로그인_성공(LoginResponseDto loginResponseDto, ResultActions resultActions) throws Exception {
        resultActions.andExpect(status().isOk())
                .andExpect(content().json(toJson(loginResponseDto)))
                .andDo(print())
                .andDo(toDocument("user-login-success"));
    }

    private ResultActions 유저_로그인_요청(LoginRequestDto LoginRequestDto) throws Exception {
        String content = objectMapper.writeValueAsString(LoginRequestDto);
        return mockMvc.perform(post("/api/v1/users/login")
                .content(content)
                .contentType(MediaType.APPLICATION_JSON));
    }
}
