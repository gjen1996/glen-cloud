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
        sh '''pwd
ls -al
/Users/gaiyucheng/software/sonarQube/sonar-scanner-4.5.0.2216-macosx/bin/sonar-scanner -X \\
-Dsonar.host.url=http://192.168.43.166:9000 \\
-Dsonar.login=admin \\
-Dsonar.password=admin \\
-Dsonar.language=java \\
-Dsonar.projectKey=glen-cloud \\
-Dsonar.projectName=glen-cloud \\
-Dsonar.projectVersion=$BUILD_NUMBER \\
-Dsonar.sources=./**/src/ \\
-Dsonar.sourceEncoding=UTF-8 \\
-Dsonar.java.binaries=./**/target/ \\
-Dsonar.exclusions=./src/test/**
'''
      }
    }

    stage('image') {
      steps {
        sh '''source ~/.bash_profile

cd glen-eureka

# docker build -t glen-eureka:v$BUILD_ID .
# docker tag glen-eureka:v$BUILD_ID 192.168.43.166/docker-test/glen-eureka:v$BUILD_ID
# docker push 192.168.43.166/docker-test/glen-eureka:v$BUILD_ID
# docker rmi glen-eureka:v$BUILD_ID'''
      }
    }

    stage('deploy/upgrade') {
      steps {
        sh '''image="nginx:1.12.0"
name="nginx"
url=http://localhost:50020/api/rancherapi/deployUpgrade/v1
curl -i -X POST -H \'Content-type\':\'application/json\' -d \'{"image":"\'$image\'","name":"\'$name\'"}\' http://localhost:50020/api/rancherapi/deployUpgrade/v1'''
      }
    }

  }
  environment {
    credentialsId = '73159fef-c482-44d0-81ea-35577d50a3b3'
  }
}