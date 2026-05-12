pipeline {
    agent any

    options {
        timestamps()
    }

    tools {
        maven 'Maven'
    }

    stages {

        // ── Checkout ─────────────────────────────────────────────────────────
        stage('Checkout') {
            steps {
                checkout([
                    $class: 'GitSCM',
                    branches: [[name: '*/main']],
                    userRemoteConfigs: [[
                        url: 'https://github.com/Sre803/private-maven-sre803.git',
                        credentialsId: 'github_maven_repo'
                    ]]
                ])
            }
        }

        // ── Compile, Test, Package (sect. 6.3 / 7.1 / 7.2) ─────────────────
        // mvn verify includes: compile → test → package → verify
        // The verify phase triggers the JaCoCo report goal, which writes
        // target/site/jacoco/jacoco.xml (needed by publishCoverage).
        stage('Compile, Test, Package') {
            steps {
                sh 'mvn -f sesion05/pom.xml clean verify'
            }
            post {
                success {
                    // JUnit trend graph
                    junit testResults: 'sesion05/target/surefire-reports/TEST-*.xml',
                          allowEmptyResults: false

                    // Archive the jar so it can be downloaded from Jenkins
                    archiveArtifacts artifacts: 'sesion05/target/*.jar',
                                     fingerprint: true

                    // sect. 6.3 / 7.2 — JaCoCo coverage trend (JaCoCo plugin)
                    jacoco(
                        execPattern:      'sesion05/target/jacoco.exec',
                        classPattern:     'sesion05/target/classes',
                        sourcePattern:    'sesion05/src/main/java',
                        exclusionPattern: 'sesion05/src/test/'
                    )

                    // sect. 7.2 — Coverage Report (Code Coverage API plugin)
                    publishCoverage adapters: [
                        jacocoAdapter('sesion05/target/site/jacoco/jacoco.xml')
                    ]
                }
            }
        }

        // ── Static analysis + Dependency-Check (sect. 7.3 / 7.4) ────────────
        // mvn site runs all plugins declared in <reporting> of pom.xml:
        //   Checkstyle, PMD (incl. CPD), SpotBugs, OWASP Dependency-Check.
        // Compiled classes from the previous stage are still in target/classes,
        // so SpotBugs can analyse bytecode without re-compiling.
        stage('Analysis') {
            steps {
                withCredentials([string(credentialsId: 'nvd-api-key', variable: 'NVD_API_KEY')]) {
                    sh 'mvn -f sesion05/pom.xml site -Dnvd.api.key=$NVD_API_KEY'
                }
            }
            post {
                success {
                    // sect. 7.4 — OWASP Dependency-Check
                    dependencyCheckPublisher pattern: 'sesion05/target/site/dependency-check-report.xml'

                    // sect. 7.3 — Warnings Next Generation plugin required for all below
                    recordIssues enabledForFailure: true, tool: checkStyle(pattern: 'sesion05/target/checkstyle-result.xml')
                    recordIssues enabledForFailure: true, tool: pmdParser(pattern: 'sesion05/target/pmd.xml')
                    recordIssues enabledForFailure: true, tool: cpd(pattern: 'sesion05/target/cpd.xml')
                    recordIssues enabledForFailure: true, tool: spotBugs(pattern: 'sesion05/target/spotbugsXml.xml')
                }
            }
        }

        // ── Javadoc & Maven Site (sect. 6.4 / 7.5) ──────────────────────────
        // javadoc:javadoc generates target/site/apidocs (published via JavadocArchiver).
        // publishHTML exposes the full Maven site already built in the Analysis stage.
        // NOTE: if Jenkins shows a JAVA_HOME error, add JAVA_HOME as a global
        //       environment variable in Manage Jenkins > Configure System.
        stage('Documentation') {
            steps {
                sh 'mvn -f sesion05/pom.xml javadoc:javadoc javadoc:aggregate'

                // Javadoc plugin required
                step([$class: 'JavadocArchiver',
                      javadocDir: 'sesion05/target/site/apidocs',
                      keepAll: false])

                // HTML Publisher plugin required
                publishHTML(target: [
                    reportName:  'Maven Site',
                    reportDir:   'sesion05/target/site',
                    reportFiles: 'index.html',
                    keepAll:     false,
                    allowMissing: false,
                    alwaysLinkToLastBuild: true
                ])
            }
        }
    }
}
