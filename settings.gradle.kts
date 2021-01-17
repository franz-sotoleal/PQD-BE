rootProject.name = "PQD"

include(
        "configuration",
        "application",
        "adapters:web",
        "adapters:persistence",
        "adapters:messaging",
        "adapters:sonarqube",
        "adapters:jira"
)
