
pipeline {
    agent any

    options {
        timeout(time: 30, unit: 'MINUTES')
    }

    stages {

        stage('Build Maven') {
            steps {
                sh 'mvn clean install -DskipTests'
            }
        }

        stage('Docker Build') {
            steps {
                sh 'docker build --cache-from automation-test -t automation-test .'
            }
        }

        stage('Remove Old Container') {
            steps {
                sh 'docker rm -f test-container || true'
            }
        }

        stage('Run Container') {
            steps {
                sh 'docker run -d --name test-container automation-test'
            }
        }

        stage('Verify Container Running') {
            steps {
                sh 'docker ps'
            }
        }

        stage('Check Logs') {
            steps {
                sh 'docker logs test-container'
            }
        }
    }

    post {
        success {
            echo 'Pipeline completed successfully!'
        }

        failure {
            echo 'Pipeline failed!'
        }
    }
}

