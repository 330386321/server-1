#!/bin/sh

REDIS_CONF="/conf/redis.conf"

echo "" > ${REDIS_CONF}

# Add additional redis servers into the redis.conf file
echo "port ${REDIS_PORT}" >> ${REDIS_CONF}
echo "cluster-enabled yes" >> ${REDIS_CONF}
echo "cluster-config-file nodes.conf" >> ${REDIS_CONF}
echo "cluster-node-timeout ${CLUSTER_NODE_TIMEOUT}" >> ${REDIS_CONF}
echo "appendonly yes" >> ${REDIS_CONF}

# Start redis
exec redis-server ${REDIS_CONF}