#!/bin/sh
echo Startup script running... > AgentStartup.log
java jade.Boot HostAgent:AgentSmith > AgentStartup.log
