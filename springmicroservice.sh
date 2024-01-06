git pull

git add .
git commit -m "New Changes"
git push

mkdir ../springmicroservice-deploys
cd ../springmicroservice-deploys

git clone git@github.com:nrchaudhry/kitaasspringmicroserviceapi.git
git clone git@github.com:nrchaudhry/jcaspringmicroserviceapi.git
git clone git@github.com:nrchaudhry/cwiztechspringmicroserviceapi.git
git clone git@github.com:nrchaudhry/kbfsspringmicroserviceapi.git

dirlist=$(find $1 -mindepth 1 -maxdepth 1 -type d)

for dir in $dirlist
do
  (
    cd $dir
    git pull

    cd src/main/java/com/cwiztech
    dirlist1=$(find $1 -mindepth 1 -maxdepth 1 -type d)
    for dir1 in $dirlist1
    do
      (
        rm -r $dir1
      )
    done
    
    cd ../../../../../../../springmicroservice/src/main/java/com/cwiztech
    dirlist1=$(find $1 -mindepth 1 -maxdepth 1 -type d)
    for dir1 in $dirlist1
    do
      (
        echo $dir1
	cd ../../../../../../springmicroservice-deploys
	cd $dir/src/main/java/com/cwiztech/
        cp -r ../../../../../../../springmicroservice/src/main/java/com/cwiztech/$dir1 .
      )
    done

    cd ../../../../../../springmicroservice-deploys

    cd $dir
    git add .
    git commit -m "New Changes"
    git push
  )
done

