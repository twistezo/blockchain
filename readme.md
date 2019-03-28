## blockchain

### Description

Simple blockchain implementation with wallets, transactions and mining system.

How this code works?

1. Create 3 wallets: Genesis Wallet (as `GW`), Wallet A (as `A`) and Wallet B (as `B`)<br/>
   Hardcode first transaction in `GW`<br/>
   Create transaction (without block) and we have e.g. 100 unit on `GW`.<br/>
   Current transactions data counter: `GW transaction: inputs = 0 , outputs = 1` -> `UTXOs = 1`
2. Send 100 unit from `GW` to `A`<br/>
   Create new transaction (as `T`) and add to new block <br/>
   Add 1 output transaction from UTXOs to input transactions of current `T`<br/>
   Current transaction data counter: `T: inputs = 1, outputs = 0`<br/>
   Add two `T` outputs -> `T(recipient, value, T.id)` and `T(sender, value, T.id)`<br/>
   Current transaction data counter: `T: inputs = 1, outputs = 2`<br/>
   Add these two outputs to UTXOs -> `UTXOs = 3`<br/>
   Remove current `T` input from UTXOs -> `UTXOs = 2`

3. Whenever we sending units (e.g. money) and creating transactions we adding a new block to blockchain and next transaction to UTXOs. Every next block in blockchain knows about previous.

What is UTXO?

> A UTXO is an unspent transaction output. In an accepted transaction in a valid blockchain
> payment system (such as Bitcoin), only unspent outputs can be used as inputs to a transaction.
> When a transaction takes place, inputs are deleted and outputs are created as new UTXOs
> that may then be consumed in future transactions.
>
> source: https://www.r3.com/blog/what-is-a-uxto/

### Tools

Java

### Features

- blocks
- wallets
- transactions
- mining system
- digital signatures

### Build/Run

```
mvn clean compile assembly:single exec:java
```

### Actual version output

```
Creating genesis wallet and first transaction..
Mining block: 09d946dfa8d9ccafc23d362e09ec036bfbdf49aed67e9cde8649e4b73f62abaa

Validating blockchain.. OK

Test #1 Sending '40' from Wallet A to Wallet B
-----------------------------------------------
Sender wallet balance = 100.0
Recipient wallet balance = 0.0
Processing transaction..
Mining block: f9b35a09fe2e6ab3b4510dcf88e23bd33816eaa9018ee8721c1a0c1e0245f138
Sender wallet balance = 60.0
Recipient wallet balance = 40.0

Validating blockchain.. OK

Test #2 Sending '1000' from Wallet A to Wallet B
-----------------------------------------------
Sender wallet balance = 60.0
Recipient wallet balance = 40.0
Error: Not enough funds
Mining block: 6fc0b114c5c9cbb56beab0d4150a08367914d7e3c8636da6b79ae406dafa4dc4
Sender wallet balance = 60.0
Recipient wallet balance = 40.0

Validating blockchain.. OK

Test #3 Sending '20' from Wallet B to Wallet A
-----------------------------------------------
Sender wallet balance = 40.0
Recipient wallet balance = 60.0
Processing transaction..
Mining block: 3215f84bb27e5f1e2a31ae791f9ae3a0033217c117969e5baa18e27108d36da1
Sender wallet balance = 20.0
Recipient wallet balance = 80.0

Validating blockchain.. OK

Test #4 Sending '30' from Wallet A to Wallet C
-----------------------------------------------
Sender wallet balance = 80.0
Recipient wallet balance = 0.0
Processing transaction..
Mining block: 858846fe5aad2a75be98a98b6e1a91ce27eb855509bbcbd38b283a59b2230fb3
Sender wallet balance = 50.0
Recipient wallet balance = 30.0

Validating blockchain.. OK
```
