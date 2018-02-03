package com.blockchain;

import java.util.ArrayList;
import java.util.HashMap;

class ChainValidator {
    static boolean check(ArrayList<Block> blockchain, Transaction genesisTransaction, int difficulty) {
        Block currentBlock;
        Block previousBlock;
        HashMap<String, TransactionOutput> tempUTXOs = new HashMap<String, TransactionOutput>();
        tempUTXOs.put(genesisTransaction.outputs.get(0).id, genesisTransaction.outputs.get(0));
        String hashTarget = new String(new char[difficulty]).replace('\0', '0');

        for (int i = 1; i < blockchain.size(); i++) {
            previousBlock = blockchain.get(i - 1);
            currentBlock = blockchain.get(i);

            if (!currentBlock.hash.equals(currentBlock.calculateHash())) {
                Utils.log("Error: Hash of block is damaged");
                return false;
            }
            if (!previousBlock.hash.equals(currentBlock.previousHash)) {
                Utils.log("Error: Previous block hash and current block hash are not equals");
                return false;
            }
            if (!currentBlock.hash.substring(0, difficulty).equals(hashTarget)) {
                Utils.log("Error: This block has not been mined");
                return false;
            }

            TransactionOutput tempOutput;
            for (int t = 0; t < currentBlock.transactions.size(); t++) {
                Transaction currentTransaction = currentBlock.transactions.get(t);

                if (!currentTransaction.verifiySignature()) {
                    Utils.log("Error: Signature on transaction(" + t + ") is invalid");
                    return false;
                }
                if (currentTransaction.getInputsValue() != currentTransaction.getOutputsValue()) {
                    Utils.log("Error: Inputs are not equal to outputs on transaction(" + t + ")");
                    return false;
                }

                for (TransactionInput input : currentTransaction.inputs) {
                    tempOutput = tempUTXOs.get(input.transactionOutputId);

                    if (tempOutput == null) {
                        Utils.log("Error: Referenced input on transaction(" + t + ") is missing");
                        return false;
                    }

                    if (input.UTXO.value != tempOutput.value) {
                        Utils.log("Error: Referenced input transaction(" + t + ") value is Invalid");
                        return false;
                    }

                    tempUTXOs.remove(input.transactionOutputId);
                }

                for (TransactionOutput output : currentTransaction.outputs) {
                    tempUTXOs.put(output.id, output);
                }
                if (currentTransaction.outputs.get(0).recipient != currentTransaction.recipient) {
                    Utils.log("Error: transaction(" + t + ") output recipient is not who it should be");
                    return false;
                }
                if (currentTransaction.outputs.get(1).recipient != currentTransaction.sender) {
                    Utils.log("Error: transaction(" + t + ") output recipient is not a sender.");
                    return false;
                }
            }
        }
        return true;
    }
}