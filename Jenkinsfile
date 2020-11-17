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
        withMaven(maven: 'Maven3.6', globalMavenSettingsConfig: '/var/jenkins_home/apache-maven-3.6.2/conf/settings.xml', globalMavenSettingsFilePath: '/var/jenkins_home/apache-maven-3.6.2/conf/settings.xml', jdk: 'jdk1.8.0_231', mavenLocalRepo: '/var/jenkins_home/apache-maven-3.6.2/repository', mavenSettingsConfig: '/var/jenkins_home/apache-maven-3.6.2/conf/settings.xml', mavenSettingsFilePath: '/var/jenkins_home/apache-maven-3.6.2/conf/settings.xml') {
          sh 'sh "mvn clean install -Dskip Tests"'
        }

      }
    }

  }
  environment {
    credentialsId = '1c0b2feb-65f5-49af-9c77-2752a11fffbe'
  }
}