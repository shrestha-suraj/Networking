Name: Suraj Shrestha
Email: sshrest4@go.olemiss.edu
Project 3

Description
There are two files in this directory. One is PingServer.java and the another is PingClient.java.
The server file consist the code provided by professor. To run the file simply guide the command line to
the working directory. Compile file usign 'javac PingServer.java' and then run it by passing a port argument
i.e. 'java PingServer XXXX' where XXXX is 4 digit port number greate than 1024. This runs the server as our
local host.

Now, we need to ping the server. For this we create a client UDP with necessary components, similar to the server
code. After this, we compile the code similarly and then run it. To run the file we pass hostname and port number
as arguments i.e. 'java PingClient "localhost" YYYY' wherer YYYY is port number greater than 1024 and not equal to
server port number. The result we get is various packet losts that happens during 1 sec delay.

Output 1 Example: 
Timeout for packet 0
Timeout for packet 1
Timeout for packet 2
Received from 127.0.0.1: Ping 3 Delay: 57
Received from 127.0.0.1: Ping 4 Delay: 12
Received from 127.0.0.1: Ping 5 Delay: 86
Received from 127.0.0.1: Ping 6 Delay: 155
Received from 127.0.0.1: Ping 7 Delay: 191
Timeout for packet 8
Received from 127.0.0.1: Ping 9 Delay: 168

Outout 2 Example:
Timeout for packet 0
Received from 127.0.0.1: Ping 1 Delay: 50
Received from 127.0.0.1: Ping 2 Delay: 175
Received from 127.0.0.1: Ping 3 Delay: 18
Received from 127.0.0.1: Ping 4 Delay: 77
Received from 127.0.0.1: Ping 5 Delay: 129
Timeout for packet 6
Received from 127.0.0.1: Ping 7 Delay: 140
Received from 127.0.0.1: Ping 8 Delay: 135
Received from 127.0.0.1: Ping 9 Delay: 60