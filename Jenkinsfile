pipeline {
    agent any

    stages {

        stage('Clone') {
            steps {
                git 'https://github.com/hj8836430-prog/-Automation-Exercise-Website.git'
            }
        }

        stage('Docker Build') {
            steps {
                sh 'docker build -t automation-test .'
            }
        }

        stage('Run Container') {
            steps {
                sh 'docker rm -f test-container || true'
                sh 'docker run --name test-container automation-test'
            }
        }
    }
}