import us.codecraft.webmagic.pipeline.Pipeline;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import java.util.Map;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;



class MysqlPipeline implements Pipeline {
    private ApplicationContext context;
    private AppMapper mapper;

    @Override
    public void process(ResultItems resultItems, Task task) {
        Map<String, Object> mapResults = resultItems.getAll();


        // 输出到控制台
        System.out.println("get page: " + resultItems.getRequest().getUrl());
        for (Map.Entry<String, Object> entry : resultItems.getAll().entrySet()) {
            System.out.println(entry.getKey() + ":\t" + entry.getValue());

        }
        // 持久化
        App app = new App();
        if (!mapResults.get("AppName").equals("")) {
            app.setAppName((String) mapResults.get("AppName"));
            app.setDownloadTimes((String) mapResults.get("DownloadTimes"));
            app.setAppSize((String) mapResults.get("AppSize"));
            app.setAppGrade((String) mapResults.get("AppGrade"));
            app.setAppVersion((String) mapResults.get("AppVersion"));
            app.setAppLanguage((String) mapResults.get("AppLanguage"));
            app.setAppPlatform((String) mapResults.get("AppPlatform"));
            app.setUpdateTime((String) mapResults.get("UpdateTime"));
            app.setAppAuthor((String) mapResults.get("AppAuthor"));
            app.setAppType((String) mapResults.get("AppType"));
            app.setAppIntroduce((String) mapResults.get("AppIntroduce"));

        }

        context = new ClassPathXmlApplicationContext("spring-config.xml");
        mapper = (AppMapper)context.getBean("appMapper");

        System.out.println("--- start ---" +app.getAppName());
        try {
            System.out.println(app.getAppName());
            int result = mapper.insert(app);
            System.out.println(result);
        }catch (Exception e){
            e.printStackTrace();
        }

        System.out.println("--- end -- " + app.getAppName());


//        try {
//            InputStream is = Resources.getResourceAsStream("conf.xml");
//            SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(is);
//            SqlSession session = sessionFactory.openSession();
//            session.insert("add", news);
//            session.commit();
//            session.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }
}