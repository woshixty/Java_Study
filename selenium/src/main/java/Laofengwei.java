import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author qyyzxty@icloud.com
 * @data 2021/5/2
 **/
public class Laofengwei {
    public static void main(String[] args) {
        //设置驱动
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\qyyzx\\Desktop\\chromedriver\\chromedriver.exe");
        //创建ChromeDriver对象
        ChromeDriver driver = new ChromeDriver();
        //创建窗口最大化
        driver.manage().window().maximize();
        //打开网页
        driver.get("http://www.laofengwei.com/search?text=%E5%8C%97%E4%BA%AC");
        //睡眠，给出一定的响应时间
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        //循环遍历
        for (int i = 0; i < 50; i++) {
            List<WebElement> webElements = driver.findElements(By.xpath("//div[@id='food']//article[@class='padding-5 cursor-default']"));
            int j = 1;
            for (WebElement element : webElements) {
                System.out.println(j++);
                //美食名称
                WebElement foodName = element.findElement(By.xpath("./div[2]/a/header"));
                System.out.println("美食名称：" + foodName.getText().trim());
                //美食简介
                WebElement intro = element.findElement(By.xpath("./div[2]/p"));
                System.out.println("美食名称：" + intro.getText().trim());
                //美食详情链接
                WebElement a = element.findElement(By.xpath("./div[1]/a"));
                System.out.println("美食详情链接：" + a.getAttribute("href"));
                //美食图片
                WebElement img = a.findElement(By.tagName("img"));
                //分割线
                System.out.println("美食图片链接：" + img.getAttribute("src"));
                System.out.println("++++++++++++++++++++++++++++++++++++++++");
                System.out.println("++++++++++++++++++++++++++++++++++++++++");
            }
            //用ID定位
            WebElement HuanYiHuan = driver.findElement(By.id("food-spin"));
            //点击换一换
            driver.findElement(By.id("food-spin")).click();
            //睡眠，给出一定的响应时间
            //打开网页
//            driver.get("http://www.laofengwei.com/f/104/html");
            driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        }
    }
}
