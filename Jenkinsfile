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
        rancher(image: '192.168.43.166/docker-test/glen-eureka:v39', environmentId: 'local:p-4xwbm', service: 'test/test-64b9d7b997-498fp', credentialId: '0e1a3601-f028-4751-97b5-c802edc301f1', endpoint: 'https://192.168.43.166:8444/v3', confirm: true, ports: '8555', environments: 'default:test', timeout: 600, startFirst: true)
      }
    }

  }
  environment {
    credentialsId = '73159fef-c482-44d0-81ea-35577d50a3b3'
  }
}