pipeline {
    agent any

    stages {

        stage('Build Maven') {
            steps {
                sh 'mvn clean install -DskipTests'
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
                sh 'docker run -d --name test-container automation-test'
            }
        }

        stage('Check Logs') {
            steps {
                sh 'docker logs test-container'
            }
        }
    }
}
