pipeline {
    agent any

    tools {
        maven 'maven'
    }

    environment {
        PROJECT_NAME = 'LUMA Web Automation' // Set your project name here
        //SANITY_TESTS_RAN = 'false'
        // Define your necessary environment variables here
        REPO_URL = 'https://github.com/yogeshcodeshare/SeleniumProjectDemo.git'
        MAVEN_CLEAN_PACKAGE = "mvn -Dmaven.test.failure.ignore=true clean package"
        MAVEN_CLEAN_TEST_REGRESSION = "mvn clean test -Dsurefire.suiteXmlFiles=src/test/resources/testrunner/test_regression.xml"
        MAVEN_CLEAN_TEST_SANITY = "mvn clean test -Dsurefire.suiteXmlFiles=src/test/resources/testrunner/test_sanity.xml"
        ALLURE_RESULTS_DIR = 'companyName/allure-results'
        ALLURE_RESULTS_ARCHIVE_DIR = 'companyName/allure-results-archive'
    }

    stages {
        stage("Build") {
            steps {
                echo "Building the project"
                // Add your build commands here
            }
        }

        stage("Run Unit Tests") {
            steps {
                echo "Running Unit Tests"
                // Add your unit test execution commands here, e.g., Maven commands
            }
        }

        stage("Run Integration Tests") {
            steps {
                echo "Running Integration Tests"
                // Add your integration test execution commands here
            }
        }

        stage("Deploy to Dev") {
            steps {
                echo "Deploying to Dev"
                // Add your deployment logic here if required
            }
        }

        stage("Deploy to QA") {
            when {
                expression { currentBuild.result == null || currentBuild.result == 'SUCCESS' }
            }
            steps {
                echo "Deploying to QA environment..."
                // Add your actual deployment logic here
            }
            post {
                failure {
                    script {
                        currentBuild.result = 'FAILURE'
                    }
                }
            }
        }

        // **New Stage: Archive Old Allure Results**
        stage('Archive Old Allure Results') {
            when {
                expression { fileExists("${ALLURE_RESULTS_DIR}") }
            }
            steps {
                script {
                    // Create archive directory if it doesn't exist
                    sh "mkdir -p ${ALLURE_RESULTS_ARCHIVE_DIR}"
                    // Move old results to the archive with a timestamp or build number
                    sh "mv ${ALLURE_RESULTS_DIR} ${ALLURE_RESULTS_ARCHIVE_DIR}/${BUILD_NUMBER}"
                    echo 'Old Allure results archived.'
                }
            }
        }

        stage('Run Regression Automation Tests') {
            when {
                expression { currentBuild.result == null || currentBuild.result == 'SUCCESS' }
            }
            steps {
                script {
                    try {
                        echo "Running Regression Tests"
                        git url: "${REPO_URL}", credentialsId: '42c246fe-f282-436e-a83a-a1469a36a4da'
                        dir('AkbarTravelsWebLocal') {
                            // Run the Maven command and capture the result
                            def result = sh(script: "${MAVEN_CLEAN_TEST_REGRESSION}", returnStatus: true)

                            // Check the result for failures
                            if (result != 0) {
                                echo "Regression Tests failed."
                                currentBuild.result = 'FAILURE' // Fail the build on test failure
                            } else {
                                echo "Regression Tests ran successfully."
                            }
                        }
                    } catch (Exception e) {
                        echo "Regression Tests encountered an error: ${e.message}"
                        currentBuild.result = 'FAILURE' // Fail the build on execution error
                    }
                }
            }
            post {
                failure {
                    script {
                        currentBuild.result = 'FAILURE'
                    }
                }
            }
        }


        stage('Publish Allure Reports') {
            steps {
                script {
                    allure([
                        includeProperties: false,
                        jdk: '',
                        properties: [],
                        reportBuildPolicy: 'ALWAYS',
                        results: [[path: 'companyName/allure-results']]
                    ])
                }
            }
        }

        stage("Deploy to Stage") {
            when {
                expression { currentBuild.result == null || currentBuild.result == 'SUCCESS' }
            }
            steps {
                script {
                    // Log the current build result before deployment
                    echo "Current Build Result before Deployment to Stage: ${currentBuild.result}"
                    echo "Deploying to Stage"
                    // Your deployment commands here
                }
            }
            post {
                failure {
                    script {
                        currentBuild.result = 'FAILURE'
                    }
                }
                success {
                    script {
                        // Optionally log the result after successful deployment
                        echo "Current Build Result after Deployment to Stage Success: ${currentBuild.result}"
                    }
                }
            }
        }

        stage('Run Sanity Automation Tests') {
            when {
                expression { currentBuild.result == null || currentBuild.result in ['SUCCESS', 'UNSTABLE'] }
            }
            steps {
                    script {
                        echo "Running Sanity Tests"
                        try {
                            git url: "${REPO_URL}", credentialsId: '42c246fe-f282-436e-a83a-a1469a36a4da'
                            dir('AkbarTravelsWebLocal') {
                                // Run the Maven command and capture the result
                                def result = sh(script: "${MAVEN_CLEAN_TEST_SANITY}", returnStatus: true)

                                // Check the result for failures
                                if (result != 0) {
                                    echo "Sanity Tests failed."
                                    currentBuild.result = 'FAILURE' // Fail the build on test failure
                                } else {
                                    echo "Sanity Tests ran successfully."
                                    env.SANITY_TESTS_RAN = 'true'
                                    echo "SANITY_TESTS_RAN value set to: ${env.SANITY_TESTS_RAN}" // Print the value here
                                }
                            }
                        } catch (Exception e) {
                            echo "Sanity Tests encountered an error: ${e.message}"
                            currentBuild.result = 'FAILURE' // Fail the build on execution error
                        }
                    }
                }
            post {
                failure {
                    script {
                        currentBuild.result = 'FAILURE'
                    }
                }
            }
        }

        stage('Publish Sanity Extent Report') {
            when {
                expression { env.SANITY_TESTS_RAN == 'true' }
            }
            steps {
                publishHTML([allowMissing: false,
                    alwaysLinkToLastBuild: false,
                    keepAll: true,
                    reportDir: 'companyName/TestReports',
                    reportFiles: 'TestExecutionReport.html',
                    reportName: 'HTML Sanity Extent Report',
                    reportTitles: ''])
            }
        }
    }
    post {
            always {
                script {
                    echo 'Cleaning up...'
                }
            }
            success {
                script {
                    echo "Build succeeded for project: ${env.PROJECT_NAME}"
                    echo 'Build succeeded! Sending success email notification.'
                }
                emailext(
                    subject: "$PROJECT_NAME - Build # $BUILD_NUMBER - Success",
                    body: """
                    Hi,

                    The build for the project "$PROJECT_NAME" was successful.

                    Build Number: $BUILD_NUMBER
                    Build Status: SUCCESS
                    Console output: $BUILD_URL

                    Thanks,
                    Automation Team
                    """,
                    recipientProviders: [[$class: 'DevelopersRecipientProvider'], [$class: 'RequesterRecipientProvider']],
                    to: 'yogesh.ybm999@gmail.com'
                )
            }
            failure {
                script {
                    echo "Build failed for project: ${env.PROJECT_NAME}"
                    echo 'Build failed! Sending failure email notification.'
                }
                emailext(
                    subject: "$PROJECT_NAME - Build # $BUILD_NUMBER - Failure",
                    body: """
                    Hi,

                    The build for the project "$PROJECT_NAME" failed.

                    Build Number: $BUILD_NUMBER
                    Build Status: FAILURE
                    Console output: $BUILD_URL

                    Please check the logs for more details.

                    Thanks,
                    Automation Team
                    """,
                    recipientProviders: [[$class: 'DevelopersRecipientProvider'], [$class: 'RequesterRecipientProvider']],
                    to: 'yogesh.ybm999@gmail.com'
                )
            }
            unstable {
                script {
                    echo "Build is unstable for project: ${env.PROJECT_NAME}"
                    echo 'Build is unstable! Sending unstable build email notification.'
                }
                emailext(
                    subject: "$PROJECT_NAME - Build # $BUILD_NUMBER - Unstable",
                    body: """
                    Hi,

                    The build for the project "$PROJECT_NAME" is unstable.

                    Build Number: $BUILD_NUMBER
                    Build Status: UNSTABLE
                    Console output: $BUILD_URL

                    Thanks,
                    Automation Team
                    """,
                    recipientProviders: [[$class: 'DevelopersRecipientProvider'], [$class: 'RequesterRecipientProvider']],
                    to: 'yogesh.ybm999@gmail.com'
                )
            }

    }
}