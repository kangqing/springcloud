package com.kangqing.satokendemo;

import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
class SaTokenDemoApplicationTests {

    @Test
    void contextLoads() {
        StpUtil.setLoginId(2L);
        // 当前账号是否含有指定角色标识, 返回true或false
        final boolean test = StpUtil.hasRole("TEST");
        Assertions.assertTrue(test);

        final SaTokenInfo tokenInfo = StpUtil.getTokenInfo();
        log.info("tokenName = {}, tokenValue = {}", tokenInfo.getTokenName(), tokenInfo.getTokenValue());
    }

}
