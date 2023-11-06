package com.chanwook.demo.domain.auth.api;

import com.chanwook.demo.domain.auth.api.service.UserSignupCommand;

public interface UserSignupUsecase {
	/**
     * 회원가입 링크 선택
     *
     * <ol>
     * <li>필수 사용자 정보 입력</li>
     * </ol>
     *
     * @param UserSignupCommand
     */
	void checkRequired(UserSignupCommand signup);

	/**
     * 회원 생성 요청
     *
     * <ol>
     * <li>회원 정보 유효 확인</li>
     * <li>회원 계정 생성됨</li>
     * </ol>
     *
     * @param UserSignupCommand
     */
	void requestSignup(UserSignupCommand signup);

	/**
     * 인증메일 재전송
     *
     * @param email
     */
	void resendMail(String email);
}
