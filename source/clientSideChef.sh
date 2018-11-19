user=$9
host=${10}
echo "Compressing data to be sent to the client side node(chef)."
rm -rf clientSideChef.zip
zip -rq clientSideChef.zip chef comInfo
echo "Transfering data to the client side node."
ssh $user@$host.ua.pt 'mkdir -p teste/the_restaurant2'
ssh $user@$host.ua.pt 'rm -rf teste/the_restaurant2/*'
scp clientSideChef.zip $user@$host.ua.pt:teste/the_restaurant2
echo "Decompressing data sent to the client side node."
ssh $user@$host.ua.pt 'cd teste/the_restaurant2 ; unzip -uq clientSideChef.zip'
echo "Compiling program files at the client side node."
ssh $user@$host.ua.pt 'cd teste/the_restaurant2 ; javac */*.java'
sleep 3
echo "Executing program at the client side node."
ssh $user@$host.ua.pt 'cd teste/the_restaurant2 ; java chef.ChefStart '$1' '$2' '$3' '$4' '$5' '$6' '$7' '$8''
rm clientSideChef.zip
