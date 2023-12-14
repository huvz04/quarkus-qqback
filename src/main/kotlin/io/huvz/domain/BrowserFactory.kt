package io.huvz.domain

import com.ruiyun.jvppeteer.core.Puppeteer
import com.ruiyun.jvppeteer.core.browser.Browser
import com.ruiyun.jvppeteer.core.browser.BrowserFetcher
import com.ruiyun.jvppeteer.core.page.Page
import com.ruiyun.jvppeteer.options.Clip
import com.ruiyun.jvppeteer.options.Device
import com.ruiyun.jvppeteer.options.LaunchOptionsBuilder
import com.ruiyun.jvppeteer.options.ScreenshotOptions
import com.ruiyun.jvppeteer.options.Viewport
import com.ruiyun.jvppeteer.protocol.network.Cookie
import com.ruiyun.jvppeteer.protocol.network.CookieParam
import jakarta.enterprise.context.ApplicationScoped
import java.io.IOException
import java.util.concurrent.ExecutionException

@ApplicationScoped
class BrowserFactory {

    fun getUrl4img(name:String): String? {
        BrowserFetcher.downloadIfNotExist(null)
        val argList = ArrayList<String>()


        val options = LaunchOptionsBuilder().withArgs(argList).withHeadless(true).build()
        val browser = Puppeteer.launch(options);
        try{
            argList.add("--no-sandbox")
            argList.add("--disable-setuid-sandbox")
            //argList.add("--window-size=1200,1448")
            val page: Page = browser.newPage()
            page.setUserAgent("Mozilla/5.0 (Windows NT 6.2; WOW64) AppleWebKit/537.36 (KHTML like Gecko) Chrome/46.0.2486.0 Safari/537.36 Edge/13.9200");
            val viewport = Viewport()
            viewport.width=1280;
            viewport.height=800;
            viewport.hasTouch
            page.setViewport(viewport)
            val cookies = ArrayList<CookieParam>();
            val cookie = CookieParam();cookie.domain="gitee.com";cookie.name ="slide_id";cookie.value = "10"; cookies.add(cookie);
            page.setCookie(cookies)
            println("https://gitee.com/$name")
            page.goTo("https://gitee.com/$name")
            val screenshotOptions = ScreenshotOptions()
            //设置截图范围
            //设置截图范围
            val clip = Clip(1.0,1.0,1280.0, 600.0)
            screenshotOptions.clip = clip
            //设置存放的路径
            //设置存放的路径
            //screenshotOptions.path = "test.png"
            //返回base64图片
            val base54 =page.screenshot(screenshotOptions)
            page.close();
            return base54;
        }finally {

            browser.close()
        }



    }


    fun htmlToImg(htmlCode:String):String{
        BrowserFetcher.downloadIfNotExist(null)
        val argList = ArrayList<String>()


        val options = LaunchOptionsBuilder().withArgs(argList).withHeadless(true).build()
        val browser = Puppeteer.launch(options);

        argList.add("--no-sandbox")
        argList.add("--disable-setuid-sandbox")
        //argList.add("--window-size=1200,1448")
        val page: Page = browser.newPage()
        page.setDefaultNavigationTimeout(2000)
        page.setUserAgent("Mozilla/5.0 (Windows NT 6.2; WOW64) AppleWebKit/537.36 (KHTML like Gecko) Chrome/46.0.2486.0 Safari/537.36 Edge/13.9200");
        page.setContent(htmlCode);
        page.waitFor("300")
        val screenshotOptions = ScreenshotOptions()
        //设置截图范围
        val clip = Clip(1.0,1.0,1280.0, 600.0)
        screenshotOptions.clip = clip
        val base54 = page.screenshot(screenshotOptions);
        page.close();
        browser.close()
        return base54;
    }
}