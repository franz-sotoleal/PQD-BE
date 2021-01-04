rootProject.name = "PQD"

include(
        "configuration",
        "application",
        "adapters:web",
        "adapters:persistence",
        "adapters:sonarqube",
        "adapters:messaging"
)
