git pull

git add .
git commit -m "New Changes"
git push

mkdir ../springmicroservice-deploys
cd ../springmicroservice-deploys

git clone git@github.com:nrchaudhry/cwiztechspringmicroserviceapi.git

dirlist=$(find $1 -mindepth 1 -maxdepth 1 -type d)

for dir in $dirlist
do
  (
    cd $dir
    git pull

    rm -r src/main/java
    
    cp -r ../../springmicroservice/src/main/java src/main/

    git add .
    git commit -m "New Changes"
    git push
  )
done

