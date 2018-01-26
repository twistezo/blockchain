package com.blockchain;

import java.security.PublicKey;

class TransactionOutput {
    String id;
    PublicKey recipient;
    float value;
    String parentTransactionId;

    TransactionOutput(PublicKey recipient, float value, String parentTransactionId) {
        this.recipient = recipient;
        this.value = value;
        this.parentTransactionId = parentTransactionId;
        this.id = StringUtils
                .applySha256(StringUtils.getStringFromKey(recipient) + Float.toString(value) + parentTransactionId);
    }

    boolean isMine(PublicKey publicKey) {
        return (publicKey == recipient);
    }
}