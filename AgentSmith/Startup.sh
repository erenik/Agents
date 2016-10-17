#!/bin/sh
echo Startup script running... > AgentStartup.log
git pull
agentDir=/home/ubuntu/JADE/Agents/AgentSmith
javac $agentDir/ServerAgentSmith.java
java jade.Boot -agents HostAgent:$agentDir/ServerAgentSmith > AgentStartup.log
