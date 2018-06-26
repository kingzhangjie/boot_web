package com.zj.boot_web.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.zj.boot_web.common.annotation.Log;
import com.zj.boot_web.common.annotation.Param;
import com.zj.boot_web.common.base.BaseController;
import com.zj.boot_web.common.base.PageData;
import com.zj.boot_web.common.base.PublicResult;
import com.zj.boot_web.common.base.PublicResultConstant;
import com.zj.boot_web.common.entity.VerifyCode;
import com.zj.boot_web.common.utils.ALiSms;
import com.zj.boot_web.common.utils.ComUtil;
import com.zj.boot_web.common.utils.Const;
import com.zj.boot_web.common.utils.QrCode;
import com.zj.boot_web.common.utils.RandomCode;

@Controller
@RequestMapping("/api/code/")
public class CodeApiController extends BaseController {

    private Logger log = LoggerFactory.getLogger(CodeApiController.class);

    public static final String LOGIN_KEY = Const.LOGIN_KEY; // session名称

    public static final String PREFIX_PATH = "/api/code/"; // 接口前缀路径

    // 编码格式。发送编码格式统一用UTF-8
    private static String ENCODING = "UTF-8";

    @Log(description = "接口请求", type = "api")
    @PostMapping("getKey.action")
    @ResponseBody
    public PublicResult<PageData> getKey() throws Exception {
        log.info("=====================================请求登录秘钥接口:" + PREFIX_PATH + "getKey.action=====================================");
        PageData pd = new PageData();

        long time = System.currentTimeMillis();
        String timestamp = Long.toString(time);
        String toKen = new SimpleHash("SHA-1", LOGIN_KEY, timestamp).toString();
        this.getRequest().getSession().setAttribute("loginToKen", toKen);//保存session中
        pd.put("token", toKen);
        PublicResult<PageData> results = new PublicResult<PageData>(
                PublicResultConstant.SUCCESS, pd);
        log.info("返回结果==" + results.toString());
        return results;
    }


    @Log(description = "接口请求", type = "api")
    @PostMapping("sendSmsCode.action")
    @Param(value = "phone,token")
    @ResponseBody
    public PublicResult<PageData> sendSmsCode() throws Exception {
        log.info("=====================================请求发送短信验证码接口:" + PREFIX_PATH + "sendSmsCode.action=====================================");
        PublicResult<PageData> results = new PublicResult<PageData>(
                PublicResultConstant.ERROR, null);

        PageData pd = new PageData();
        pd = getPageData();
        log.info("请求参数==" + pd);

        String loginToKen = (String) this.getRequest().getSession().getAttribute("loginToKen");
        String toKen = pd.get("toKen").toString();
        if (ComUtil.isEmpty(loginToKen) || !ComUtil.equals(loginToKen, toKen)) {
            results = new PublicResult<PageData>(
                    PublicResultConstant.NETWORK_ERROR, null);
            return results;
        }

        String u_phone = pd.get("phone").toString();
        //要发送的手机号
        String phone = URLEncoder.encode(u_phone, ENCODING);
        //生成六位随机数字phone
        int suiji = (int) ((Math.random() * 9 + 1) * 100000);
        String verifycode = Integer.toString(suiji);
        String templateCode = "SMS_109540051";
        //String templateParam = "{\"code\":\""+verifycode+"\"}";
        //String success = SMSUtils.aLiYunSendSms(phone, TemplateCode, templateParam);
        PageData result = new PageData();
        result.put("verifyCode", verifycode);
        String success = ALiSms.aLiYunSmsTemplate(templateCode, phone, result);
        if (success == "OK" || "OK".equals(success)) {
            VerifyCode smsCodeBean = new VerifyCode(String.valueOf(phone), String.valueOf(verifycode), 900); //初始化对象，设定过期时间为900s
            this.getRequest().getSession().setAttribute("smsCodeBean", smsCodeBean);
            PublicResultConstant.SUCCESS.setMsg("短信验证码已成功发送，请注意查收");
            results = new PublicResult<PageData>(
                    PublicResultConstant.SUCCESS, null);
        } else {
            PublicResultConstant.SUCCESS.setMsg("抱歉，短信验证码发送失败，请稍后重试");
            results = new PublicResult<PageData>(
                    PublicResultConstant.ERROR, null);
        }
        log.info("返回结果==" + results.toString());
        return results;
    }

    @GetMapping("getImgCode.action")
    @ResponseBody
    public void getImgCode(HttpServletRequest request, HttpServletResponse response) throws Exception {
        log.info("========请求获取图片验证码接口:" + PREFIX_PATH + "getImgCode.action=====");
        PageData pd = new PageData();
        pd = getPageData();
        log.info("请求参数==" + pd);
        String loginToKen = (String) this.getRequest().getSession().getAttribute("loginToKen");
        String toKen = pd.get("toKen").toString();
        if (ComUtil.isEmpty(loginToKen) || !ComUtil.equals(loginToKen, toKen)) {
            JSONObject object = new JSONObject();
            object.put("code", "10000012");
            object.put("msg", "当前请求网络环境异常，请稍后再试");

            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json; charset=utf-8");
            PrintWriter out = null;

            try {
                out = response.getWriter();
                out.append(object.toString());
                logger.debug("返回是\n");
                logger.debug(object.toString());
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (out != null) {
                    out.close();
                }
            }
        } else {
            try {
                response.setContentType("image/jpeg");// 设置相应类型,告诉浏览器输出的内容为图片
                response.setHeader("Pragma", "No-cache");// 设置响应头信息，告诉浏览器不要缓存此内容
                response.setHeader("Cache-Control", "no-cache");
                response.setDateHeader("Expire", 0);
                RandomCode randomCode = new RandomCode();
                randomCode.getRandcode(request, response);// 输出验证码图片方法
            } catch (Exception e) {
                log.error("获取验证码失败>>>> ", e);
            }
        }
    }

    /**
     * 获取二维码接口
     * MethodsName(方法名)：getQrCode
     * Description(描述)：TODO 获取二维码接口
     *
     * @param @return
     * @param @throws
     * @return void
     * @author Adger
     * @date 2018年6月25日下午3:38:04
     *
     */
    @GetMapping("getQrCode.action")
    @ResponseBody
    public void getQrCode(HttpServletRequest request, HttpServletResponse response) throws Exception {
        try {
            response.setContentType("image/jpeg");// 设置相应类型,告诉浏览器输出的内容为图片
            response.setHeader("Pragma", "No-cache");// 设置响应头信息，告诉浏览器不要缓存此内容
            response.setHeader("Cache-Control", "no-cache");
            response.setDateHeader("Expire", 0);
            // 将图像输出到Servlet输出流中。
            ServletOutputStream sos = response.getOutputStream();
            String imgUrl = "http://g.subaotec.com/ressources/img/static/wechat/qr-logo.jpg";
            QrCode.encode("https://w.url.cn/s/A1iiFzb", null, imgUrl, sos, true);
            sos.close();
        } catch (Exception e) {
            log.error("获取二维码失败>>>> ", e);
        }


    }
}
