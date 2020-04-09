How to init kafka server on windows:
1. start zookeeper from Projects dir:
kafka\bin\windows\zookeeper-server-start.bat kafka\config\zookeeper.properties
Stay the window open

2. kafka\bin\windows\kafka-server-start.bat kafka\config\server.properties  
stay the window open

3. test kafka and zookeeper:
kafka\bin\windows\zookeeper-shell.bat localhost:2181 ls /brokers/ids  

4. Create kafka topic:
kafka\bin\windows\kafka-topics.bat --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic test   

5. review avaliable topics : 
kafka\bin\windows\kafka-topics.bat --list --zookeeper localhost:2181 

6. Start producer: 
kafka\bin\windows\kafka-console-producer.bat --broker-list localhost:9092 --topic test   
you can send messages to kafka topic in the broker

7. start consumer:
kafka\bin\windows\kafka-console-consumer.bat --bootstrap-server localhost:9092 --topic test --from-beginning   
Now you can recieve messages that you produce to kafka topic in the broker 



Apache maven : 

Download maven 3.6.3 
Add maven to the path 
test mvn -version via cmd

setup intelij:
update pom.xml with maven compiler plugin following the java version 
add kafka and log4j dependecies

setup Config and producer classes 
add scripts folder with the kafka cli: 

zookeeper:
C:\Users\danie\Desktop\Projects\kafka\bin\windows\zookeeper-server-start.bat C:\Users\danie\Desktop\Projects\kafka\config\zookeeper.properties
kafka:
C:\Users\danie\Desktop\Projects\kafka\bin\windows\kafka-server-start.bat C:\Users\danie\Desktop\Projects\kafka\config\server.properties
create topic :
C:\Users\danie\Desktop\Projects\kafka\bin\windows\kafka-topics.bat --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic raw_data


1. Now play the producer class
2. Play the consumer script to see the messages 
C:\Users\danie\Desktop\Projects\kafka\bin\windows\kafka-console-consumer.bat --bootstrap-server localhost:9092 --topic raw_data --from-beginning