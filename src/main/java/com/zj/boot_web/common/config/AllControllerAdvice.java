package com.zj.boot_web.common.config;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.ShiroException;
import org.apache.shiro.authz.UnauthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.thymeleaf.exceptions.TemplateInputException;

import com.zj.boot_web.common.base.PublicResult;
import com.zj.boot_web.common.base.PublicResultConstant;
import com.zj.boot_web.common.utils.ComUtil;
import com.zj.boot_web.common.utils.Const;

/**
 * Controller统一异常处理
 *TypesName(类名)：AllControllerAdvice
 *Description(描述)：TODO 
 * @author Adger
 * @date 2018年6月11日下午1:10:34
 *
 */
@ControllerAdvice
public class AllControllerAdvice {
    private static Logger logger = LoggerFactory.getLogger(AllControllerAdvice.class);
    
    public static final String URL = Const.URL; // 路径
	public static final String TEST_URL = Const.TEST_URL; // 测试环境路径
    
    public static final String DEFAULT_ERROR_404_VIEW = "error/404";  // 404错误界面
    public static final String DEFAULT_ERROR_500_VIEW = "error/500";  // 500错误界面 

    /**
     * 应用到所有@RequestMapping注解方法，在其执行之前初始化数据绑定器
     */
    @InitBinder
    public void initBinder(WebDataBinder binder) {
    }

    /**
     * 把值绑定到Model中，使全局@RequestMapping可以获取到该值
     */
    @ModelAttribute
    public void addAttributes(Model model) {
    }
    
    /**
     * 404异常页面转发
     */
    @ExceptionHandler(value = NoHandlerFoundException.class)
    public ModelAndView defaultError(HttpServletRequest req, Exception e) throws Exception {  
        ModelAndView mav = new ModelAndView();  
        String path = req.getServletPath();
		if (path.matches(".*/((api)).*")) {
			mav.setViewName("redirect:" + TEST_URL + "/api/404.action");
		} else {
			mav.addObject("exception", e);  
	        mav.addObject("url", req.getRequestURL());
	        mav.setViewName(DEFAULT_ERROR_404_VIEW);
		}
        return mav;  
    }
    

    /**
     * 全局异常捕捉处理
     */
    @ResponseBody
    @ExceptionHandler(value = Exception.class)
	public PublicResult<String> errorHandler(HttpServletRequest req, Exception ex) {
		ex.printStackTrace();
		logger.error("接口出现严重异常：{}", ex.getMessage());
		if (ex instanceof NoHandlerFoundException) {
			return new PublicResult<String>(
					PublicResultConstant.REQUEST_ERROR_404, null);
		} else {
			PublicResultConstant.REQUEST_ERROR_500.setMsg(ex.getMessage());
			return new PublicResult<String>(
					PublicResultConstant.REQUEST_ERROR_500, null);
		}
		// return new PublicResult<String>(PublicResultConstant.FAILED, null);
	}

    /**
     * 捕捉UnauthorizedException
     * @return
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(UnauthorizedException.class)
    @ResponseBody
    public PublicResult<String> handle401() {
        return new PublicResult<String>(PublicResultConstant.USER_NO_PERMITION, null);
    }

    /**
     * 捕捉shiro的异常
     * @param e
     * @return
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(ShiroException.class)
    @ResponseBody
    public PublicResult<String> handle401(ShiroException e) {
        return new PublicResult<String>(PublicResultConstant.USER_NO_PERMITION, null);
    }

    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(TemplateInputException.class)
    @ResponseBody
    public PublicResult<String> handle401(TemplateInputException e) {
        return new PublicResult<String>(PublicResultConstant.USER_NO_PERMITION, null);
    }
    
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseBody
    public PublicResult<String> requestMethod(HttpRequestMethodNotSupportedException e) {
    	String method = e.getMethod().toUpperCase();
    	if (ComUtil.equals(method, "POST")) {
    		PublicResultConstant.REQUEST_METHOD.setMsg("请求类型错误，请尝试GET请求");
		}

    	return new PublicResult<String>(PublicResultConstant.REQUEST_METHOD, null);
    }

    /*@ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(value = ParamJsonException.class)
    @ResponseBody
    public BaseResult<String> handleParam(Exception e) {
        if(e instanceof ParamJsonException) {
            logger.info("参数错误："+e.getMessage());
            return new BaseResult<String>(PublicResultConstant.PARAM_ERROR.getCode(), e.getMessage(),null);
        }
        return new PublicResult<String>(PublicResultConstant.ERROR, null);
    }*/
}