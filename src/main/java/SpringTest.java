import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;



public class SpringTest {

    public static void main(String[] args) {
        //创建一个Spinrg的IOC容器对象
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring-config.xml");
        //获取IOC容器中的SpiderStartr对象
        SpiderStart s = (SpiderStart) applicationContext.getBean("spiderStart");
        //执行SpiderStart的main方法
        s.main(null);
    }
}
