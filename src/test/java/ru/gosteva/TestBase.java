package ru.gosteva;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import static com.codeborne.selenide.Configuration.baseUrl;
import static com.codeborne.selenide.Selenide.closeWebDriver;
import static com.codeborne.selenide.Selenide.open;

public class TestBase {
    @BeforeAll
    static void beforeAll() {
        baseUrl = "https://www.onetwotrip.com/";
    }

    @BeforeEach
    void beforeEach() {
        Configuration.browserSize = "1920x1080";
        open(baseUrl);
    }

    @AfterEach
    void afterEach() {
        closeWebDriver();
    }
}
