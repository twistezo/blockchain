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
        // id -> String: "recipient + value + parentTransactionId" encoded by SHA256
        this.id = CryptoUtils
                .applySha256(CryptoUtils.encodeWithBase64(recipient) + Float.toString(value) + parentTransactionId);
    }

    boolean isMine(PublicKey publicKey) {
        return (publicKey == recipient);
    }
}