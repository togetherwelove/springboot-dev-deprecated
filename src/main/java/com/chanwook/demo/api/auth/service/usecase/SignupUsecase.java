package com.chanwook.demo.api.auth.service.usecase;

import com.chanwook.demo.api.auth.service.vo.SignupRequest;

public interface SignupUsecase {
	/**
     * 회원가입 링크 선택
     *
     * <ol>
     * <li>필수 사용자 정보 입력</li>
     * </ol>
     *
     * @param SignupRequest
     */
	void checkRequired(SignupRequest signup);

	/**
     * 회원 생성 요청
     *
     * <ol>
     * <li>회원 정보 유효 확인</li>
     * <li>회원 계정 생성됨</li>
     * </ol>
     *
     * @param SignupRequest
     */
	void requestSignup(SignupRequest signup);
}
