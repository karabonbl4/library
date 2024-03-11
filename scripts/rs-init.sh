#!/bin/bash
echo "sleeping for 10 seconds"
sleep 10

echo rs-init.sh time now: `date +"%T" `
mongosh --host localhost:27017 <<EOF
  var cfg = {
    "_id": "rs0",
    "version": 1,
    "members": [
      {
        "_id": 0,
        "host": "localhost:27017",
        "priority": 2
      }
    ]
  };
  rs.initiate(cfg);
EOF