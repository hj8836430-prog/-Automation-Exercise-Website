pipeline {
    agent any

    stages {

        stage('Clone') {
            steps {
                git branch: 'main', url: 'https://github.com/hj8836430-prog/-Automation-Exercise-Website.git'
            }
        }

        stage('Build') {
            steps {
                sh 'mvn clean install -DskipTests'
            }
        }

        stage('Docker Build') {
            steps {
                sh 'docker build -t my-app .'
            }
        }

        stage('Run Container') {
            steps {
                sh '''
                docker stop my-app || true
                docker rm my-app || true

                docker run -d -p 8081:8080 --name my-app my-app
                '''
            }
        }

    }
}