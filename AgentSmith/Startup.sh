#!/bin/sh
echo Startup script running... > AgentStartupScript.log
agentDir=/home/ubuntu/JADE/Agents/AgentSmith
cd $agentDir
logFile=Startup.log
echo Yolo >> $logFile
git pull >> $logFile
javac TCPServer.java >> $logFile
javac Worker.java >> $logFile
(java TCPServer 1> Output.log 2> Error.log &)

# Old stuff. (java -cp "/home/ubuntu/JADE/Agents/AgentSmith/:/home/ubuntu/JADE/jade/lib/jade.jar:/home/ubuntu/JADE/jade/lib/jadeTools.jar:/home/ubuntu/JADE/jade/lib/Base64.jar:/home/ubuntu/JADE/jade/lib/http.jar:/home/ubuntu/JADE/jade/lib/iiop.jar" jade.Boot -agents hostAgent:ServerAgentSmith 1> Output.log 2> Error.log &)

