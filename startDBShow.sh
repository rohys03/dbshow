#!/bin/sh
export DBSHOW_HOME=/app/puser/dbshow
export JAVA_HOME=/app/jdk/bin/java
export JAVA_OPT=''
export APP_OPT='--server.port=8081 --spring.profiles.active=stage'

nohup java $JAVA_OPTS -jar $DBSHOW_HOME/build/libs/dbshow-0.0.1-SNAPSHOT.jar $APP_OPT >> $DBSHOW_HOME/log/log.out 2>&1 &


