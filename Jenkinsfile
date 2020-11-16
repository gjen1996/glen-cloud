pipeline {
  agent {
    node {
      label 'worker_node'
    }

  }
  stages {
    stage('error') {
      steps {
        git 'https://github.com/gjen1996/glen-cloud.git'
      }
    }

  }
}