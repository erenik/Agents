#!/bin/sh
echo Startup script running... > AgentStartupScript.log
agentDir=/home/ubuntu/JADE/Agents/AgentSmith
cd $agentDir
logFile=Startup.log
echo Yolo >> $logFile
git pull >> $logFile
javac ServerAgentSmith.java >> $logFile
(java jade.Boot -agents hostAgent:ServerAgentSmith & >> $logFile)
