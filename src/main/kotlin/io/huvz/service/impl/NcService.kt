package io.huvz.service.impl

import io.huvz.client.GitWebDriver
import io.huvz.service.IApiService
import io.vertx.core.http.impl.HttpClientConnection
import jakarta.enterprise.context.ApplicationScoped
import kotlinx.coroutines.*
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
class NcService:IApiService {

    /**
     * 牛客查询图片
     */
    suspend fun nkToImg(name: String): ByteArray? = runBlocking {
        var webDriver: WebDriver? = null
        return@runBlocking try {
            webDriver = GitWebDriver().getDriver()
            var base64: ByteArray? = null
            val windowSize = Dimension(1200, 900)
            val windowPosition = Point(500, 100)
            webDriver.manage().window().position = windowPosition
            webDriver.manage().window().size = windowSize
            val waitDuration = Duration.ofSeconds(10) // 等待时间为 10 秒
            val nowcodeapi = "https://www.nowcoder.com/search/user?query=$name&type=user&searchType=%E6%90%9C%E7%B4%A2%E9%A1%B5%E8%BE%93%E5%85%A5%E6%A1%86&subType=0"
            val wait = WebDriverWait(webDriver,waitDuration ) // 设置最大等待时间为10秒
            HttpClientConnection.log.info(nowcodeapi)
            webDriver.get(nowcodeapi)
            val divElement: WebElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("user-card-list")))
            HttpClientConnection.log.info("has found $divElement")

            val anchorElements = divElement.findElements(By.tagName("a"))
            val anchorCount: Int = anchorElements.size
            if (anchorCount == 1) {
                val url: String = anchorElements[0].getAttribute("href")
                HttpClientConnection.log.info("will to URL：$url")
                val modifiedUrl = url.replace("https://www.nowcoder.com/users/", "")
                val getUrl = "https://ac.nowcoder.com/acm/contest/profile/$modifiedUrl"
                HttpClientConnection.log.info("请求的 URL：$getUrl")

                webDriver.get(getUrl)
                val profileInfoWrapper: WebElement =  wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("profile-info-wrapper")))
                val myStateMain: WebElement =  wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("my-state-main")))
                val ratingState: WebElement =  wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("rating-state")))
                /**
                 * 最简单的办法是休眠1s 等加载
                 */
                delay(1000)
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
                val indexhtml: WebElement =  wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("nk-container")))
                base64 = indexhtml.getScreenshotAs(OutputType.BYTES)
            }

            webDriver.close()
            //webDriver.quit()
            base64
        } catch (e: Exception) {
            webDriver?.close()
            webDriver?.quit()
            HttpClientConnection.log.error("erro has in : $e")
            null
        }
    }
}