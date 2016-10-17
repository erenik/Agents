#!/bin/sh
echo Startup script running... > AgentStartupScript.log
agentDir=/home/ubuntu/JADE/Agents/AgentSmith
cd $agentDir
echo Yolo > Startup.sh.log
git pull
javac $agentDir/ServerAgentSmith.java
java jade.Boot -agents hostAgent:ServerAgentSmith & 
