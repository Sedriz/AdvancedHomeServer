#!/bin/sh

# create passwd file in mosquitto config library.
mosquitto_passwd -c -b "$MOSQUITTO_PASSWD_FILE" "$MQTT_USERNAME" "$MQTT_PASSWORD"

#Copy startup/mosquitto.conf to config/mosquitto.conf
cp /mosquitto/startup/mosquitto.conf /mosquitto/config/mosquitto.conf

# Replace var in config/mosquitto.conf
/mosquitto/startup/env-replace-exec.sh

/docker-entrypoint.sh /usr/sbin/mosquitto -c /mosquitto/config/mosquitto.conf

if [ $# -gt 0 ]; then
    "$@"
fi