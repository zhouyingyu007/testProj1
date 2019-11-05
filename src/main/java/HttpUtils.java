import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;

import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class HttpUtils {

    //模拟get请求接口返回json数据格式
    public static String doGet(String url){
        String result="";
        //获取httpclient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        //获取get请求对象
        HttpGet httpGet = new HttpGet(url);
        try {
            //发起请求
            HttpResponse response = httpClient.execute(httpGet);
            //获取响应中的数据
            HttpEntity entity = response.getEntity();
            //把entity转换成字符串
            result = EntityUtils.toString(entity, "utf-8");
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return result;
    }

    //post请求

    /**
     * @param
     *
     * /**
     * ①：创建一个HttpCient对象 client
     * ②：创建一个HttpGet对象
     * ③：使用client发起一个get请求
     * ④：获取HttpEntity响应
     * ⑤：解析这个响应对象
     * @throws Exception
     */
    public static String doPost(String url,String param){
        String result="";
        //获取httpclient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        //获取请求对象
        HttpPost httpPost = new HttpPost(url);
        try {
            //把传入进来的结构树封装
            httpPost.setEntity(new StringEntity(param,"utf-8"));
            //执行一个post请求
            HttpResponse response = httpClient.execute(httpPost);
            //从响应获取数据
            HttpEntity entity = response.getEntity();
            //将httpentity转换为string
            result = EntityUtils.toString(entity, "utf-8");
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return result;
    }
    //测试
    public static void main(String[] args) {
        String url="http://zhushou.sogou.com/apps/list/0-0.html?act=getapp&page=2";
        String doGet = doGet(url);
        System.out.println(doGet);

        String jsonData =doGet;
        String value = null;
        List<String> nexturl = new ArrayList<>();
        try{
            JSONObject jsonObject = new JSONObject(jsonData);
            JSONArray jsonArray = jsonObject.getJSONArray("data");//从json对象中提取json数组(有时候json对象的数据是个json数组)
            for(int i=0;i<jsonArray .length();i++){
                //遍历所有JSON数组元素
                JSONObject jsonObject1 = jsonArray .getJSONObject(i);
                value= jsonObject1.getString("url");
                System.out.println(value);
                nexturl.add(value);
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        System.out.println("Hello");
        System.out.println(nexturl);
        System.out.println("Hello");

    }
}
