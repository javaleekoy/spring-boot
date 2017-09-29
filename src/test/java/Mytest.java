import com.peramdy.utils.ResponseSimpleUtil;
import com.peramdy.utils.ResponseUtil;
import com.peramdy.utils.TokenUtils;
import org.junit.Test;

import java.util.Date;

/**
 * Created by peramdy on 2017/9/21.
 */
public class Mytest {


    @Test
    public void testOne() {
        ResponseUtil<?> responseUtil = new ResponseUtil<Object>();
        ResponseSimpleUtil simpleUtil = new ResponseSimpleUtil();
        simpleUtil.setMessage("ss");
        simpleUtil.setStatus(11);
        System.out.println(simpleUtil.toString());
        System.out.println(responseUtil.toString());
    }

    @Test
    public void testTwo() {
        String token = TokenUtils.createLoginToken(1, "123456", new Date());
        System.out.println(token);
    }

    @Test
    public void testThree() {
        String token = TokenUtils.createUUID();
        System.out.println(token);
    }

}
