import lombok.Builder;
import lombok.Data;

@Data

public class App{
    private int id;
    private String appName;
    private String downloadTimes;
    private String appSize;
    private String appGrade;
    private String appVersion;
    private String appLanguage;
    private String appPlatform;
    private String updateTime;
    private String appAuthor;
    private String appType;
    private String appIntroduce;

    public App(){

    }

}
