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
        withMaven(maven: 'Maven3.6', globalMavenSettingsConfig: ' /Users/gaiyucheng/software/apache-maven-3.6.2/conf/settings.xml', globalMavenSettingsFilePath: ' /Users/gaiyucheng/software/apache-maven-3.6.2/conf/settings.xml', jdk: 'jdk1.8.0_231', mavenLocalRepo: '/Users/gaiyucheng/software/apache-maven-3.6.2/repository', mavenSettingsConfig: '/Users/gaiyucheng/software/apache-maven-3.6.2/conf/settings.xml', mavenSettingsFilePath: '/Users/gaiyucheng/software/apache-maven-3.6.2/conf/settings.xml') {
          sh 'sh "mvn clean install -Dskip Tests"'
        }

      }
    }

  }
  environment {
    credentialsId = '1c0b2feb-65f5-49af-9c77-2752a11fffbe'
  }
}