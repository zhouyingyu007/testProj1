import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;








import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

//import edu.heu.domain.News;
import org.json.JSONArray;
import org.json.JSONObject;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.ConsolePipeline;
import us.codecraft.webmagic.pipeline.Pipeline;
import us.codecraft.webmagic.processor.PageProcessor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
/**
 * @ClassName: SougoAppSpider
 * @author ZYY
 * @date 2019年10月15日 下午1:43:48
 */

public class SougoAppSpider implements PageProcessor {

    // 抓取网站的相关配置，包括编码、抓取间隔、重试次数等
    private Site site = Site.me().setRetryTimes(3).setSleepTime(100);

    public Site getSite() {
        return site;
    }

    @Override
    public void process(Page page) {
        // 文章页，匹配 https://zhushou.sogou.com/appa/detail/任意位数数字.html
        if (page.getUrl().regex("http://zhushou\\.sogou\\.com/apps/detail/[0-9]+\\.html").match()) {
            page.putField("AppName", page.getHtml().xpath("/html/body/div[2]/div/div[2]/div[1]/p[1]/text()").toString());
            page.putField("DownloadTimes", page.getHtml().xpath("/html/body/div[2]/div/div[2]/div[1]/p[3]/span[1]/text()").toString());
            page.putField("AppSize", page.getHtml().xpath("/html/body/div[2]/div/div[2]/div[2]/table/tbody/tr[1]/td[1]/text()").toString());
            page.putField("AppGrade", page.getHtml().xpath("/html/body/div[2]/div/div[2]/div[1]/p[2]/@class").toString());
            page.putField("AppVersion", page.getHtml().xpath("html/body/div[2]/div/div[2]/div[2]/table/tbody/tr[1]/td[2]/text()").toString());
            page.putField("AppLanguage", page.getHtml().xpath("html/body/div[2]/div/div[2]/div[2]/table/tbody/tr[1]/td[3]/text()").toString());
            page.putField("AppPlatform", page.getHtml().xpath("html/body/div[2]/div/div[2]/div[2]/table/tbody/tr[1]/td[4]/text()").toString());
            page.putField("UpdateTime", page.getHtml().xpath("/html/body/div[2]/div/div[2]/div[2]/table/tbody/tr[2]/td[1]/text()").toString());
            page.putField("AppAuthor", page.getHtml().xpath("/html/body/div[2]/div/div[2]/div[2]/table/tbody/tr[2]/td[2]/text()").toString());
            page.putField("AppType", page.getHtml().xpath("/html/body/div[2]/div/div[2]/div[2]/table/tbody/tr[3]/td[1]/a/text()").toString());
            page.putField("AppIntroduce", page.getHtml().xpath("/html/body/div[2]/div/div[2]/div[2]/div[4]/div/div/text()").toString());
            //获取图片并转化为base64编码
            String image_url = page.getHtml().xpath("/html/body/div[2]/div/div[2]/div[1]/img/@src").toString();
            System.out.println(image_url);



//            page.putField("Content",
//                    page.getHtml().xpath("/html/body/div[4]/div[1]/div[2]/div/div[2]/p/text()").all().toString());
        }
//        //  请求页
//        else if(page.getUrl().regex("http://zhushou\\.sogou\\.com/apps/list/0-0\\.html\\?act=getapp\\&page=[0-9]+").match()){
//            System.out.println("haha");
//
//        }
        // 列表页
        else {
            // 文章url
            page.addTargetRequests(
                    page.getHtml().xpath("/html/body/div[2]/div/ul/li/a[1]/@href").all());

            // 翻页url（静态） 或加载更多请求页（动态）
            java.util.List<String> nexturl =new ArrayList<>();
            String request = new String();
            for(int j=1;j<10;j++){ //j为请求页编号
                request="http://zhushou.sogou.com/apps/list/0-0.html?act=getapp&page="+(j+1);//get请求的url
                System.out.println(request);

                String doGet = HttpUtils.doGet(request); //模拟get请求，获取响应json数据
                String jsonData =doGet;
                String value = null;
                try{
                    JSONObject jsonObject = new JSONObject(jsonData);
                    JSONArray jsonArray = jsonObject.getJSONArray("data");//从json对象中提取json数组(有时候json对象的数据是个json数组)
                    for(int i=0;i<jsonArray .length();i++){
                        //遍历所有JSON数组元素
                        JSONObject jsonObject1 = jsonArray .getJSONObject(i);
                        value= jsonObject1.getString("url");//提取url键对应的值
                        nexturl.add(value);
                    }
                }
                catch (Exception e){
                    e.printStackTrace();
                }


            }
            page.addTargetRequests(nexturl);

        }
    }





    public static void main(String[] args) {
        String Starturl = "http://zhushou.sogou.com/apps/list/0-0.html?act=getapp&page=2";
        String Starturl1 = "http://zhushou.sogou.com/apps";

        Spider.create(new SougoAppSpider()).addUrl(Starturl1).addPipeline(new MysqlPipeline())
                .thread(3).run();



//        try {
//            Document doc = Jsoup.connect(Starturl).userAgent("bbb").timeout(120000).ignoreContentType(true).get();
//            System.out.println(doc);
//
//            //获取html页面的所有链接
////            Elements links = doc.select("a[href]");
////            for (Element link : links)
////            {
////                System.out.println("link : " + link.attr("href"));
////                System.out.println("text : " + link.text());
////            }
//
//            //获取body元素
//            Elements links = doc.select("body");
//            for (Element link : links)
//            {
//                System.out.println("applist : " + link.getAllElements().attr("data"));
//
//            }
//
//        } catch (IOException e) {
//            System.out.println("发送GET请求出现异常！");
//        }


    }


}