pipeline {
    agent any

    tools {
        // Jenkins 환경 설정에서 구성한 Maven 이름 (예: 'Maven 3.9')
        maven 'Maven 3.9' 
        // Jenkins 환경 설정에서 구성한 JDK 이름 (예: 'JDK 17')
        jdk 'JDK 17'
    }

    environment {
        // SonarQube 시스템 설정에서 세팅한 서버. (이름을 SonarQube로 설정했다고 가정)
        // Jenkins 컨테이너 내부에서 실행되므로 환경변수는 필요한 것만 적거나 생략 가능합니다.
        SONAR_SCANNER_OPTS = "-Xmx512m"
    }

    stages {
        stage('Checkout') {
            steps {
                // Multibranch 파이프라인은 코드를 자동으로 인식하므로 'checkout scm'만 작성하면 됩니다!
                echo 'Checking out source code...'
                checkout scm
            }
        }

        stage('Build') {
            steps {
                echo 'Building the project...'
                // Docker로 올라간 Jenkins는 Linux 환경이므로 'bat' 대신 'sh' 명령어를 사용합니다.
                sh 'mvn clean install -DskipTests'
            }
        }

        stage('Test') {
            steps {
                echo 'Running unit tests...'
                sh 'mvn test'
            }
        }

        stage('SonarQube Analysis') {
            steps {
                echo 'Running SonarQube analysis...'
                // Jenkins에서 System > SonarQube servers에 등록했던 이름(예: 'SonarQube')과 동일해야 합니다.
                withSonarQubeEnv('SonarQube') {
                    sh 'mvn sonar:sonar'
                }
            }
        }

        stage('Quality Gate') {
            steps {
                echo 'Checking Quality Gate...'
                timeout(time: 1, unit: 'HOURS') {
                    // SonarQube 웹훅이 Jenkins로 응답을 줄 때까지 대기
                    // 실패 시 파이프라인을 중단(abort)시킵니다.
                    waitForQualityGate abortPipeline: true
                }
            }
        }
    }

    post {
        always {
            echo 'Pipeline has finished.'
        }
        success {
            echo 'Pipeline succeeded!'
        }
        failure {
            echo 'Pipeline failed! Check SonarQube Quality Gate.'
        }
    }
}
