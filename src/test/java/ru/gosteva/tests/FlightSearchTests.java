package ru.gosteva.tests;

import com.codeborne.selenide.CollectionCondition;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;
import java.util.stream.Stream;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Configuration.baseUrl;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.WebDriverConditions.currentFrameUrl;
import static ru.gosteva.tests.DynamicTestData.tomorrow;

public class FlightSearchTests extends TestBase {

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

    @ValueSource(strings = {
            "Эконом", "Бизнес", "Премиум", "Первый"
    })
    @ParameterizedTest(name = "Выбор класса перелета {0} для поиска рейса из Москвы в Батуми на завтра")
    void selectFlightClassSearchingFlightFromMoscowToBatumiForTomorrow(String flightClass) {
        $("[title=Откуда]").setValue("Москва");
        $("[data-locator=dropdown-overlay]").findElement(byText("Москва")).click();
        $("[title=Куда]").setValue("Батуми");
        $("[data-locator=dropdown-overlay]").findElement(byText("Батуми")).click();
        $("[data-locator=Datepicker]").hover();
        $("[data-locator='" + tomorrow + "']").click();
        $("[name=travellers-trigger]").click();
        $("[data-test=service-classes]").findElement(byText(flightClass)).click();
        $("button[type='submit']").click();
        $("#searchPage").shouldBe(visible);
    }

    @CsvSource(value = {
            "Москва, Бангкок",
            "Ереван, Нью-Йорк",
            "Прага,  Непалгандж"
    })
    @ParameterizedTest(name = "Выбор города отправления {0} и города назначения {1} для поиска рейса на завтра")
    void selectDepartureCityAndDestinationCitySearchingFlightForTomorrow(String departureCity, String destinationCity) {
        $("[title=Откуда]").setValue(departureCity);
        $("[data-locator=dropdown-overlay]").findElement(byText(departureCity)).click();
        $("[title=Куда]").setValue(destinationCity);
        $("[data-locator=dropdown-overlay]").findElement(byText(destinationCity)).click();
        $("[data-locator=Datepicker]").hover();
        $("[data-locator='" + tomorrow + "']").click();
        $("button[type='submit']").click();
        $("#searchPage").shouldBe(visible);
    }
}