package io.huvz.domain

import io.huvz.client.GitWebDriver
import io.huvz.config.MyAppConfig
import io.vertx.core.http.impl.HttpClientConnection.log
import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.runBlocking
import org.openqa.selenium.*
import org.openqa.selenium.remote.RemoteWebDriver
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait
import java.awt.Color
import java.awt.image.BufferedImage
import java.io.ByteArrayOutputStream
import java.time.Duration
import javax.imageio.ImageIO


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
            val s = webDriver.findElement(By.className("detail1"))
            val base64 = s.getScreenshotAs(OutputType.BYTES)
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
    fun jhcToClass(name:String): ByteArray? {
        var webDriver: RemoteWebDriver? = null
        return try {
            webDriver = GitWebDriver().getWebDriver()
            val JhcUrl = "https://webvpn.jhc.cn/"
            //log.info("http://43.142.135.84:9085/v2api/view/gitee?name=${name}");
            webDriver.get(JhcUrl)
            // 找到用户名输入框，并填写用户名
            val usernameInput: WebElement = webDriver.findElement(By.id("user_name"))
            usernameInput.sendKeys(MyAppConfig.username)
            // 找到密码输入框，并填写密码
            val passwordInput: WebElement = webDriver.findElement(By.name("password"))
            passwordInput.sendKeys(MyAppConfig.password)

            val submitButton: WebElement = webDriver.findElement(By.id("login"))
            submitButton.click()

            val JHcjwglxt = "https://webvpn.jhc.cn//https/77726476706e69737468656265737421fae046903f24265a760bc7af96/"
            //val s = webDriver.findElement(By.className("detail1"))
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

    suspend fun nkToImg(name: String): ByteArray? = runBlocking {
        var webDriver: RemoteWebDriver? = null
        return@runBlocking try {
            webDriver = GitWebDriver().getWebDriver()
            var base64: ByteArray? = null
                val windowSize = Dimension(1200, 900)
                val windowPosition = Point(500, 100)
                webDriver.manage().window().position = windowPosition
                webDriver.manage().window().size = windowSize
            val waitDuration = Duration.ofSeconds(10) // 等待时间为 10 秒
            val nowcodeapi = "https://www.nowcoder.com/search/user?query=$name&type=user&searchType=%E6%90%9C%E7%B4%A2%E9%A1%B5%E8%BE%93%E5%85%A5%E6%A1%86&subType=0"
            val wait = WebDriverWait(webDriver,waitDuration ) // 设置最大等待时间为10秒
            log.info(nowcodeapi)
            webDriver.get(nowcodeapi)
            val divElement: WebElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("user-card-list")))
            log.info("has found $divElement")

            val anchorElements = divElement.findElements(By.tagName("a"))
            val anchorCount: Int = anchorElements.size
            if (anchorCount == 1) {
                val url: String = anchorElements[0].getAttribute("href")
                log.info("提取到的 URL：$url")
                val modifiedUrl = url.replace("https://www.nowcoder.com/users/", "")
                val getUrl = "https://ac.nowcoder.com/acm/contest/profile/$modifiedUrl"
                log.info("请求的 URL：$getUrl")

                webDriver.get(getUrl)
                val profileInfoWrapper: WebElement =  wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("profile-info-wrapper")))
                val myStateMain: WebElement =  wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("my-state-main")))
                val ratingState: WebElement =  wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("rating-state")))

                val elements = listOf(myStateMain, ratingState)

                val targetWidth = 1200
                val targetHeight = 750
                val combinedImage = BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_ARGB)

                val g = combinedImage.createGraphics()
                g.background = Color.WHITE
                g.clearRect(0, 0, targetWidth, targetHeight)
                var currentY = 0

                val screenshots = elements.map { element ->
                    async(Dispatchers.IO) {
                        ImageIO.read(element.getScreenshotAs(OutputType.FILE))
                    }
                }
                val profileInfo  = ImageIO.read(profileInfoWrapper.getScreenshotAs(OutputType.FILE))

                val images = screenshots.awaitAll()
                g.drawImage(profileInfo, 0, currentY, targetWidth, profileInfo.height, null)
                currentY += profileInfo.height + 10
                for (image in images) {
                    val offsetX = ((targetWidth - image.width) ?: 0) / 2
                    g.drawImage(image, offsetX, currentY, 1000, image.height, null)
                    currentY += image.height + 10
                }
                g.dispose()

                val outputStream = ByteArrayOutputStream()
                ImageIO.write(combinedImage, "png", outputStream)
                base64 = outputStream.toByteArray()
            } else {
                base64 = webDriver.getScreenshotAs(OutputType.BYTES)
            }

            webDriver.close()
            webDriver.quit()
            base64
        } catch (e: Exception) {
            webDriver?.close()
            webDriver?.quit()
            log.error("erro has in : $e")
           null
        }
    }

}