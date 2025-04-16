CREATE TABLE accounts (
    id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '主键',
    card_no VARCHAR(128) NOT NULL DEFAULT '' COMMENT '账号',
    balance DECIMAL(18,2) not null DEFAULT 0.00  COMMENT '账户余额',
    is_deleted INT not null DEFAULT 0  COMMENT '0 未删除  1 已删除',
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    UNIQUE KEY (card_no)
)CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci COMMENT='账户信息表';


CREATE TABLE transaction_events (
    id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '主键',
    from_id BIGINT NOT NULL DEFAULT 0 COMMENT '转出账号',
    to_id BIGINT NOT NULL DEFAULT 0 COMMENT '转入账号',
    serial_no BIGINT NOT NULL DEFAULT 0 COMMENT '交易流水号',
    amount DECIMAL(18,2) not null DEFAULT 0.00  COMMENT '转账金额',
    state INT not null DEFAULT 0  COMMENT '0 初始状态  1 成功 2 失败',
    reason VARCHAR(256) NOT NULL DEFAULT '' COMMENT '失败原因',
    is_deleted INT not null DEFAULT 0  COMMENT '0 未删除  1 已删除',
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    UNIQUE KEY (serial_no)
) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci COMMENT='转账事件信息表';


CREATE TABLE transactions (
    id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '主键',
    account_id BIGINT NOT NULL DEFAULT 0 COMMENT '账号id',
    serial_no BIGINT NOT NULL DEFAULT 0 COMMENT '交易流水号',
    amount DECIMAL(18,2) not null DEFAULT 0.00  COMMENT '账户余额',
    category INT not null DEFAULT 0 COMMENT '1 转入 2 转出',
    is_deleted INT not null DEFAULT 0  COMMENT '0 未删除  1 已删除',
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
)CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci COMMENT='交易流水信息表';
