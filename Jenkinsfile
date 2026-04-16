pipeline {
    agent any

    tools {
        maven 'maven'
    }

    stages {

        stage('Checkout Code') {
            steps {
                git branch: 'master', url: 'https://github.com/Ramu-prog/opencartframework'
            }
        }

        stage('Build') {
            steps {
                bat "mvn clean package -Dmaven.test.failure.ignore=true"
            }
            post {
                success {
                    junit '**/target/surefire-reports/TEST-*.xml'
                    archiveArtifacts 'target/*.jar'
                }
            }
        }

        stage('Regression Test (QA)') {
            steps {
                catchError(buildResult: 'SUCCESS', stageResult: 'FAILURE') {
                    bat "mvn test -Dsurefire.suiteXmlFiles=src/test/resources/testrunners/testng_regression.xml -Denv=qa"
                }
            }
        }

        stage('Publish Allure Report') {
            steps {
                allure tool: 'allure', [
                    includeProperties: false,
                    jdk: '',
                    properties: [],
                    reportBuildPolicy: 'ALWAYS',
                    results: [[path: 'allure-results']]
                ]
            }
        }

        stage('Publish ChainTest Report') {
            steps {
                publishHTML([
                    allowMissing: false,
                    alwaysLinkToLastBuild: true,
                    keepAll: true,
                    reportDir: 'target/chaintest',
                    reportFiles: 'Index.html',
                    reportName: 'ChainTest Report'
                ])
            }
        }

        stage('Sanity Test (Stage)') {
            steps {
                catchError(buildResult: 'SUCCESS', stageResult: 'FAILURE') {
                    bat "mvn test -Dsurefire.suiteXmlFiles=src/test/resources/testrunners/testng_sanity.xml -Denv=stage"
                }
            }
        }

        stage('Sanity Test (Prod)') {
            steps {
                catchError(buildResult: 'SUCCESS', stageResult: 'FAILURE') {
                    bat "mvn test -Dsurefire.suiteXmlFiles=src/test/resources/testrunners/testng_sanity.xml -Denv=prod"
                }
            }
        }
    }
}