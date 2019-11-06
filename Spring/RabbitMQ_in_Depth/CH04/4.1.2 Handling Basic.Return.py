import datetime
import rabbitpy

# Connect to the default URL of amqp://guest:guest@localhost:15672/%2F
connection = rabbitpy.Connection()
try:
    with connection.channel() as channel:
        # Create the message to send
        body = 'server.cpu.utilization 25.5 1350884514'
        properties = {'content_type': 'text/plain',
                      'timestamp': datetime.datetime.now(),
                      'message_type': 'graphite metric'}
        message = rabbitpy.Message(channel, body, properties)

        # Publish the message to the exchange with the routing key
        # "server-metrics" and make sure it is routed to the exchange
        message.publish('chapter2-example', 'server-metrics', mandatory=True)
except rabbitpy.exceptions.MessageReturnedException as error:
    print('Publish failure: %s' % error)
