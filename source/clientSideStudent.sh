user=$9
host=${10}
echo "Compressing data to be sent to the client side node(student)."
rm -rf clientSideStudent.zip
zip -rq clientSideStudent.zip student comInfo
echo "Transfering data to the client side node."
ssh $user@$host.ua.pt 'mkdir -p teste/the_restaurant2'
ssh $user@$host.ua.pt 'rm -rf teste/the_restaurant2/*'
scp clientSideStudent.zip $user@$host.ua.pt:teste/the_restaurant2
echo "Decompressing data sent to the client side node."
ssh $user@$host.ua.pt 'cd teste/the_restaurant2 ; unzip -uq clientSideStudent.zip'
echo "Compiling program files at the client side node."
ssh $user@$host.ua.pt 'cd teste/the_restaurant2 ; javac */*.java'
sleep 10
echo "Executing program at the client side node."
ssh $user@$host.ua.pt 'cd teste/the_restaurant2 ; java student.StudentStart '$1' '$2' '$3' '$4' '$5' '$6' '$7' '$8''
rm clientSideStudent.zip
