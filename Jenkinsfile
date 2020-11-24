pipeline {
  agent {
    node {
      label 'worker_node1'
    }

  }
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
        sh '''cd glen-eureka

docker build -t glen-eureka:v0.0.2-SNAPSHOT .

docker tag glen-eureka:v0.0.2-SNAPSHOT 192.168.43.166/docker-test/glen-eureka:v0.0.2-SNAPSHOT

docker push 192.168.43.166/docker-test/glen-eureka:v0.0.2-SNAPSHOT'''
      }
    }

  }
  environment {
    credentialsId = '1c0b2feb-65f5-49af-9c77-2752a11fffbe'
  }
}