import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import java.util.concurrent.TimeUnit;

/**
 * @author qyyzxty@icloud.com
 * 2021/4/7
 **/
public class HelloWorld {
    public static void main(String[] args) throws Exception {
        //设置驱动
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\qyyzx\\Desktop\\chromedriver\\chromedriver.exe");
        //创建ChromeDriver对象
        ChromeDriver driver = new ChromeDriver();
        //创建窗口最大化
        driver.manage().window().maximize();
        //打开网页
        driver.get("https://www.baidu.com");
        //用ID定位输入框
        WebElement id = driver.findElement(By.id("kw"));
        //输入Python
        id.sendKeys("Java 网络爬虫");
        //用ID定位
        WebElement ById = driver.findElement(By.id("su"));
        //点击百度一下
        ById.click();
        //给出一定的响应时间
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        //使用Xpath解析数据
        int i;
        for (i = 2; i < 100; i++) {
            System.out.println(i);
            WebElement webElement = driver.findElementById(String.valueOf(i));
            WebElement a = webElement.findElement(By.tagName("a"));
            System.out.println("标题：" + a.getText());
            System.out.println("链接：" + a.getAttribute("href"));
            System.out.println("+++++++++++++++++++++++++++++++");
        }
        driver.quit();
    }
}
