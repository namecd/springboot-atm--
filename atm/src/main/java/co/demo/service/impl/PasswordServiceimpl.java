package co.demo.service.impl;

import co.demo.common.exception.ExceptionsForAtm;
import co.demo.mapper.CardInfoMapper;
import co.demo.common.enums.ExceptionEnum;
import co.demo.domain.AjaxRes;
import co.demo.domain.CardInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import co.demo.service.PasswordService;

@Service
@Transactional  //出错时数据回滚，保证一致性
@Slf4j
public class PasswordServiceimpl implements PasswordService {
    @Autowired
    private CardInfoMapper cardInfoMapper;
    /*
    * 逻辑和LoginServiceImpl一样，都是先创建前端返回信息，
    * 然后再创建对应的info子类，再通过传入的参数进行初始化
    * 最后在数据库中查询刚创建的子类即可。
    * */
    @Override
    public AjaxRes checkPassword(String cardId, String password) {
        AjaxRes ajaxRes = new AjaxRes();
        CardInfo cardInfo =new CardInfo();
        cardInfo.setCardId(cardId);
        cardInfo.setPassword(password);
        try{
            CardInfo card = cardInfoMapper.selectOne(cardInfo);
            if(StringUtils.isEmpty(card)){
                ajaxRes.setRes("error");
                ajaxRes.setMesg("密码验证错误");
            }
            else{
                ajaxRes.setRes("success");
                ajaxRes.setMesg("密码验证通过");
            }
        }
        catch (Exception e){
            log.error("密码验证失败");
            throw new ExceptionsForAtm(ExceptionEnum.LOGIN_ERROE);
        }
        return ajaxRes;
    }

    @Override
    public AjaxRes updatePassword(String cardId, String password) {
        AjaxRes ajaxRes = new AjaxRes();
        CardInfo cardInfo = new CardInfo();
        cardInfo.setCardId(cardId);
        try{
            CardInfo card = cardInfoMapper.selectOne(cardInfo);
            card.setPassword(password);
            int i = cardInfoMapper.updateByPrimaryKey(card);
            if(i>0){
                ajaxRes.setRes("success");
                ajaxRes.setMesg("密码修改成功");
            }
            else{
                ajaxRes.setRes("error");
                ajaxRes.setMesg("修改密码失败");
            }
        }catch (Exception e){
            log.error("密码修改失败");
            throw  new ExceptionsForAtm(ExceptionEnum.UPDATE_PASSWORD_ERROR);
        }
        return ajaxRes;
    }
}
