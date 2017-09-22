import com.peramdy.utils.ResponseSimpleUtil;
import com.peramdy.utils.ResponseUtil;
import org.junit.Test;

/**
 * Created by peramdy on 2017/9/21.
 */
public class Mytest {


    @Test
    public void testOne(){

        ResponseUtil<?> responseUtil=new ResponseUtil<Object>();

        ResponseSimpleUtil simpleUtil=new ResponseSimpleUtil();

        simpleUtil.setMessage("ss");
        simpleUtil.setStatus(11);

        System.out.println(simpleUtil.toString());
        System.out.println(responseUtil.toString());


    }

}
