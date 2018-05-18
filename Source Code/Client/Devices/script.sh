#!/bin/bash

lxterm -e 'bash -c "cd DoorLock/src && make compile; make ip=$(hostname -I) run"' &
lxterm -e 'bash -c "cd Lamp/src && make compile; make ip=$(hostname -I) run"' &
lxterm -e 'bash -c "cd SecurityLaser/src && make compile; make ip=$(hostname -I) run"' & 
lxterm -e 'bash -c "cd LightBulb/src && make compile; make ip=$(hostname -I) run"'