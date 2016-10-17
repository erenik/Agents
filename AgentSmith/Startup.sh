#!/bin/sh
echo Startup script running... > AgentStartup.log
javac ServerAgentSmith.java
java jade.Boot -agents HostAgent:ServerAgentSmith > AgentStartup.log
