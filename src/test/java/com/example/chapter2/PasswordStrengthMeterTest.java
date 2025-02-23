package com.example.chapter2;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/*
    패스워드의 강도를 측정하는 클래스
    -> PasswordStrengthMeter

    아래와 같은 패스워드 규칙을 만족하는 확인한다.
    1. 길이가 8자 이상
    2. 0부터 9까지의 숫자를 포함한다.
    3. 대문자를 포함하고 있다.

    규칙을 준수하는 지에 따라서 패스워드의 강도를 반환한다.
    모든 규칙을 만족한다면, Strong
    2개의 규칙을 만족한다면, Normal
    1개의 규칙을 만족한다면, Weak
 */
public class PasswordStrengthMeterTest {
    PasswordStrengthMeter meter = new PasswordStrengthMeter();

    // 첫 번째 테스트 : 가장 쉽거나 예외적인 상황을 생각해서 테스트를 만든다.
    // 모든 규칙을 만족하거나, 모든 조건을 만족하지 않을 경우가 가장 쉽게 구현할 수 있기 때문에 먼저 테스트한다.
    @Test
    void meetsAllCriteria_Then_Strong() {
        assertStrength("ab12!@AB", PasswordStrength.STRONG);
        assertStrength("abf3123!@AB", PasswordStrength.STRONG);
    }

    // 두 번째 테스트: 길이만 8글자 미만이고 나머지 조건은 충족할 경우.
    @Test
    void meetsOtherCriteria_except_for_Length_Then_Normal() {
        assertStrength("ab12!@A", PasswordStrength.NORMAL);
        assertStrength("AB12!@A", PasswordStrength.NORMAL);
    }

    // 세 번째 테스트: 숫자를 포함하지 않고 나머지 조건은 충족할 경우.
    @Test
    void meetsOtherCriteria_except_for_number_Then_Normal() {
        assertStrength("aB!@asdf", PasswordStrength.NORMAL);
        assertStrength("BADB!@asdf", PasswordStrength.NORMAL);
    }

    // 네 번째 테스트: 값이 없는 경우.
    // NPE을 항상 의심해야한다.
    // null일 때 어떻게 반응해야할까?
    // 1. IllegalArgumentException
    // 2. Null Object
    @Test
    void nullInput_Then_Invalid() {
        assertStrength(null, PasswordStrength.INVALID);
        assertStrength("", PasswordStrength.INVALID);
    }

    // 다섯 번째: 대문자를 포함하지 않고 나머지 조건은 충족할 경우.
    @Test
    void meetsOtherCriteria_except_for_Uppercase_Then_Normal() {
        assertStrength("asb!@#324", PasswordStrength.NORMAL);
    }

    // 여섯 번째: 길이 제한 조건만 만족하는 경우.
    @Test
    void meetsOnlyLengthCriteria_Then_Weak() {
        assertStrength("asbfffffe", PasswordStrength.WEAK);
    }

    // 일곱 번째: 숫자를 포함한 조건만 충족할 경우.
    @Test
    void meetsOnlyNumberCriteria_Then_Weak() {
        assertStrength("234", PasswordStrength.WEAK);
    }

    // 여덟 번째 테스트: 대문자 포함 조건만
    @Test
    void meetsOnlyUpperCaseCriteria_Then_Weak() {
        assertStrength("ABZA", PasswordStrength.WEAK);
    }

    // 아홉 번째 테스트: 아무 조건도 만족하지 않을 경우.
    @Test
    void meetsNoCriteria_Then_Weak() {
        assertStrength("abc", PasswordStrength.WEAK);
    }


    private void assertStrength(String password, PasswordStrength strong) {
        PasswordStrength result = meter.meter(password);
        assertEquals(strong, result);
    }
}
