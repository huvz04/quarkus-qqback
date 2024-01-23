package io.huvz.service.impl

import io.huvz.client.GitWebDriver
import io.huvz.service.IApiService
import io.vertx.core.http.impl.HttpClientConnection
import io.vertx.core.http.impl.HttpClientConnection.log
import jakarta.enterprise.context.ApplicationScoped
import org.openqa.selenium.*
import org.openqa.selenium.remote.RemoteWebDriver
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait
import java.time.Duration

@ApplicationScoped
class GithubSerivce : IApiService{

    /**
     * 获取github主页
     */
    fun getimg(name:String): ByteArray? {
        var webDriver: WebDriver? = null
        try{
            webDriver = GitWebDriver().getDriver()
            // 设置窗口大小
            val windowSize = Dimension(1500, 2000)
            webDriver.manage().window().size = windowSize
            // 创建一个 Cookie 对象
            //val url = "https://github.com/$name"
            val url = "https://github.com/$name"
            val nurl = "https://hub.nuaa.cf/$name"

            try {
                webDriver.get(url)
                // 设置超时时间为10秒
                val waitDuration = Duration.ofSeconds(10) // 等待时间为 10 秒
                val wait = WebDriverWait(webDriver, waitDuration)
                // 等待元素加载完成
                wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("main-content")))
                println("网页加载成功")
            } catch (e: Exception) {
                println("网页加载超时，切换API")
                webDriver.get(nurl)
            }
            var element:  WebElement? = null;
            element = try {
                webDriver.findElement(By.className("application-main"))
            }catch (e: NoSuchElementException) {
                //如果找不到结果就抓404的
                webDriver.findElement(By.className("font-mktg"))
            }catch (e:Exception){
                webDriver.findElement(By.tagName("html"))
            }
            val base64 = element?.getScreenshotAs(OutputType.BYTES)
            webDriver.close();
            webDriver.quit()
            return base64;

        }catch (e:Exception){
            webDriver?.close();
            webDriver?.quit()
            HttpClientConnection.log.error("erro in ${e}")
            return null;
        }
    }



}