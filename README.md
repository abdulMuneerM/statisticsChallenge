# Statistics Challenge
The main use case for statistics challenge is to provide API to calculate real time statistic from the last 60 seconds.
There will be two APIs, one of them is called every time a transaction is made. It is also the sole input of this rest
API. The other one returns the statistic based of the transactions of the last 60 seconds.

## Usage
POST /transactions

Every Time a new transaction happened, this endpoint will be called.
Sample Body:
    {
        "amount": 12.3,
        "timestamp": 1478192204000
    }

Where:
    amount - Transaction amount
    timestamp - Transaction time in epoch in millis in UTC time zone (this is not current timestamp)

Returns: Empty body with either 201 or 204.
    201 - in case of success
    204 - if transaction is older than 60 seconds


GET​ ​/statistics

This is the main endpoint of this task, this endpoint have to execute in constant time and memory (O(1)). It returns the
 statistic based on the transactions which happened in the last 60 seconds.

Sample Result:
    {
        "sum": 1000,
        "avg": 100,
        "max": 200,
        "min": 50,
        "count": 10
    }

Where:
    sum - Total sum of transaction value in the last 60 seconds
    avg - Average amount of transaction value in the last 60seconds
    max - Single highest transaction value in the last 60 seconds
    min - Single lowest transaction value in the last 60 seconds
    count - Total number of transactions happened in the last 60 seconds

## Requirements
1. JDK 8
2. Maven

## Deployment
1. Build the project using maven goal `install` on pom.
2. Application jar will be present inside `target/` directory.
3. Deploy & run the executable jar using `java -jar xyz.jar`.