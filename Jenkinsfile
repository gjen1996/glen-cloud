pipeline {
  agent any
  stages {
    stage('clone') {
      steps {
        git(url: 'https://github.com/gjen1996/glen-cloud.git', branch: 'master', changelog: true)
      }
    }

  }
  environment {
    credentials = '1c0b2feb-65f5-49af-9c77-2752a11fffbe'
  }
}