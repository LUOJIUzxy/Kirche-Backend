pipeline {
    agent any
    stages {
        stage("build") {
            steps {
                // sh 'npm install'
                // sh 'npm build'
                echo 'building the application...'
                script {
                    // groovy script must be included in script {} test
                    def test = 2 + 2 < 3 ? 'cool' : 'not cool at all'
                    echo test
                }
            }
        }
        stage("test") {
            steps {
                echo 'testing the application...'
            }
        }
        stage("deploy") {
            steps {
                echo 'deploying the application...'
            }
        }
    }
}
