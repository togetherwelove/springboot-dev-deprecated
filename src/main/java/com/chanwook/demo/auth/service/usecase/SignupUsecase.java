package com.chanwook.demo.auth.service.usecase;

import com.chanwook.demo.auth.api.vo.SignupVO;

public interface SignupUsecase {
	/**
     * 회원가입 링크 선택
     * 
     * <ol>
     * <li>필수 사용자 정보 입력</li>
     * </ol>
     * 
     * @param SignupVO
     */
	void checkRequired(SignupVO signup);
	
	/**
     * 회원 생성 요청
     * 
     * <ol>
     * <li>회원 정보 유효 확인</li>
     * <li>회원 계정 생성됨</li>
     * </ol>
     * 
     * @param SignupVO
     */
	void requestSignup(SignupVO signup);
}
