package ru.gosteva;

import com.codeborne.selenide.CollectionCondition;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static com.codeborne.selenide.Configuration.baseUrl;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.WebDriverConditions.currentFrameUrl;

public class FlightSearchTest extends TestBase {

    static Stream<Arguments> headerShouldContainOptionsForSelectedLocale() {
        return Stream.of(
                Arguments.of("Polski", "pl/", List.of("Punkty Bonusowe", "Wsparcie 24/7", "Moje zamówienie")),
                Arguments.of("Deutsch", "de/", List.of("Bonuspunkte", "24/7 Kundenservice", "Meine Buchung"))
        );
    }

    @MethodSource()
    @ParameterizedTest(name = "Для локали {0} должен отображаться список опций {2}")
    void headerShouldContainOptionsForSelectedLocale(String locale, String path, List<String> expectedOptions) {
        $("[data-locator=currentLocale]").click();
        $(byText(locale)).click();
        webdriver().shouldHave(currentFrameUrl(baseUrl + path));
        $$("._3DD68").shouldHave(CollectionCondition.texts(expectedOptions));
    }
}
