## my-blockchain

### Description
My blockchain implementation in Java

### Tools
Java

### Requirements
maven

### Features
* SHA256 algorithm for hash
* requires proof of work mining to validate new blocks
* methods for checking if data in it is valid and unchanged

### Build/Run
```
mvn clean compile assembly:single exec:java
```

### Actual version
```
*** My Blockchain ***

Trying to mine block 1...
Block mined: 0000006956846d9d049cae8a382dc3e4faa8d41de6809580ac1c95ebefede6eb

Trying to mine block 2...
Block mined: 000005ecd3a8ba70e63ab0a8975c8ccc2562eb918d0dc451f64d6244bcc0aba9

Trying to mine block 3...
Block mined: 00000a95292012a982e082d08cc3c8d8d70aa30046a7898cde04c4c73393b801

Blockchain is valid: true

Blockchain:
[
  {
    "hash": "0000006956846d9d049cae8a382dc3e4faa8d41de6809580ac1c95ebefede6eb",
    "previousHash": "0",
    "data": "I am first block",
    "timeStamp": 1515154151390,
    "nonce": 4462828
  },
  {
    "hash": "000005ecd3a8ba70e63ab0a8975c8ccc2562eb918d0dc451f64d6244bcc0aba9",
    "previousHash": "0000006956846d9d049cae8a382dc3e4faa8d41de6809580ac1c95ebefede6eb",
    "data": "I am second block",
    "timeStamp": 1515154156319,
    "nonce": 1200888
  },
  {
    "hash": "00000a95292012a982e082d08cc3c8d8d70aa30046a7898cde04c4c73393b801",
    "previousHash": "000005ecd3a8ba70e63ab0a8975c8ccc2562eb918d0dc451f64d6244bcc0aba9",
    "data": "I am third block",
    "timeStamp": 1515154157774,
    "nonce": 675895
  }
]
```


