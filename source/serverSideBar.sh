user=$1
host=$2
echo "Compressing data to be sent to the server side node(bar)."
rm -rf serverSideBar.zip
zip -rq serverSideBar.zip bar comInfo
echo "Transfering data to the server side node."
ssh $user@$host.ua.pt 'mkdir -p teste/the_restaurant2'
ssh $user@$host.ua.pt 'rm -rf teste/the_restaurant2/*'
scp serverSideBar.zip $user@$host.ua.pt:teste/the_restaurant2
echo "Decompressing data sent to the server side node."
ssh $user@$host.ua.pt 'cd teste/the_restaurant2 ; unzip -uq serverSideBar.zip'
echo "Compiling program files at the server side node."
ssh $user@$host.ua.pt 'cd teste/the_restaurant2 ; javac */*.java'
echo "End of compiling at the server side node."
echo "Executing program at the server side node."
ssh $user@$host.ua.pt 'cd teste/the_restaurant2 ; java bar.BarServer '$3''
echo "Server  shutdown."
rm serverSideBar.zip
