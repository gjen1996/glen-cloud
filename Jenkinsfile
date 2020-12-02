pipeline {
  agent any
  stages {
    stage('clone') {
      steps {
        git(url: 'https://github.com/gjen1996/glen-cloud.git', branch: 'master', changelog: true)
      }
    }

    stage('build') {
      steps {
        tool 'Maven3.6'
        withMaven(maven: 'Maven3.6', jdk: 'openjdk11') {
          sh '''cd glen-eureka
mvn clean install -DskipTests'''
        }

      }
    }

    stage('sonar') {
      steps {
        sh '''
source ~/.bash_profile
echo "SonarScanner is starting......"
result=`echo **/src`
array=(${result///src/ })
for var in ${array[@]}
do
echo "${var} == Module code is being scanned,ŒWaiting......"
/Users/gaiyucheng/software/sonarQube/sonar-scanner-4.5.0.2216-macosx/bin/sonar-scanner -X -Dsonar.host.url=http://192.168.43.166:9000 -Dsonar.login=admin -Dsonar.password=admin -Dsonar.language=java -Dsonar.projectKey=glen-cloud -Dsonar.projectName=glen-cloud -Dsonar.projectVersion=$BUILD_NUMBER -Dsonar.sources=${var}/src/ -Dsonar.sourceEncoding=UTF-8 -Dsonar.java.binaries=${var}/target/
echo "${var} == Module code has been scanned"
echo "====================DiDiDiï,ŒSplit Line===================="
done
'''
      }
    }

    stage('image') {
      steps {
        sh '''source ~/.bash_profile
cd glen-eureka
docker login https://47.102.132.143:9001 -u admin -p Glen19960111
docker build -t glen-eureka:v$BUILD_ID .
docker tag glen-eureka:v$BUILD_ID 47.102.132.143:9001/docker-test/glen-eureka:v$BUILD_ID
docker push 47.102.132.143:9001/docker-test/glen-eureka:v$BUILD_ID
docker rmi glen-eureka:v$BUILD_ID'''
      }
    }

    stage('deploy/upgrade') {
      steps {
        sh '''image="47.102.132.143:9001/docker-test/glen-eureka:v$BUILD_ID"
name="eureka"
url=http://localhost:50020/api/rancherapi/deployUpgrade/v1
curl -i -X POST -H \'Content-type\':\'application/json\' -d \'{"image":"\'$image\'","name":"\'$name\'"}\' http://localhost:50020/api/rancherapi/deployUpgrade/v1'''
      }
    }

  }
  environment {
    credentialsId = '73159fef-c482-44d0-81ea-35577d50a3b3'
  }
}