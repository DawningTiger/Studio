import rabbitpy

for message in rabbitpy.consume('amqp://guest:guest@localhost:5672/%2f',
                                'test-messages'):
    message.pprint()            
    message.ack()
