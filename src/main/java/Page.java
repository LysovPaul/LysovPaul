import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
public class Page {
    /**
     * конструктор класса, занимающийся инициализацией полей класса
     */
    public WebDriver driver;
    public Page(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver; }
    /**
     * локатор первой страницы
     */
    @FindBy(xpath = "//*[contains(text(),'Next')]")
    private WebElement linkBtn;
    public WebElement getLinkBtn() {
        return linkBtn; } }