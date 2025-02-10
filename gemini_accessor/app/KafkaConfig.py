
from confluent_kafka import Producer, Consumer
import json
from LlmService import myArtical as llm

class KafkaConfig:
    def __init__(self, bootstrap_servers: str = 'localhost:9092'):
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
            print(f'שגיאה בשליחת ההודעה: {err}')
        else:
            print(f'ההודעה נשלחה בהצלחה ל-{msg.topic()}')
    
    def produce_message(self, topic: str, message:str) -> None:
        producer = self.create_producer()
        try:
            t=message

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
            print(f"שגיאה בשליחת ההודעה: {e}")

    
    def consume_messages(self) -> None:
        consumer = self.create_consumer("newsAi")
        consumer.subscribe(["api.getMyArticle"])
        
        try:
            while True:
                print("whiting for message")
                msg = consumer.poll(1.0)
                if msg is None:
                    continue
                if msg.error():
                    print(f"שגיאת צרכן: {msg.error()}")
                    continue
                
                value = json.loads(msg.value().decode('utf-8'))
                ans= llm(value)
                self.produce_message("LlmAnswer",ans)
                print(f"התקבלה הודעה: {value}")
                
        except KeyboardInterrupt:
            print("עוצר את הצרכן...")
        finally:
            consumer.close()





