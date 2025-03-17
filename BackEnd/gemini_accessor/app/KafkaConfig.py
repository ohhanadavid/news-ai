import asyncio
import logging

from confluent_kafka import Producer, Consumer
import json
from LlmService import my_article as llm

class KafkaConfig:
    def __init__(self, bootstrap_servers: str = 'kafka:9092'):
        self.config = {
            'bootstrap.servers': bootstrap_servers
        }
        self.bootstrap_servers=bootstrap_servers
    
    def create_producer(self) -> Producer:
        config = {
            'bootstrap.servers': self.bootstrap_servers,
            'acks': '1',
            'retries': 0,

            'linger.ms': 0,
            'message.timeout.ms': 30000,



        }
        return Producer(config)
    
    def create_consumer(self, group_id: str) -> Consumer:
        consumer_config = {
            **self.config,
            'group.id': group_id,
            'auto.offset.reset': 'earliest'
        }
        return Consumer(consumer_config)
    
    def delivery_callback(self, err, msg):
        if err:
            logging.info(f'שגיאה בשליחת ההודעה: {err}')
        else:
            logging.info(f'ההודעה נשלחה בהצלחה ל-{msg.topic()}')
    
    def produce_message(self, topic: str, message:str) -> None:
        producer = self.create_producer()
        try:
            producer.produce(
                topic,
                value=message,
                headers=[
                    ("content-type", b"application/json")
                ],
            callback=self.delivery_callback
            )
            producer.flush()
        except Exception as e:
            logging.info(f"שגיאה בשליחת ההודעה: {e}")

    def consume_messages(self) -> None:
        logging.basicConfig(
            level=logging.INFO,
            format='%(asctime)s - %(levelname)s - %(message)s'
        )
        logging.info("start run")
        consumer = self.create_consumer("newsAi")
        consumer.subscribe(["api.getMyArticle"])
        
        try:
            while True:

                msg = consumer.poll(1.0)
                if msg is None:
                    continue
                if msg.error():
                    logging.info(f"שגיאת צרכן: {msg.error()}")
                    continue
                asyncio.run(self.message_handling(msg))

                
        except KeyboardInterrupt:
            logging.info("עוצר את הצרכן...")
        finally:
            consumer.close()

    async def  message_handling(self, msg):
        value = json.loads(msg.value().decode('utf-8'))
        ans = llm(value)
        self.produce_message("LlmAnswer", ans)
        return value





