pipeline {
    agent any

    environment {
        HEADLESS = 'true'
    }

    stages {
        stage('Checkout Code') {
            steps {
                checkout scm
            }
        }

        stage('Execute TestNG Web Automation') {
            steps {
                script {
                    if (isUnix()) {
                        sh 'chmod +x mvnw'
                        sh './mvnw clean test -Dheadless=${HEADLESS}'
                    } else {
                        bat 'mvnw.cmd clean test -Dheadless=%HEADLESS%'
                    }
                }
            }
        }
    }

    post {
        always {
            // Archive Extent Reports & Screenshots as downloadable Jenkins build artifacts
            archiveArtifacts artifacts: 'test-output/*.html, test-output/screenshots/*.png', allowEmptyArchive: true
        }
        success {
            echo '=== TestNG Data-Driven Test Suite Executed Successfully ==='
        }
        failure {
            echo '=== TestNG Suite Execution Failed. Check Extent Report & Screenshots in Build Artifacts ==='
        }
    }
}
