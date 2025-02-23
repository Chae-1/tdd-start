package com.example.chapter3;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import org.junit.jupiter.api.Test;

/**
 * 테스트 작성 연습.
 * <p>
 * 매달 비용을 지불해야 사용할 수 있는 유료 서비스가 존재한다.
 * <p>
 * 이 서비스에서는 다음 규칙에 따라 서비스 만료일을 결정한다.
 * 1. 서비스를 사용하려면 매달 1만 원을 선불로 납부해야한다.
 * - 납부일을 기준으로 한 달 뒤가 서비스 만료일이 된다.
 * 2. 2개월 이상 요금을 납부할 수 있다.
 * 3. 10만 원을 납부하면 서비스를 1년 제공한다. 납부 금액을 기준으로 서비스 만료일이 결정된다.
 * <p>
 * <p>
 * 1. 가장 간단한 것부터 구현.
 * - 하나의 테스트 케이스를 만들고 그를 반환하도록 테스트 대상 메서드를 만든다.
 * - 예를 추가하면서 구현을 일반화.
 * - 테스트 케이스를 하나 더 만들어서 테스트를 통과하도록 일반화.
 * <p>
 * 2. 예외 상황 처리
 */
public class ExpiryDateCalculatorTest {

    ExpiryDateCalculator cal = new ExpiryDateCalculator();

    @Test
    void payTenThousandWonThenExpiryDateShouldBeOneMonthLater() {
        assertExpiryDate(
                LocalDate.of(2024, 4, 10),
                PayData.builder()
                        .billingDate(LocalDate.of(2024, 3, 10))
                        .payAmount(10000)
                        .build());

        assertExpiryDate(LocalDate.of(2024, 5, 10),
                PayData.builder()
                        .billingDate(LocalDate.of(2024, 4, 10))
                        .payAmount(10000)
                        .build());
    }

    @Test
    void 납부일과_한달_뒤가_일자가_같지_않음() {
        assertExpiryDate(
                LocalDate.of(2024, 2, 29),
                PayData.builder()
                        .billingDate(LocalDate.of(2024, 1, 31))
                        .payAmount(10000)
                        .build()
        );
        assertExpiryDate(
                LocalDate.of(2023, 2, 28),
                PayData.builder()
                        .billingDate(LocalDate.of(2023, 1, 31))
                        .payAmount(10000)
                        .build()
        );
        assertExpiryDate(
                LocalDate.of(2024, 6, 30),
                PayData.builder()
                        .payAmount(10000)
                        .billingDate(LocalDate.of(2024, 5, 31))
                        .build()
        );
    }

    @Test
    void 첫_납부일과_만료일_일자가_다를때_이만원_이상_납부() {
        assertExpiryDate(
            LocalDate.of(2019, 4, 30),
            PayData.builder()
                    .firstBillingDate(LocalDate.of(2019, 1, 31))
                    .billingDate(LocalDate.of(2019, 2, 28))
                    .payAmount(20000)
                    .build()
        );
    }

    @Test
    void 첫_납부일과_만료일_일자가_다를때_만원_납부() {
        PayData payData = PayData.builder()
                .firstBillingDate(LocalDate.of(2019, 1, 31))
                .billingDate(LocalDate.of(2019, 2, 28))
                .payAmount(10000)
                .build();

        assertExpiryDate(LocalDate.of(2019, 3, 31), payData);

        PayData payData2 = PayData.builder()
                .firstBillingDate(LocalDate.of(2019, 1, 30))
                .billingDate(LocalDate.of(2019, 2, 28))
                .payAmount(10000)
                .build();

        assertExpiryDate(LocalDate.of(2019, 3, 30), payData2);

        PayData payData3 = PayData.builder()
                .firstBillingDate(LocalDate.of(2019, 5, 31))
                .billingDate(LocalDate.of(2019, 6, 30))
                .payAmount(10000)
                .build();

        assertExpiryDate(LocalDate.of(2019, 7, 31), payData3);
    }

    @Test
    void 이만원_이상을_납부하면_비례해서_만료일_계산() {
        assertExpiryDate(
                LocalDate.of(2019, 5, 1),
                PayData.builder()
                        .billingDate(LocalDate.of(2019, 3, 1))
                        .payAmount(20000)
                        .build()
        );

        assertExpiryDate(
                LocalDate.of(2019, 6, 1),
                PayData.builder()
                        .billingDate(LocalDate.of(2019, 3, 1))
                        .payAmount(30000)
                        .build()
        );
    }

    private void assertExpiryDate(LocalDate expectedExpiryDate, PayData payData) {
        LocalDate realExpiryDate = cal.calculateExpiryDate(payData);
        assertEquals(expectedExpiryDate, realExpiryDate);
    }
}
