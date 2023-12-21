package co.demo.controller;

import co.demo.domain.AjaxRes;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import javax.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import co.demo.service.LoginService;

@RestController
@Slf4j
@Api(value = "用户登录controller", tags = {"用户登录接口"})
public class LoginController {
    @Autowired
    private LoginService loginService;

    @ApiOperation(value= "校验卡号是否正确",notes = "根据卡号验证账户是否存在",httpMethod = "POST")
    @PostMapping("/getCardInfo")
    public ResponseEntity<AjaxRes> getCardInfo(String cardId, HttpSession session){
        AjaxRes ajaxRes = loginService.getUserInfo(cardId, session);

        //创建 HTTP 200 OK 响应的，同时将给定的对象作为响应主体返回。
        return ResponseEntity.ok(ajaxRes);
    }
    @ApiOperation(value = "效验卡号和密码是否正确" , notes = "根据卡号和密码验证账号是否存在",httpMethod = "POST")
    @PostMapping("/userLogin")
    public ResponseEntity<AjaxRes> userLogin(String cardId,String password,HttpSession session){
        AjaxRes ajaxRes = loginService.getCardInfo(cardId,password);
        session.setAttribute("LoginUser",ajaxRes.getObject());
        return ResponseEntity.ok(ajaxRes);
    }
}
