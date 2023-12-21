package co.demo.controller;

import co.demo.service.TransacationService;
import co.demo.domain.AjaxRes;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import javax.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@Api(value = "交易金额",tags = {"交易金额接口"})
public class TransacationController {
    private TransacationService transacationService;


    //验证转账的卡号是否存在
    //不能转入一个空卡
    /*@ApiOperation(value = "通过用户的id和取款金额来进行取款操作",notes = "当取款金额大于用户账号可用余额时不可取款",httpMethod = "POST")
    @PostMapping("/queryCardInfo")
    public ResponseEntity <AjaxRes> getUserInfo(String cardId, HttpSession session){
        AjaxRes ajaxRes = transacationService.getUserCardInfo(cardId,session);
        return ResponseEntity.ok(ajaxRes);
    }*/

    @ApiOperation(value = "通过卡号查询账户金额", notes = "银行卡号查询账户余额", httpMethod = "POST")
    @PostMapping("/getMoney")
    public ResponseEntity<AjaxRes> getUserMoney( String cardId) {
        return ResponseEntity.ok(transacationService.searchMoney(cardId));
    }

    @ApiOperation(value = "通过银行卡号和取款金额来进行取款操作", notes = "当取款金额大于用户账号可用金额时不可取款", httpMethod = "POST")
    @PostMapping("/getWithdrawal")
    public ResponseEntity<AjaxRes> getWithdrawal( String cardId,BigDecimal money, HttpSession session){
        AjaxRes ajaxRes = transacationService.drawMoney(cardId, money,session);
        return ResponseEntity.ok(ajaxRes);
    }

    @ApiOperation(value = "通过卡号完成存款操作", notes = "存款操作", httpMethod = "POST")
    @PostMapping("/saveMoney")
    public ResponseEntity<AjaxRes> saveMoney(String cardId,BigDecimal money, HttpSession session){
        AjaxRes ajaxRes = transacationService.saveMoney(cardId, money,session);
        return ResponseEntity.ok(ajaxRes);
    }

    @ApiOperation(value = "通过双方卡号来完成转账交易", notes = "当用户余额小于转出金额时不可转账", httpMethod = "POST")
    @PostMapping("/transferMoney")
    public ResponseEntity<AjaxRes> transferMoney(String userCard, String transCard, BigDecimal money, HttpSession session){
        AjaxRes ajaxRes = transacationService.transacMoney(userCard, transCard, money,session);
        return ResponseEntity.ok(ajaxRes);
    }
}
