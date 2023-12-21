package co.demo.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;
/*
* 本数据结构设计的目的是辅助完成交易转账操作。
* */
@Data
@NoArgsConstructor
@Table(name = "transinfo")
public class TransInfo {
    private String cardId;   //银行卡号
    private Integer transType;  //交易类型
    private BigDecimal transMoney;   //交易金额
    private Date transDate;   //交易时间
    private String remarks;   //备注信息
}
