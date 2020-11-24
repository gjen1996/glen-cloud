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
        rancher(image: ' 192.168.43.166/docker-test/glen-eureka:v$BUILD_ID', environmentId: 'k8sformac', service: 'kubernetes', credentialId: 'token-n7stn:2wqcldpd4ls9g2bkxfwf99wvvbl2kbpz4n7kkt6mbp6dkdwhzp7mx7', endpoint: 'docker-desktop', confirm: true, ports: '8081', environments: 'dev', timeout: 600, startFirst: true)
      }
    }

  }
  environment {
    credentialsId = '1c0b2feb-65f5-49af-9c77-2752a11fffbe'
  }
}