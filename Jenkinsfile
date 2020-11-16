pipeline {
  agent {
    node {
      label 'worker'
    }

  }
  stages {
    stage('clone') {
      steps {
        git(url: 'https://github.com/gjen1996/glen-cloud.git', branch: 'master')
      }
    }

    stage('end') {
      steps {
        sleep 10
      }
    }

  }
}