package io.huvz.service.impl

import com.google.gson.Gson
import io.huvz.client.GitWebDriver
import io.huvz.client.nowTime
import io.huvz.config.MyAppConfig
import io.huvz.domain.vo.ClassInfo
import io.huvz.domain.vo.JsonResult
import io.vertx.core.http.impl.HttpClientConnection.log
import jakarta.enterprise.context.ApplicationScoped
import kotlinx.coroutines.runBlocking
import okhttp3.*
import okhttp3.Cookie
import org.openqa.selenium.*
import org.openqa.selenium.interactions.Actions
import org.openqa.selenium.remote.RemoteWebDriver
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.io.Reader
import java.time.Duration


@ApplicationScoped
class JhcService {

    private final var webcookie = Cookiejar()


    suspend fun login(): OkHttpClient?  = runBlocking {
        var webDriver: WebDriver? = null
        return@runBlocking try {
            webDriver = GitWebDriver().getDriver()
            val JhcUrl = "https://webvpn.jhc.cn/login"
            log.info(JhcUrl)
            webDriver.get(JhcUrl)
            // 找到用户名输入框，并填写用户名

                val waitDuration = Duration.ofSeconds(10)
                val wait = WebDriverWait(webDriver,waitDuration ) // 设置最大等待时间为10秒

                 val usernameInput: WebElement = webDriver.findElement(By.id("user_name"));
                log.info(usernameInput)

            // 创建Actions对象
            val actions = Actions(webDriver)
            println(MyAppConfig.username)
                // 使用sendKeys方法点击输入框
                actions.moveToElement(usernameInput).click()
                actions.sendKeys(usernameInput,MyAppConfig.username).perform();
                actions.sendKeys(Keys.ENTER)
                // 找到密码输入框，并填写密码
                val passwordInput: WebElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("password")))
                passwordInput.sendKeys(MyAppConfig.password)

                val submitButton: WebElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("login")))
                //submitButton.submit()
                submitButton.click()
                log.info(submitButton)

            // 等待0.5秒
            try {
                Thread.sleep(500)
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
                val JHcjwglxt = "https://webvpn.jhc.cn/https/77726476706e69737468656265737421fae046903f24265a760bc7af96/"
                log.info(JHcjwglxt)
                webDriver.get(JHcjwglxt)

            // 等待0.5秒
            try {
                Thread.sleep(300)
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
            // 再登录一遍
                // 找到密码输入框，并填写密码
                val usernameInput2: WebElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("username")))
                usernameInput2.sendKeys(MyAppConfig.username)
                // 找到密码输入框，并填写密码
                val passwordInput2: WebElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("ppassword")))
                passwordInput2.sendKeys(MyAppConfig.password)

            val submitButton2: WebElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("dl")))
            //submitButton.submit()
            submitButton2.click()


            log.info(submitButton)

            // 等待0.6秒
            try {
                Thread.sleep(600)
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
            // 提取 Selenium 的 Cookie
            val seleniumCookies: Set<org.openqa.selenium.Cookie> = webDriver.manage().getCookies()

            // 将 Selenium 的 Cookie 转换为 OkHttp 的 Cookie 对象
            val okhttpCookies: MutableList<Cookie> = ArrayList()
            for (seleniumCookie in seleniumCookies) {
                val builder = Cookie.Builder()
                    .name(seleniumCookie.name)
                    .value(seleniumCookie.value)
                    .domain(seleniumCookie.domain)
                    .path(seleniumCookie.path)
                okhttpCookies.add(builder.build())
            }
            // 将转换后的 OkHttp 的 Cookie 添加到 CookieJar 中
            HttpUrl.parse("webvpn.jhc.cn")?.let { webcookie.saveFromResponse(it, okhttpCookies) };
            webDriver.close();
            webDriver.quit();
            OkHttpClient.Builder()
                .cookieJar(webcookie)
                .followRedirects(true)
                .build()
        }catch (e:Exception){
            webDriver?.close();
            webDriver?.quit()
            log.error("erro in ${e}")
            null
        }


    }


    suspend fun jhcToClass(name:String):ByteArray? {
        val client = login();

        //学年
        val xnm = nowTime.getcurrentYear().toString()
        //获取学期号
        val xqm = nowTime.getSemesterNumber().toString()
        //获取本周
        val zs = nowTime.getcurrentWeek().toString()

        val bj  = getnjid(name);

        var result : String? = null;
        val formBody = FormBody.Builder()
            .add("zs",zs)
            .add("xnm", xnm)
            .add("xqm", xqm)
            .add("njdm_id", bj.nj_id)
            .add("zyh_id", bj.zyh_id)
            .add("bh_id", bj.bh_id)
            .add("tjkbzdm", "1")
            .add("tjkbzxsdm", "0")
            .build()

        val request = Request.Builder()
            .url("https://webvpn.jhc.cn/https/77726476706e69737468656265737421fae046903f24265a760bc7af96/jwglxt/kbdy/bjkbdy_cxBjKb.html?vpn-12-o2-jwglxt.jhc.cn&gnmkdm=N214505")
            .post(formBody)
            .build()
        client!!.newCall(request).execute().use { response ->
            // 处理响应数据
            val responseBody = response.body()!!.string()
            println(responseBody)
        }
        return null;
    }

    /**
     * 转化班级名字
     */
    fun extractFirstTwoDigits(input: String): String? {
        val regex = Regex("\\d+")
        val matchResult = regex.find(input)
        return matchResult?.value?.take(2)
    }
    fun getClassList():JsonResult{
        val gson = Gson()
        val inputStream = javaClass.getClassLoader().getResourceAsStream("classList.json")
        val reader: Reader = InputStreamReader(inputStream as InputStream)
        return gson.fromJson(reader, JsonResult::class.java)
    }
    /**
     * 获取班级对象
     */
    fun getnjid(input: String):ClassInfo{

        val jsonResult = getClassList()
        if(input.length<=3) {
           return jsonResult.classes.get(0);
        }
        val clas2 = input.substring(0,2);
        var item :ClassInfo = jsonResult.classes.get(0);
        for( i  in jsonResult.classes)
        {
            if(input.contains(i.bj))   item= i;
        }
        item.nj_id = "20"+clas2;
        return item
    }
}
class Cookiejar : CookieJar {
    private val cookieStore = mutableMapOf<String, MutableList<Cookie>>()

    override fun saveFromResponse(url: HttpUrl, cookies: List<Cookie>) {
        cookieStore[url.host()]?.addAll(cookies) ?: cookieStore.put(url.host(), cookies.toMutableList())
    }

    override fun loadForRequest(url: HttpUrl): List<Cookie> {
        //printCookies()
        return cookieStore[url.host()] ?: emptyList()
    }

    fun printCookies() {
        for ((host, cookies) in cookieStore) {
            println("Host: $host")
            for (cookie in cookies) {
                println("Cookie: ${cookie.name()}=${cookie.value()}")
            }
        }
    }
}