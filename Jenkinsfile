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

    stage('image') {
      steps {
        sh '''source ~/.bash_profile

cd glen-eureka

docker build -t glen-eureka:v$BUILD_ID .

docker tag glen-eureka:v$BUILD_ID 192.168.43.166/docker-test/glen-eureka:v$BUILD_ID

docker push 192.168.43.166/docker-test/glen-eureka:v$BUILD_ID

docker rmi glen-eureka:v$BUILD_ID'''
      }
    }

    stage('deploy/upgrade') {
      steps {
        rancher(image: '192.168.43.166/docker-test/glen-eureka:v$BUILD_ID', environmentId: 'c-75tmk:p-5gvnj', service: 'jenkins/glen-eureka', credentialId: '73159fef-c482-44d0-81ea-35577d50a3b3', endpoint: 'https://192.168.43.166:8444/v3', confirm: true, ports: '8081', environments: 'dev:dev', timeout: 600, startFirst: true)
      }
    }

  }
  environment {
    credentialsId = '73159fef-c482-44d0-81ea-35577d50a3b3'
  }
}