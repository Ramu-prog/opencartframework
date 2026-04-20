pipeline
{
    agent any

    tools{
        maven 'maven'
    }

    stages
    {
        stage('Build')
        {
            steps
            {
                 git branch: 'master', url: 'https://github.com/Ramu-prog/opencartframework.git'
                 bat "mvn clean compile"
            }
            post
            {
                success
                {
                    echo 'Build completed successfully'
                }
            }
        }

        stage("Deploy to QA"){
            steps{
                echo("deploy to qa done")
            }
        }

        stage('Regression Automation Tests') {
            steps {
                catchError(buildResult: 'SUCCESS', stageResult: 'FAILURE') {
                    cleanWs()
                    git branch: 'master', url: 'https://github.com/Ramu-prog/opencartframework.git'
                    bat "mvn clean test -Dsurefire.suiteXmlFiles=src/test/resources/testrunners/testng_regression.xml -Denv=qa"
                }
            }
        }

        // ALLURE STAGE DISABLED - Will re-enable after tool configuration fix
        /*
        stage('Publish Allure Reports') {
            steps {
                script {
                    allure([
                        includeProperties: false,
                        jdk: '',
                        properties: [],
                        reportBuildPolicy: 'ALWAYS',
                        results: [[path: 'target/allure-results']]
                    ])
                }
            }
        }
        */

        stage('Publish ChainTest HTML Report'){
            steps{
                publishHTML([allowMissing: true,
                              alwaysLinkToLastBuild: true,
                              keepAll: true,
                              reportDir: 'target/chaintest',
                              reportFiles: 'index.html',
                              reportName: 'HTML Regression ChainTest Report',
                              reportTitles: ''])
            }
        }

        stage("Deploy to Stage"){
            steps{
                echo("deploy to Stage")
            }
        }

        stage('Sanity Automation Test on Stage') {
            steps {
                catchError(buildResult: 'SUCCESS', stageResult: 'FAILURE') {
                    cleanWs()
                    git branch: 'master', url: 'https://github.com/Ramu-prog/opencartframework.git'
                    bat "mvn clean test -Dsurefire.suiteXmlFiles=src/test/resources/testrunners/testng_sanity.xml -Denv=stage"
                }
            }
        }

        stage('Publish sanity ChainTest Report'){
            steps{
                publishHTML([allowMissing: true,
                              alwaysLinkToLastBuild: true,
                              keepAll: true,
                              reportDir: 'target/chaintest',
                              reportFiles: 'index.html',
                              reportName: 'HTML Sanity ChainTest Report',
                              reportTitles: ''])
            }
        }

        stage("Deploy to PROD"){
            steps{
                echo("deploy to PROD")
            }
        }

        stage('Sanity Automation Test on PROD') {
            steps {
                catchError(buildResult: 'SUCCESS', stageResult: 'FAILURE') {
                    cleanWs()
                    git branch: 'master', url: 'https://github.com/Ramu-prog/opencartframework.git'
                    bat "mvn clean test -Dsurefire.suiteXmlFiles=src/test/resources/testrunners/testng_sanity.xml -Denv=prod"
                }
            }
        }
    }
}