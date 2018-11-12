/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ifsul.fsi.web.controller.filter;

import br.com.ifsul.fsi.web.controller.LoginBean;
import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

public class LoginFilter implements Filter {

    final static Logger logger = Logger.getLogger(LoginFilter.class);

    public static final String LOGIN_PAGE = "/login.xhtml";
    public static final String ACCESS_DENIED = "/access-denied.xhtml";

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {

        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;

        // managed bean name is exactly the session attribute name
        LoginBean loginBean = (LoginBean) httpServletRequest.getSession().getAttribute("loginBean");

        if (loginBean != null) {
            if (loginBean.isLoggedIn()) {
                filterChain.doFilter(servletRequest, servletResponse);
            } else {
                logger.debug("Usuario nao esta logado " + loginBean.getUsername());
                httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + LOGIN_PAGE);
            }

        } else {
            logger.debug("Redirecionado para a tela de login devido nao ter sessao para iniciar.");
            // user is not logged in, redirect to login page
            httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + LOGIN_PAGE);
        }
    }

    @Override
    public void init(FilterConfig arg0) throws ServletException {
        logger.debug("LoginFilter initialized");
    }

    @Override
    public void destroy() {
        // close resources
    }
}
