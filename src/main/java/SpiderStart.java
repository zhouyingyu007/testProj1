
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.ConsolePipeline;
import us.codecraft.webmagic.pipeline.Pipeline;
import us.codecraft.webmagic.processor.PageProcessor;


public class SpiderStart {
    private static PageProcessor pageProcessor;//处理解析页面组件
    private static Pipeline pipeline;//数据处理，持久化组件

    public SpiderStart(PageProcessor pageProcessor,Pipeline pipeline){
        this.pageProcessor = pageProcessor;
        this.pipeline = pipeline;
    }

    public PageProcessor setPageProcessor(PageProcessor pageProcessor) {
        return pageProcessor;
    }

    public Pipeline setPipeline(Pipeline pipeline) {
        return pipeline;
    }

    public static void main(String[] args) {
        String Starturl = "http://zhushou.sogou.com/apps/list/0-0.html?act=getapp&page=2";
        String Starturl1 = "http://zhushou.sogou.com/apps";

        Spider.create(pageProcessor).addUrl(Starturl1).addPipeline(pipeline)
                .thread(3).run();
    }
}
