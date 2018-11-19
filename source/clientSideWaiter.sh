user=${10}
host=${11}
echo "Compressing data to be sent to the client side node(waiter)."
rm -rf clientSideWaiter.zip
zip -rq clientSideWaiter.zip waiter comInfo
echo "Transfering data to the client side node."
ssh $user@$host.ua.pt 'mkdir -p teste/the_restaurant2'
ssh $user@$host.ua.pt 'rm -rf teste/the_restaurant2/*'
scp clientSideWaiter.zip $user@$host.ua.pt:teste/the_restaurant2
echo "Decompressing data sent to the client side node."
ssh $user@$host.ua.pt 'cd teste/the_restaurant2 ; unzip -uq clientSideWaiter.zip'
echo "Compiling program files at the client side node."
ssh $user@$host.ua.pt 'cd teste/the_restaurant2 ; javac */*.java'
sleep 3
echo "Executing program at the client side node."
ssh $user@$host.ua.pt 'cd teste/the_restaurant2 ; java waiter.WaiterStart '$1' '$2' '$3' '$4' '$5' '$6' '$7' '$8' '$9''
rm clientSideWaiter.zip
