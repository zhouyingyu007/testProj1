


public class AppService {


    private AppMapper mapper;

    public int insert(App app){
        return mapper.insert(app);
    }

}