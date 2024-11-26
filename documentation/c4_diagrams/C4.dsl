workspace {

    model {
        user = person "User" "person who buys insurance"

        broker = person "Broker" "registered external user"

        employee = person "Employee" "registered internal user"

        application = softwareSystem "Application" "Insurance software system" {
            apiApp = container "API/Web Application" "handles business logic and provides REST API & web" "Java and Spring Boot" {
                apiApp_webController = component "Web Controller" "handles web requests" "Java, Spring Boot, Thymeleaf"
                apiApp_restControllerExt = component "REST Controller (external)" "handles external REST requests" "Java and Spring Boot"
                apiApp_restControllerInt = component "REST Controller (internal)" "handles internal REST requests" "Java and Sring Boot"
                apiApp_underwriting = component "Underwriting service" "handles business logic calculations" "Java and Spring Boot"
                apiApp_calculateService = component "Calculate Agreement service" "handles agreement generation" "Java and Spring Boot"
                apiApp_getService = component "Get agreement by UUID service" "retrieves agreement data by UUID" "Java and Spring Boot"
                apiApp_exportService = component "Export service" "exports agreement data to XML" "Java and Spring Boot"
                apiApp_proposalQueueSender = component "Proposal queue sender" "sends proposals to RabbitMQ" "Java and Spring Boot"
                apiApp_proposalAckQueueListener = component "Proposal acknowledgment queue listener" "listens for acknowledgments from RabbitMQ" "Java and Spring Boot"
                apiApp_proposalAck = component "Proposal acknowledgments" "registers acknowledgments to database" "Java and Spring Boot"
                apiApp_blackListService = component "Blacklist service" "handles blacklist checks" "Java and Spring Boot"
                apiApp_blackListWebClient = component "Blacklist Web Client" "sends REST requests" "Java and Spring Boot"
                apiApp_validation = component "Validation" "handles data validation" "Java and Spring Boot"
                apiApp_repositories = component "Repository classes" "handles access to rates and coefficients" "Java and Spring Boot"
                apiApp_entities = component "Repository entity classes" "handles access to agreement data" "Java and Spring Boot"
                apiApp_jobs = component "Scheduled jobs" "performs jobs" "Java and Spring Boot"
            }

            blackListApp = container "Blacklist Application" "handles blacklist data" "Java and Spring Boot" {
                blackListApp_restController = component "REST Controller" "handles REST requests" "Java and Spring Boot"
                blackListApp_service = component "Blacklist service" "handles blacklist checks" "Java and Spring Boot"
                blackListApp_repository = component "Repositoryclass" "handles access to blacklist data" "Java and Spring Boot"
            }

            docGeneratorApp = container "Document Generator Application" "handles document generation" "Java and Spring Boot" {
                docGeneratorApp_proposalQueueListener = component "Proposal acknowledgment queue listener" "listens for acknowledgments from RabbitMQ" "Java and Spring Boot"
                docGeneratorApp_proposalGenerator = component "Proposal generator" "generates proposal PDF" "Java and Spring Boot"
                docGeneratorApp_proposalAckQueueSender = component "Proposal acknowledgment queue sender" "sends acknowledgments to RabbitMQ" "Java and Spring Boot"

            }

            database = container "Database" "stores agreement data, rates and coefficients, black list" "MySQL" "Database" {
                database_insuranceDatabase = component "Insurance Calc database" "stores agreement data, rates and coefficients" "MySQL"
                database_blackListDatabase = component "Blacklist database" "stores black list data" "MySQL"
            }

            rabbitMQ = container "RabbitMQ" "handles messages" "RabbitMQ" "Message broker"

            elasticSearch = container "ElasticSearch" "stores logs and metrics" "ElasticSearch" "ELS"
            fileBeat = container "FileBeat" "collects and ships logs" "FileBeat" "ELS"
            metricBeat = container "MetricBeat" "collects and ships metrics" "MetricBeat" "ELS"
            logStash = container "LogStash" "processes logs" "LogStash" "ELS"
            kibana = container "Kibana" "visualizes logs and metrics" "Kibana" "ELS"
        }

        // apiApp's interactions
        user -> apiApp_webController "visits web page (unauthenticated)"
        broker -> apiApp_restControllerExt "makes API calls (POST, authenticated)"
        employee -> apiApp_restControllerExt "makes API calls (POST, authenticated)"
        employee -> apiApp_restControllerInt "makes API calls (GET, authorized)"

        // additional relationship for correct Context and Container view
        employee -> application "makes API calls (GET, authorized)"
        employee -> apiApp "makes API calls (GET, authorized)"
        apiApp -> rabbitMQ "message exchange"
        docGeneratorApp -> rabbitMQ "message exchange"

        apiApp_webController -> apiApp_calculateService "uses"
        apiApp_restControllerExt -> apiApp_calculateService "uses"
        apiApp_restControllerInt -> apiApp_getService "uses"

        apiApp_calculateService -> apiApp_validation "uses"
        apiApp_validation -> apiApp_blackListService "uses"
        apiApp_blackListService -> apiApp_blackListWebClient "uses"
        apiApp_blackListWebClient -> blackListApp_restController "makes API calls (POST)"
        apiApp_calculateService -> apiApp_underwriting "uses"
        apiApp_underwriting -> apiApp_repositories "uses"

        apiApp_calculateService -> apiApp_proposalQueueSender "uses"
        apiApp_proposalQueueSender -> rabbitMQ "sends messages to queue"
        apiApp_proposalAckQueueListener -> rabbitMQ "listens for messages"
        apiApp_proposalAckQueueListener -> apiApp_proposalAck "uses"
        apiApp_proposalAck -> apiApp_entities "uses"

        apiApp_getService -> apiApp_validation "uses"
        apiApp_getService -> apiApp_entities "uses"

        apiApp_jobs -> apiApp_exportService "uses"
        apiApp_exportService -> apiApp_validation "uses"
        apiApp_exportService -> apiApp_entities "uses"

        apiApp_entities -> database_insuranceDatabase "reads from and writes to"
        apiApp_repositories -> database_insuranceDatabase "reads from"

        // blackListApp's interactions
        blackListApp_restController -> blackListApp_service "uses"
        blackListApp_service -> blackListApp_repository "uses"
        blackListApp_repository -> database_blackListDatabase "reads from"

        // docGeneratorApp's interactions
        docGeneratorApp_proposalQueueListener -> rabbitMQ "listens for messages"
        docGeneratorApp_proposalQueueListener -> docGeneratorApp_proposalGenerator "uses"
        docGeneratorApp_proposalGenerator -> docGeneratorApp_proposalAckQueueSender "uses"
        docGeneratorApp_proposalAckQueueSender -> rabbitMQ "sends messages to queue"

        // Elastic Stack's interactions
        fileBeat -> apiApp "collects logs"
        fileBeat -> blackListApp "collects logs"
        fileBeat -> docGeneratorApp "collects logs"
        fileBeat -> logstash "ships logs"
        logstash -> elasticSearch "ships logs"
        metricBeat -> apiApp "collects metrics"
        metricBeat -> blackListApp "collects metrics"
        metricBeat -> docGeneratorApp "collects metrics"
        metricBeat -> elasticSearch "ships metrics"
        kibana -> elasticSearch "accesses logs and metrics"
    }

    views {
        systemContext application "Application" {
            include *
            autoLayout lr
        }

        container application "Containers" {
            include *
            autoLayout lr
        }

        component apiApp "API_Web_Application" {
            include *
            autolayout lr
        }

        component docGeneratorApp "Doc_Generator_App" {
            include *
            autolayout lr
        }

        component blackListApp "Black_List_App" {
            include *
            autolayout lr
        }

        styles {
            element "Person" {
                shape person
                background #08427b
                color #ffffff
            }

            element "Software System" {
                background #1168bd
                color #ffffff
            }

            element "Container" {
                background #438dd5
                color #ffffff
            }

            element "Component" {
                background #85bb65
                color #ffffff
            }

            element "Database" {
                shape cylinder
                background #f5da81
                color #000000
            }

            element "Message broker" {
                background #D6CDEA
                color #4A3C66
            }

            element "ELS" {
                background #FADADD
                color #7A3031
            }
        }
    }
}
