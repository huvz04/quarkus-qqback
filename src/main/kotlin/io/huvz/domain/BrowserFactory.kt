package io.huvz.domain

import io.huvz.client.GitWebDriver
import io.vertx.core.http.impl.HttpClientConnection.log
import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject
import org.openqa.selenium.*
import org.openqa.selenium.remote.RemoteWebDriver


@ApplicationScoped
class BrowserFactory {



    val WIDTH = 600;
    private val IMAGE_FORMAT = "png"


    fun getUrl4img(name:String): ByteArray? {
        var webDriver: RemoteWebDriver? = null
        try{
            webDriver = GitWebDriver().getWebDriver()
                // 设置窗口大小
                val windowSize = Dimension(1280, 800)
                webDriver.manage().window().size = windowSize
                // 创建一个 Cookie 对象
                webDriver.get("https://gitee.com/$name")
                val cookie = Cookie("slide_id", "10")
                // 设置 Cookie
                webDriver.manage().addCookie(cookie)
                webDriver.navigate().refresh()
                val element = webDriver.findElement(By.id("user-show-detail"))
                val base64 = element.getScreenshotAs(OutputType.BYTES)
                webDriver.close();
                webDriver.quit()
                return base64;

        }catch (e:Exception) {

            if (webDriver != null) {
                webDriver.close();
                webDriver.quit()
            }
            log.error("出现异常${e.message}")
        }
        return null
    }


    fun htmlToImg(name:String): ByteArray? {
        var webDriver: RemoteWebDriver? = null
        try {
            webDriver = GitWebDriver().getWebDriver()
            webDriver.get("http://127.0.0.1:9085/v2api/view/gitee?name=${name}")
            val base64 = (webDriver as TakesScreenshot).getScreenshotAs(OutputType.BYTES)
            webDriver.close();
            webDriver.quit()
            return base64
        }catch (e:Exception){
            if (webDriver != null) {
                webDriver.close();
                webDriver.quit()
            }
            log.error("erro in ${e}")
            return null;
        }


    }

}