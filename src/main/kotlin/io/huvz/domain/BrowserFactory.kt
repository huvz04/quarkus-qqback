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
                var element:  WebElement? = null;
                element = try {
                         webDriver.findElement(By.id("user-show-detail"))
                    }catch (e:NoSuchElementException) {
                        //如果找不到结果就抓404的
                        webDriver.findElement(By.className("content"))
                    }catch (e:Exception){
                        webDriver.findElement(By.className("ui_basic"))
                    }
                val base64 = element?.getScreenshotAs(OutputType.BYTES)
                webDriver.close();
                webDriver.quit()
                return base64;

        }catch (e:Exception){
            webDriver?.close();
            webDriver?.quit()
            log.error("erro in ${e}")
            return null;
        }
    }


    fun htmlToImg(name:String): ByteArray? {
        var webDriver: RemoteWebDriver? = null
        return try {
            webDriver = GitWebDriver().getWebDriver()
            log.info("http://43.142.135.84:9085/v2api/view/gitee?name=${name}");
            webDriver.get("http://43.142.135.84:9085/v2api/view/gitee?name=${name}")
            webDriver.findElement(By.id("detail"))
            val base64 = webDriver.getScreenshotAs(OutputType.BYTES)
            webDriver.close();
            webDriver.quit()
            base64
        }catch (e:Exception){
            webDriver?.close();
            webDriver?.quit()
            log.error("erro in ${e}")
            null;
        }


    }

}