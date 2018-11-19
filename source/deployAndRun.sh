#--------------------------------Configuration-----------------------
user="cd0406"
kitchenHost="l040101-ws04"
kitchenPort="22455"
barHost="l040101-ws05"
barPort="22456"
tableHost="l040101-ws06"
tablePort="22454"
sharedMemHost="l040101-ws07"
sharedMemPort="22457"
chefHost="l040101-ws01"
waiterHost="l040101-ws02"
studentHost="l040101-ws03"
nrStudents="7"
nrMeals="3"
#--------------------------------Run-------------------------------
for i in {1..9}
do
	ssh $user@l040101-ws0$i.ua.pt "killall -u "$user""
done
	xterm  -T "Kitchen" 		 +hold -e "./serverSideKitchen.sh $user $kitchenHost $kitchenPort"	&
	xterm  -T "Bar" 				 +hold -e "./serverSideBar.sh $user $barHost $barPort" 				&
	xterm  -T "Table" 			 +hold -e "./serverSideTable.sh $user $tableHost $tablePort $nrStudents"&
	xterm  -T "SharedMemory" +hold -e "./serverSideSharedMem.sh $user $sharedMemHost $sharedMemPort $nrStudents" 						&
	xterm  -T "Chef" 				 +hold -e "./clientSideChef.sh $nrStudents $nrMeals $kitchenHost $barHost $sharedMemHost $kitchenPort $barPort $sharedMemPort $user $chefHost" 	&
	xterm  -T "Waiter" 			 +hold -e "./clientSideWaiter.sh $nrStudents $kitchenHost $barHost $tableHost $sharedMemHost $kitchenPort $barPort $tablePort $sharedMemPort $user $waiterHost" 							&
	xterm  -T "Student" 		 +hold -e "./clientSideStudent.sh $nrStudents $nrMeals $barHost $tableHost $sharedMemHost $barPort $tablePort $sharedMemPort $user $studentHost" 							&

