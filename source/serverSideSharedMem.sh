user=$1
host=$2
echo "Compressing data to be sent to the server side node(SharedMemory)."
rm -rf serverSideSharedMem.zip
zip -rq serverSideSharedMem.zip sharedMem comInfo
echo "Transfering data to the server side node."
ssh $user@$host.ua.pt 'mkdir -p teste/the_restaurant2'
ssh $user@$host.ua.pt 'rm -rf teste/the_restaurant2/*'
scp serverSideSharedMem.zip $user@$host.ua.pt:teste/the_restaurant2
echo "Decompressing data sent to the server side node."
ssh $user@$host.ua.pt 'cd teste/the_restaurant2 ; unzip -uq serverSideSharedMem.zip'
echo "Compiling program files at the server side node."
ssh $user@$host.ua.pt 'cd teste/the_restaurant2 ; javac */*.java'
echo "End of compiling at the server side node."
echo "Executing program at the server side node."
ssh $user@$host.ua.pt 'cd teste/the_restaurant2 ; java sharedMem.SharedMemServer '$3' '$4''
echo "Server  shutdown."
ssh $user@$host.ua.pt 'cd teste/the_restaurant2'
scp $user@$host.ua.pt:teste/the_restaurant2/log.txt ~/Desktop
rm serverSideSharedMem.zip
