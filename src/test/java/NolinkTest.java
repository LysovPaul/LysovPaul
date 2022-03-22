import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.junit.AfterClass;

import java.util.*;
import java.util.concurrent.TimeUnit;

public class NolinkTest {
    public static Page page;
    public static WebDriver driver;
    /**
     * осуществление первоначальной настройки
     */
    @BeforeClass
    public static void setup() {
        //определение пути до драйвера и его настройка
        System.setProperty("webdriver.chrome.driver", "chromedriver/chromedriver.exe");
        driver = new ChromeDriver();
        page = new Page(driver);
        //окно разворачивается на полный экран
        driver.manage().window().maximize();
        //задержка на выполнение теста = 10 сек.
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        //получение ссылки на страницу входа из файла настроек
        driver.get(AppProperties.getProperty("page"));
    }
    /**
     * метод получения номера старницы без видимой ссылки путем отбрасывания текста адреса, кроме номера страницы
     */
    public String getPageNum(String url) {
        int breaker = url.lastIndexOf('/');
        int point = url.lastIndexOf('.');
        return url.substring(breaker + 1, point);
    }
    /**
     * метод для поиска страниц без ссылок
     */
    @Test
    public void nolinkTest() throws Exception {
        //стек для перебора страниц
        Stack<String> toVisit = new Stack<>();
        //в качестве точки входа первая страница с ссылкой
        toVisit.push(page.getLinkBtn().getAttribute("href"));
        //множество для хранения страниц без ссылок
        Set<String> pagesNoLinks = new HashSet<String>();
        //Начинаем перебирать страницы, пока есть ссылки
        while (!toVisit.isEmpty()) {
            driver.navigate().to(toVisit.pop());
            //Если увидели последнюю, останавливаемся
            if (driver.findElement(By.tagName("body")).getText().contains("последняя страница"))
                break;
            List<WebElement> elems = driver.findElements(By.tagName("a"));
            //когда ссылки нет даже скрытой, собираем страницу из текущей, добавляя в номер +1, чтобы перейти к следующей
            if (elems.isEmpty()) {
                String url = driver.getCurrentUrl();
                int breaker = url.lastIndexOf('/');
                int point = url.lastIndexOf('.');
                String currPage = url.substring(breaker + 1, point);
                pagesNoLinks.add(currPage);
                String newUrl = String.format(
                        "%s%d%s",
                        url.substring(0, breaker + 1),
                        Integer.parseInt(currPage) + 1,
                        url.substring(point));
                toVisit.push(newUrl);
                continue;
            }
            elems.forEach((elem) -> {
                //тут добавляем страницы, где ссылка есть, но не отображается. Возможно такие страницы не нужно учитывать, но я не подумал об этом сразу и не успел уточнил
                if (!elem.isDisplayed())
                    pagesNoLinks.add(getPageNum(driver.getCurrentUrl()));
                toVisit.push(elem.getAttribute("href"));
            });
        }
        System.out.println("Номера страниц без ссылки: " + String.join(",", pagesNoLinks));
    }
    /**
     * закрытие окна браузера
     */
    @AfterClass
    public static void tearDown() {
        driver.quit();
    }
}