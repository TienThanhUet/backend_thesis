package com.stadio.restapi.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

import javax.servlet.*;
import java.io.IOException;

@Configuration
public class WebFilter implements Filter {

    private static final Logger logger = LoggerFactory.getLogger(WebFilter.class);

    private static final boolean CONDITION = false;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        logger.debug("Initiating WebFilter >> ");

    }

    @Override
    public void doFilter(ServletRequest requestX, ServletResponse response, FilterChain chain) throws IOException,
            ServletException {

        chain.doFilter(requestX,response);
        return;


        //TODO: Uncoment when production
//        HttpServletRequest request = (HttpServletRequest) requestX;
//        String contextPath = request.getRequestURI().substring(request.getContextPath().length());
//        Manager manager = managerService.getCurrentManager(requestHandler.getToken());
//
//        request.getMethod();
//        //TODO : hash router for more speed
//        for (MDMenu router: this.mdMenuRouters) {
//            if (contextPath.equals(router.getRouter()) && request.getMethod().equals(router.getMethod())
//                    && router.isUserCanAccess(manager)) {
//                chain.doFilter(request,response);
//                return;
//            }
//        }
//        //Handle Exception
//        ((HttpServletResponse) response).sendError(HttpServletResponse.SC_NOT_ACCEPTABLE, messageService.getMessage("error.permissionDeny"));
    }

    @Override
    public void destroy() {
        logger.debug("Destroying WebFilter >> ");
    }
}
