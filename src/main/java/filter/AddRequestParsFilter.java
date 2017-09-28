package filter;

import com.peramdy.common.AddParametersForHeader;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Created by peramdy on 2017/9/23.
 */
public class AddRequestParsFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        System.out.println("Auth:"+  req.getHeader("X-Auth-Sense"));
        System.out.println("Timestamp:"+req.getHeader("Timestamp"));
        AddParametersForHeader requestAddPars=new AddParametersForHeader(req);
        requestAddPars.putHeader("X-Auth-Sense","d6f2d37c2f518ce5b2d2d299b3542e82");
        requestAddPars.putHeader("Timestamp","1506147081");
        chain.doFilter(requestAddPars, response);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void destroy() {

    }
}
