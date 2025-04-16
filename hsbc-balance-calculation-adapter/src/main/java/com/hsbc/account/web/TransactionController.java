package com.hsbc.account.web;

import com.hsbc.account.api.TransactionServiceI;
import com.hsbc.account.dto.TransactionCmd;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 转账
 *
 * TransactionController
 * @author yubo
 */
@Slf4j
@RestController
@RequestMapping("/transactions")
public class TransactionController {

    @Autowired
    private TransactionServiceI transactionService;

    /**
     * 执行交易操作
     * @param command 交易参数
     * @return 如果交易成功，返回 HTTP 状态码 200 和消息 "交易已提交"；否则返回 HTTP 状态码 500 和错误消息
     */
    @PostMapping("/transfer")
    public ResponseEntity<String> transfer(@RequestBody TransactionCmd command) {
        try {
            transactionService.transfer(command);
            return ResponseEntity.ok("交易已提交");
        } catch (Exception e) {
            log.error("交易失败", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("交易失败：" + e.getMessage());
        }
    }

}

