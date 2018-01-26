package com.blockchain;

import java.util.ArrayList;
import java.util.HashMap;

class ChainValidator {
    static boolean check(ArrayList<Block> blockchain, Transaction genesisTransaction, int difficulty) {
        System.out.println("\nValidate blockchain...");
        Block currentBlock;
        Block previousBlock;
        HashMap<String, TransactionOutput> tempUTXOs = new HashMap<String, TransactionOutput>();
        tempUTXOs.put(genesisTransaction.outputs.get(0).id, genesisTransaction.outputs.get(0));
        String hashTarget = new String(new char[difficulty]).replace('\0', '0');

        for (int i = 1; i < blockchain.size(); i++) {
            previousBlock = blockchain.get(i - 1);
            currentBlock = blockchain.get(i);

            if (!currentBlock.hash.equals(currentBlock.calculateHash())) {
                System.out.println("Error: Current Hashes not equal");
                return false;
            }
            if (!previousBlock.hash.equals(currentBlock.previousHash)) {
                System.out.println("Error: Previous Hashes not equal");
                return false;
            }
            if (!currentBlock.hash.substring(0, difficulty).equals(hashTarget)) {
                System.out.println("Error: This block has not been mined");
                return false;
            }

            TransactionOutput tempOutput;
            for (int t = 0; t < currentBlock.transactions.size(); t++) {
                Transaction currentTransaction = currentBlock.transactions.get(t);

                if (!currentTransaction.verifiySignature()) {
                    System.out.println("Error: Signature on transaction(" + t + ") is invalid");
                    return false;
                }
                if (currentTransaction.getInputsValue() != currentTransaction.getOutputsValue()) {
                    System.out.println("Error: Inputs are not equal to outputs on transaction(" + t + ")");
                    return false;
                }

                for (TransactionInput input : currentTransaction.inputs) {
                    tempOutput = tempUTXOs.get(input.transactionOutputId);

                    if (tempOutput == null) {
                        System.out.println("Error: Referenced input on transaction(" + t + ") is missing");
                        return false;
                    }

                    if (input.UTXO.value != tempOutput.value) {
                        System.out.println("Error: Referenced input transaction(" + t + ") value is Invalid");
                        return false;
                    }

                    tempUTXOs.remove(input.transactionOutputId);
                }

                for (TransactionOutput output : currentTransaction.outputs) {
                    tempUTXOs.put(output.id, output);
                }
                if (currentTransaction.outputs.get(0).recipient != currentTransaction.recipient) {
                    System.out.println("Error: transaction(" + t + ") output recipient is not who it should be");
                    return false;
                }
                if (currentTransaction.outputs.get(1).recipient != currentTransaction.sender) {
                    System.out.println("Error: transaction(" + t + ") output recipient is not a sender.");
                    return false;
                }
            }
        }
        System.out.println("Validate blockchain... DONE");
        return true;
    }
}