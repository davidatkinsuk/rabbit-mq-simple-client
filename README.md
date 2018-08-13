# rabbit-mq-simple-client

Using https://www.rabbitmq.com/tutorials/tutorial-one-java.html

Docker based server

      docker run -d --hostname my-rabbit --name some-rabbit -p 8080:15672 -p 5672:5672 rabbitmq:3-management

Can check logs with

      docker logs some-rabbit

See https://hub.docker.com/_/rabbitmq/ for more info

Can look at management page on

      http://localhost:8080