pipeline {
  agent {
    node {
      label 'worker'
    }

  }
  stages {
    stage('clone') {
      steps {
        git 'https://github.com/gjen1996/glen-cloud.git'
      }
    }

  }
}